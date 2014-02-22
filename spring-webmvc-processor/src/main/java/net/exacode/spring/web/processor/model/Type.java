package net.exacode.spring.web.processor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.exacode.spring.web.processor.AptUtils;

public class Type {

	public static Map<String, Type> types = new HashMap<String, Type>();

	public static Type create(String canonicalName) {
		Type type = types.get(canonicalName);
		if (type == null) {
			type = new Type(canonicalName);
			types.put(canonicalName, type);
		}
		return type;
	}

	public static Type create(AptUtils aptUtils, TypeMirror typeMirror) {
		if (TypeKind.DECLARED.equals(typeMirror.getKind())) {
			DeclaredType declaredType = (DeclaredType) typeMirror;
			List<String> generics = new ArrayList<String>();
			for (TypeMirror genericType : declaredType.getTypeArguments()) {
				generics.add(genericType.toString());
			}
			TypeElement typeElement = (TypeElement) declaredType.asElement();
			return new Type(typeElement.getQualifiedName().toString(), generics);
		} else {
			// primitives and arrays
			return create(typeMirror.toString());
		}
	}

	private final String packageName;

	private final String canonicalName;

	private final String className;

	private final String canonicalNameWithGenerics;

	private Type(String canonicalName) {
		this(canonicalName, Collections.<String> emptyList());
	}

	private Type(String canonicalName, List<String> generics) {
		this.canonicalName = canonicalName;
		int lastDot = canonicalName.lastIndexOf('.');
		this.className = canonicalName.substring(lastDot + 1);
		String pkg = null;
		if (lastDot > -1) {
			pkg = canonicalName.substring(0, lastDot);
		}
		this.packageName = pkg;
		if (generics.size() == 0) {
			canonicalNameWithGenerics = canonicalName;
		} else {
			StringBuilder builder = new StringBuilder(canonicalName);
			boolean first = true;
			builder.append("<");
			for (String generic : generics) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append(generic);
			}
			builder.append(">");
			canonicalNameWithGenerics = builder.toString();
		}
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public String getCanonicalNameWithGenerics() {
		return canonicalNameWithGenerics;
	}

	public boolean isDefaultPackage() {
		return packageName == null;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public String toString() {
		return "Type [canonicalNameWithGenerics=" + canonicalNameWithGenerics
				+ "]";
	}

}
