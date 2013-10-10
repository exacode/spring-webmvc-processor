package net.exacode.spring.web.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Simple apt logger implementation.
 * <p/>
 * Used during debugging of this annotation processing mechanism.
 * 
 * @author mendlik
 * 
 */
public class AptLogger {
	private final ProcessingEnvironment processingEnv;

	public AptLogger(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
	}

	/**
	 * This is the equivalent of logger functionality, except its output shows
	 * up as a "note" from the compiler
	 * 
	 * @param msg
	 *            - to be logged
	 */
	public void note(String msg) {
		log(Diagnostic.Kind.NOTE, msg);
	}

	/**
	 * This is the equivalent of logger functionality, except its output shows
	 * up as a "error" from the compiler
	 * 
	 * @param msg
	 *            - to be logged
	 */
	public void error(String msg) {
		log(Diagnostic.Kind.ERROR, msg);
	}

	/**
	 * This is the equivalent of logger functionality, except its output shows
	 * up as a "warning" from the compiler
	 * 
	 * @param msg
	 *            - to be logged
	 */
	public void warn(String msg) {
		log(Diagnostic.Kind.WARNING, msg);
	}

	/**
	 * This is the equivalent of logger functionality
	 * 
	 * @param level
	 *            - logging level
	 * @param msg
	 *            - to be logged
	 */
	public void log(Diagnostic.Kind level, String msg) {
		processingEnv.getMessager().printMessage(level, msg);
	}

}
