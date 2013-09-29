package org.springframework.web.processor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
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
	private final TypeElement mapType;
	private final WildcardType nullWildcardType;
	private final Map<String, DeclaredType> cachedParentTypes = new HashMap<String, DeclaredType>();
	private final AptLogger aptLogger;

	public AptUtils(ProcessingEnvironment processingEnv) {
		this.aptLogger = new AptLogger(processingEnv);
		this.elementUtils = processingEnv.getElementUtils();
		this.typeUtils = processingEnv.getTypeUtils();
		this.mapType = elementUtils.getTypeElement("java.util.Map");
		this.nullWildcardType = typeUtils.getWildcardType(null, null);
	}

	/**
	 * Checks if given type is assignable to {@code Map<?, ?>}.
	 * 
	 * @param type
	 * @return
	 */
	public boolean isMap(TypeMirror type) {
		return type != null && isA(type, mapType);
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
