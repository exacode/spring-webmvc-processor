package org.springframework.web.processor.model;

public class Parameter {
	private final String methodParameterName;

	private final Type type;

	private final String bindingName;

	private final String pathName;

	private final boolean required;

	private final String defaultValue;

	public static Builder builder(String methodParameterName, Type type) {
		return new Builder(methodParameterName, type);
	}

	private Parameter(String methodParameterName, Type type,
			String bindingName, String pathName, boolean required,
			String defaultValue) {
		super();
		this.pathName = pathName;
		this.methodParameterName = methodParameterName;
		this.type = type;
		this.bindingName = bindingName;
		this.required = required;
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return required;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getMethodParameterName() {
		return methodParameterName;
	}

	public String getBindingName() {
		return bindingName;
	}

	public String getPathName() {
		return pathName;
	}

	public Type getType() {
		return type;
	}

	public String getSignature() {
		return type.getCanonicalName() + " " + methodParameterName;
	}

	public String getDeclaration() {
		return type.getCanonicalNameWithGenerics() + " " + methodParameterName;
	}

	public static class Builder {
		private final String methodParameterName;

		private final Type type;

		private String bindingName;

		private String pathName;

		private boolean required = true;

		private String defaultValue;

		public Builder(String methodParameterName, Type type) {
			this.methodParameterName = methodParameterName;
			this.type = type;
		}

		public Builder setPathName(String pathName) {
			this.pathName = pathName;
			return this;
		}

		public Builder setBindingName(String bindingName) {
			this.bindingName = bindingName;
			return this;
		}

		public Builder setRequired(boolean required) {
			this.required = required;
			return this;
		}

		public Builder setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Parameter build() {
			return new Parameter(methodParameterName, type, bindingName,
					pathName, required, defaultValue);
		}
	}
}
