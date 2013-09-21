package org.springframework.web.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Java APT utility mechanism. Used during meta model generation.
 * 
 * @author mendlik
 * 
 */
public class AptUtils {
	private final Elements elementUtils;
	private final Types typeUtils;
	private final TypeElement collectionType;
	private final TypeElement objectType;
	private final WildcardType nullWildcardType;
	private final Set<TypeElement> nonDocumentTypes = new HashSet<TypeElement>();
	private final Map<String, DeclaredType> cachedParentTypes = new HashMap<String, DeclaredType>();
	private final AptLogger aptLogger;

	public AptUtils(ProcessingEnvironment processingEnv) {
		this.aptLogger = new AptLogger(processingEnv);
		this.elementUtils = processingEnv.getElementUtils();
		this.typeUtils = processingEnv.getTypeUtils();
		this.collectionType = elementUtils
				.getTypeElement("java.util.Collection");
		this.objectType = elementUtils.getTypeElement("java.lang.Object");
		this.nullWildcardType = typeUtils.getWildcardType(null, null);

		// MongoDB Non document types from MongoMappingContext
		// Primitive wrappers
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Integer"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Long"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Float"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Double"));
		nonDocumentTypes
				.add(elementUtils.getTypeElement("java.lang.Character"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Byte"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Short"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Boolean"));
		// Basic types
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.String"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.util.Date"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.net.URL"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("java.math.BigInteger"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("java.math.BigDecimal"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("java.util.Collection"));
		nonDocumentTypes.add(elementUtils.getTypeElement("java.lang.Void"));
		// BSON types
		nonDocumentTypes.add(elementUtils
				.getTypeElement("org.bson.types.ObjectId"));
		// Joda date types
		nonDocumentTypes.add(elementUtils
				.getTypeElement("org.joda.time.DateTime"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("org.joda.time.LocalDate"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("org.joda.time.LocalDateTime"));
		nonDocumentTypes.add(elementUtils
				.getTypeElement("org.joda.time.DateMidnight"));
	}

	/**
	 * Determines if given {@code type} should be treated as MongoDB document or
	 * nested document.
	 * 
	 * @param type
	 * @return
	 */
	public boolean isDocument(TypeMirror type) {
		if (type == null) {
			return false;
		}
		if (type.getKind().isPrimitive()) {
			return false;
		}
		if (type.getKind() == TypeKind.ARRAY) {
			return false;
		}
		if (isEnum(type)) {
			return false;
		}
		if (isCollection(type)) {
			return false;
		}
		for (TypeElement nonDocumentType : nonDocumentTypes) {
			// nonDocumentType == null when given type could not be found
			if (nonDocumentType != null && isA(type, nonDocumentType)) {
				return false;
			}
		}
		return type.getKind() == TypeKind.DECLARED;
	}

	/**
	 * Checks if given type is assignable to {@code Collection<?>}.
	 * 
	 * @param type
	 * @return
	 */
	public boolean isCollection(TypeMirror type) {
		return type != null && isA(type, collectionType);
	}

	/**
	 * Retrieves type argument {@code <T>} from type that implements
	 * {@code Collection<T>}.
	 * 
	 * 
	 * @param type
	 * @return collection type argument or null if type argument cannot be
	 *         determined.
	 * 
	 */
	public TypeMirror getCollectionTypeArgument(TypeMirror type) {
		if (type == null || !isTypeElement(type) || !isA(type, collectionType)) {
			return null;
		}
		DeclaredType declaredType = (DeclaredType) type;
		TypeElement typeElement = toTypeElement(type);
		while (typeElement != null && !objectType.equals(typeElement)) {
			for (TypeMirror typeInterface : typeElement.getInterfaces()) {
				TypeElement typeInterfaceElement = toTypeElement(typeInterface);
				if (collectionType.equals(typeInterfaceElement)) {
					List<? extends TypeMirror> typeArgsList = declaredType
							.getTypeArguments();
					TypeMirror[] typeArgs = typeArgsList
							.toArray(new TypeMirror[typeArgsList.size()]);
					declaredType = typeUtils.getDeclaredType(
							typeInterfaceElement, typeArgs);
					return declaredType.getTypeArguments().get(0);
				}
			}
			typeElement = toTypeElement(typeElement.getSuperclass());
		}
		return null;
	}

	/**
	 * 
	 * @param type
	 * @return instance of {@link TypeElement} or null if {@code type} cannot be
	 *         converted
	 */
	public TypeElement toTypeElement(TypeMirror type) {
		if (type != null && isTypeElement(type)) {
			return (TypeElement) ((DeclaredType) type).asElement();
		}
		return null;
	}

	public TypeMirror getUpperBound(TypeMirror type) {
		TypeMirror lowerBound = type;
		if (TypeKind.TYPEVAR.equals(type.getKind())) {
			TypeVariable typeVariable = (TypeVariable) type;
			TypeMirror typeVariableLowerBound = typeVariable.getUpperBound();
			if (!TypeKind.NULL.equals(typeVariableLowerBound)
					&& !typeUtils.isSameType(typeVariableLowerBound,
							objectType.asType())) {
				lowerBound = getUpperBound(typeVariableLowerBound);
			}
		} else if (TypeKind.WILDCARD.equals(type.getKind())) {
			WildcardType wildcardVariable = (WildcardType) type;
			TypeMirror wildcardTypeLowerBound = wildcardVariable
					.getExtendsBound();
			if (wildcardTypeLowerBound != null
					&& !typeUtils.isSameType(wildcardTypeLowerBound,
							objectType.asType())) {
				lowerBound = getUpperBound(wildcardTypeLowerBound);
			}
		}
		return lowerBound;
	}

	public Types getTypeUtils() {
		return typeUtils;
	}

	public Elements getElementUtils() {
		return elementUtils;
	}

	public AptLogger getAptLogger() {
		return aptLogger;
	}

	private boolean isTypeElement(TypeMirror type) {
		return isDeclaredType(type)
				&& ((DeclaredType) type).asElement() instanceof TypeElement;
	}

	private boolean isDeclaredType(TypeMirror type) {
		return type instanceof DeclaredType;
	}

	private boolean isEnum(TypeMirror type) {
		if (isDeclaredType(type)) {
			DeclaredType declaredType = (DeclaredType) type;
			return declaredType.asElement().getKind() == ElementKind.ENUM;
		}
		return false;
	}

	private boolean isA(TypeMirror type, TypeElement typeElement) {
		// Have we used this type before?
		DeclaredType parentType = cachedParentTypes.get(typeElement
				.getQualifiedName().toString());
		if (parentType == null) {
			// How many generic type parameters does this typeElement require?
			int genericsCount = typeElement.getTypeParameters().size();

			// Fill the right number of types with nulls
			TypeMirror[] typeMirrors = new TypeMirror[genericsCount];
			for (int i = 0; i < genericsCount; i++) {
				typeMirrors[i] = nullWildcardType;
			}

			// Locate the correct DeclaredType to match with the type
			parentType = typeUtils.getDeclaredType(typeElement, typeMirrors);

			// Remember this DeclaredType
			cachedParentTypes.put(typeElement.getQualifiedName().toString(),
					parentType);
		}
		// Is the given type able to be assigned as the typeElement?
		return typeUtils.isAssignable(type, parentType);
	}
}
