package org.springframework.web.processor;

import java.io.IOException;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.processor.model.MetaController;
import org.springframework.web.processor.model.MetaControllerMethod;
import org.springframework.web.processor.model.Parameter;
import org.springframework.web.processor.model.Type;

/**
 * Crawls through field definitions of given model type.
 * 
 * @author mendlik
 */
class MetaControllerGenerator {

	private final AptUtils aptUtils;

	public MetaControllerGenerator(AptUtils aptUtils) {
		this.aptUtils = aptUtils;
	}

	/**
	 * Crawls through type (and super type) field definitions and generates
	 * context for writing meta model.
	 * 
	 * @param pw
	 * @param type
	 * @param outputFileName
	 * @throws IOException
	 */
	public MetaController analyzeType(TypeElement typeElement) {
		if (!isControllerValid(typeElement)) {
			return null;
		}
		String qualifiedMetaControllerName = aptUtils.getElementUtils()
				.getBinaryName(typeElement).toString();
		qualifiedMetaControllerName = qualifiedMetaControllerName.replaceAll(
				"\\$", "_").concat("_");
		String qualifiedControllerName = typeElement.getQualifiedName()
				.toString();

		MetaController mappedController = new MetaController(
				qualifiedMetaControllerName, qualifiedControllerName);
		analyzeController(typeElement, mappedController);

		return mappedController;
	}

	private void analyzeController(TypeElement typeElement,
			MetaController mappedController) {
		RequestMapping controllerRequestMapping = typeElement
				.getAnnotation(RequestMapping.class);
		String controllerUri = "";
		if (controllerRequestMapping != null
				&& controllerRequestMapping.value().length > 0) {
			controllerUri = controllerRequestMapping.value()[0];
		}
		for (ExecutableElement method : ElementFilter.methodsIn(typeElement
				.getEnclosedElements())) {
			analyzeMethod(method, mappedController, controllerUri);
		}
	}

	private void analyzeMethod(ExecutableElement method,
			MetaController mappedController, String controllerUri) {
		if (!isMethodValid(method, controllerUri)) {
			return;
		}

		RequestMapping methodRequestMapping = method
				.getAnnotation(RequestMapping.class);
		String uri = controllerUri;
		String[] methodUris = methodRequestMapping.value();
		if (methodUris != null && methodUris.length > 0) {
			uri += methodRequestMapping.value()[0];
		}

		MetaControllerMethod metaMethod = new MetaControllerMethod(method
				.getSimpleName().toString(), uri);

		for (VariableElement param : method.getParameters()) {
			analyzeMethodParameter(method, param, metaMethod);
		}

		mappedController.addMethod(metaMethod);
	}

	private boolean isGetHttpRequestMapping(RequestMapping requestMapping) {
		RequestMethod[] requestMethods = requestMapping.method();
		if (requestMethods == null || requestMethods.length == 0) {
			// by default all request mappings are bind to GET
			return true;
		}
		for (RequestMethod requestMethod : requestMethods) {
			if (RequestMethod.GET.equals(requestMethod)) {
				return true;
			}
		}
		return false;
	}

	private void analyzeMethodParameter(ExecutableElement method,
			VariableElement param, MetaControllerMethod metaMethod) {
		if (param.getAnnotation(PathVariable.class) != null) {
			metaMethod.addPathVariable(analyzePathVariable(method, param));
		} else if (param.getAnnotation(RequestParam.class) != null) {
			metaMethod.addRequestParameter(analyzeRequestParam(method, param));
		} else if (param.getAnnotation(MatrixVariable.class) != null) {
			metaMethod.addMatrixVariable(analyzeMatrixVariable(method, param));
		}
	}

	private Parameter analyzePathVariable(ExecutableElement method,
			VariableElement param) {
		PathVariable pathVariable = param.getAnnotation(PathVariable.class);
		Type type = Type.create(aptUtils, param.asType());
		String paramName = param.getSimpleName().toString();
		Parameter metaParam = Parameter.builder(paramName, type)
				.setBindingName(pathVariable.value()).build();
		return metaParam;
	}

	private Parameter analyzeRequestParam(ExecutableElement method,
			VariableElement param) {
		RequestParam requestParam = param.getAnnotation(RequestParam.class);
		Type type = Type.create(aptUtils, param.asType());
		String paramName = param.getSimpleName().toString();
		Parameter metaParam = Parameter.builder(paramName, type)
				.setBindingName(requestParam.value())
				.setDefaultValue(requestParam.defaultValue())
				.setRequired(requestParam.required()).build();
		return metaParam;
	}

	private Parameter analyzeMatrixVariable(ExecutableElement method,
			VariableElement param) {
		MatrixVariable matixVariable = param
				.getAnnotation(MatrixVariable.class);
		Type type = Type.create(aptUtils, param.asType());
		String paramName = param.getSimpleName().toString();
		Parameter metaParam = Parameter.builder(paramName, type)
				.setBindingName(matixVariable.value())
				.setDefaultValue(matixVariable.defaultValue())
				.setRequired(matixVariable.required()).build();
		return metaParam;
	}

	private boolean isControllerValid(TypeElement typeElement) {
		if (typeElement.getAnnotation(Controller.class) == null) {
			return false;
		}
		if (!typeElement.getTypeParameters().isEmpty()) {
			aptUtils.getAptLogger().note(
					"Skipping controller analysis: " + typeElement.toString()
							+ ". Do not analyze generic controllers.");
			return false;
		}
		return true;
	}

	private boolean isMethodValid(ExecutableElement method, String controllerUri) {
		RequestMapping methodRequestMapping = method
				.getAnnotation(RequestMapping.class);
		if (methodRequestMapping == null) {
			return false;
		}
		if (!isGetHttpRequestMapping(methodRequestMapping)) {
			aptUtils.getAptLogger().note(
					"Skipping method analysis: " + method.toString()
							+ ". Method does note use HTTP  GET method.");
			return false;
		}
		if (!method.getTypeParameters().isEmpty()) {
			aptUtils.getAptLogger().note(
					"Skipping method analysis: " + method.toString()
							+ ". Do not analyze generic methods.");
			return false;
		}
		String[] methodUris = methodRequestMapping.value();
		if (controllerUri.length() == 0
				&& (methodUris == null || methodUris.length == 0)) {
			aptUtils.getAptLogger().note(
					"Skipping method analysis: " + method.toString()
							+ ". Empty routing value.");
			return false;
		}
		return true;
	}
}