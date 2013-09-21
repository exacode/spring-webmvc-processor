package org.springframework.web.processor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionalParameters {
	private String className;

	private final List<Parameter> pathVariables = new ArrayList<Parameter>();

	private final List<Parameter> requestParameters = new ArrayList<Parameter>();

	private final List<Parameter> matrixVariables = new ArrayList<Parameter>();

	public OptionalParameters(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void addPathVariable(Parameter param) {
		pathVariables.add(param);
	}

	public void addRequestParameter(Parameter param) {
		requestParameters.add(param);
	}

	public void addMatrixVariable(Parameter param) {
		matrixVariables.add(param);
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

	public boolean isEmpty() {
		return pathVariables.isEmpty() && requestParameters.isEmpty()
				&& matrixVariables.isEmpty();
	}

}
