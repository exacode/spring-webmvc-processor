package net.exacode.spring.web.processor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetaControllerMethod {

	private String methodName;

	private String uri;

	private OptionalParameters optionalParametersType;

	private final List<Parameter> parameters = new ArrayList<Parameter>();

	private final List<Parameter> pathVariables = new ArrayList<Parameter>();

	private final List<Parameter> requestParameters = new ArrayList<Parameter>();

	private final List<Parameter> matrixVariables = new ArrayList<Parameter>();

	public MetaControllerMethod() {
	}

	public MetaControllerMethod(String methodName, String uri) {
		super();
		this.uri = uri;
		this.methodName = methodName;
		this.optionalParametersType = new OptionalParameters(
				upperCaseFirstLetter(methodName));
	}

	public String getUri() {
		return uri;
	}

	private String upperCaseFirstLetter(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	public String getOptionalParametersClassName() {
		return optionalParametersType.getClassName();
	}

	public void setOptionalParametersClassName(String optionalParametersTypeName) {
		this.optionalParametersType.setClassName(optionalParametersTypeName);
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void addPathVariable(Parameter param) {
		if (param == null) {
			return;
		}
		if (param.isRequired()) {
			parameters.add(param);
			pathVariables.add(param);
		} else {
			optionalParametersType.addPathVariable(param);
		}
	}

	public void addRequestParameter(Parameter param) {
		if (param == null) {
			return;
		}
		if (param.isRequired()) {
			parameters.add(param);
			requestParameters.add(param);
		} else {
			optionalParametersType.addRequestParameter(param);
		}
	}

	public void addMatrixVariable(Parameter param) {
		if (param == null) {
			return;
		}
		if (param.isRequired()) {
			parameters.add(param);
			matrixVariables.add(param);
		} else {
			optionalParametersType.addMatrixVariable(param);
		}
	}

	public List<Parameter> getPathVariables() {
		return Collections.unmodifiableList(pathVariables);
	}

	public List<Parameter> getRequestParameters() {
		return Collections.unmodifiableList(requestParameters);
	}

	public List<Parameter> getMatrixVariables() {
		return Collections.unmodifiableList(matrixVariables);
	}

	public List<Parameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	public boolean hasNoParameters() {
		return parameters.isEmpty() && optionalParametersType.isEmpty();
	}

	public boolean hasOptionalParameters() {
		return !optionalParametersType.isEmpty();
	}

	public String getSignature() {
		StringBuilder builder = new StringBuilder(methodName);
		builder.append("(");
		boolean first = true;
		for (Parameter param : parameters) {
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append(param.getSignature());
		}
		builder.append(")");
		return builder.toString();
	}

	public String getDeclaration() {
		StringBuilder builder = new StringBuilder(methodName);
		builder.append("(");
		boolean first = true;
		for (Parameter param : parameters) {
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append(param.getDeclaration());
		}
		builder.append(")");
		return builder.toString();
	}

	public OptionalParameters getOptionalParameters() {
		return optionalParametersType;
	}

	@Override
	public String toString() {
		return getDeclaration();
	}

}
