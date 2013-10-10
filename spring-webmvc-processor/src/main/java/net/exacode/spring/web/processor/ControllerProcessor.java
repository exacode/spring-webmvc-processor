package net.exacode.spring.web.processor;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.exacode.spring.web.processor.model.MetaController;

import org.springframework.stereotype.Controller;

/**
 * Processor used to generate meta models for MongoDB documents.
 * <p>
 * Uses {@link Document} annotation to determine the root of the MongoDB
 * document.
 * 
 * @author mendlik
 */
public class ControllerProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		if (roundEnv.processingOver()) {
			// We're not interested in the postprocessing round.
			return false;
		}

		AptUtils aptUtils = new AptUtils(processingEnv);
		MetaCotrollerWriter writer = new MetaCotrollerWriter(processingEnv);
		MetaControllerGenerator generator = new MetaControllerGenerator(
				aptUtils);
		for (TypeElement te : annotations) {
			for (Element element : roundEnv.getElementsAnnotatedWith(te)) {
				if (element instanceof TypeElement) {
					TypeElement typeElement = (TypeElement) element;
					aptUtils.getAptLogger().note(
							"Generating metamodel for controller class: "
									+ typeElement.asType().toString());
					MetaController metaController = generator
							.analyzeType(typeElement);
					if (metaController != null) {
						writer.write(metaController);
					}
				}
			}
		}
		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> supportedTypes = new HashSet<String>();
		supportedTypes.add(Controller.class.getCanonicalName());
		return supportedTypes;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

}
