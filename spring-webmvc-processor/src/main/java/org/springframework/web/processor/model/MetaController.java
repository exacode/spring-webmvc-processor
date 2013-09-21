package org.springframework.web.processor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MetaController {

	private final String qualifiedControllerName;

	private final Type type;

	private final Map<String, MetaControllerMethod> methodSignatures = new LinkedHashMap<String, MetaControllerMethod>();

	private final Map<String, MetaControllerMethod> methodOptionalParametersTypeNames = new LinkedHashMap<String, MetaControllerMethod>();

	public MetaController(String qualifiedMetaControllerName,
			String qualifiedControllerName) {
		super();
		this.type = Type.create(qualifiedMetaControllerName);
		this.qualifiedControllerName = qualifiedControllerName;
	}

	public Type getType() {
		return type;
	}

	public void addMethod(MetaControllerMethod method) {
		ensureUniqueMethodSignature(method);
		ensureUniqueMethodOptionalParameterType(method);
		methodSignatures.put(method.getSignature(), method);
		methodOptionalParametersTypeNames.put(
				method.getOptionalParametersClassName(), method);
	}

	private void ensureUniqueMethodOptionalParameterType(
			MetaControllerMethod method) {
		if (methodOptionalParametersTypeNames.containsKey(method
				.getOptionalParametersClassName())) {
			String methodOriginalParametersTypeName = method
					.getOptionalParametersClassName();
			int i = 1;
			method.setOptionalParametersClassName(methodOriginalParametersTypeName
					+ i);
			while (methodOptionalParametersTypeNames.containsKey(method
					.getOptionalParametersClassName())) {
				method.setOptionalParametersClassName(methodOriginalParametersTypeName
						+ ++i);
			}
		}
	}

	private void ensureUniqueMethodSignature(MetaControllerMethod method) {
		if (methodSignatures.containsKey(method.getSignature())) {
			String methodOriginalName = method.getMethodName();
			int i = 1;
			method.setMethodName(methodOriginalName + i);
			while (methodSignatures.containsKey(method.getSignature())) {
				method.setMethodName(methodOriginalName + ++i);
			}
		}
	}

	public Collection<MetaControllerMethod> getMethods() {
		return Collections.unmodifiableCollection(methodSignatures.values());
	}

	public boolean hasNoParameterMappings() {
		for (MetaControllerMethod method : methodSignatures.values()) {
			if (method.hasNoParameters()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasOptionalParameterMappings() {
		for (MetaControllerMethod method : methodSignatures.values()) {
			if (method.hasOptionalParameters()) {
				return true;
			}
		}
		return false;
	}

	public String getQualifiedControllerName() {
		return qualifiedControllerName;
	}

	public List<OptionalParameters> getMappingsOptionalParameters() {
		List<OptionalParameters> params = new ArrayList<OptionalParameters>();
		for (MetaControllerMethod method : methodOptionalParametersTypeNames
				.values()) {
			if (method.hasOptionalParameters()) {
				params.add(method.getOptionalParameters());
			}
		}
		return params;
	}

}
