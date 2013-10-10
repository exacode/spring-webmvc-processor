package net.exacode.spring.web.processor.shared.routing;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.exacode.spring.web.processor.shared.RequestMappingConversionUtils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class UriBuilder<T extends UriBuilder<T>> {

	public static class SimpleUriBuilder extends UriBuilder<SimpleUriBuilder> {

		public SimpleUriBuilder(String uriTemplate) {
			super(uriTemplate);
		}

		@Override
		protected SimpleUriBuilder self() {
			return this;
		}

	}

	private static final String LAST_MATRIX_VARIABLE = "LAST_MATRIX_VARIABLE";

	private static final String LAST_MATRIX_VARIABLE_PATH_NAME = "{"
			+ LAST_MATRIX_VARIABLE + "}";

	private final String uriTemplate;

	private final MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<String, String>();

	private final MultiValueMap<String, String> pathVariables = new LinkedMultiValueMap<String, String>();

	private final Map<String, MultiValueMap<String, String>> matrixVariables = new HashMap<String, MultiValueMap<String, String>>();

	public UriBuilder(String uriTemplate) {
		this.uriTemplate = uriTemplate;
	}

	public T addMatrixVariable(String pathName, String name, Object... values) {
		MultiValueMap<String, String> pathMatrixValues = matrixVariables
				.get(pathName);
		if (pathMatrixValues == null) {
			pathMatrixValues = new LinkedMultiValueMap<String, String>();
			matrixVariables.put(pathName, pathMatrixValues);
		}
		return addEntry(pathMatrixValues, name, values);
	}

	public T addMatrixVariable(String pathName, String name,
			Collection<?> values) {
		return addMatrixVariable(pathName, name, values.toArray());
	}

	public T addMatrixVariable(String name, Object... values) {
		return addMatrixVariable(LAST_MATRIX_VARIABLE, name, values);
	}

	public T addMatrixVariable(String name, Collection<?> values) {
		return addMatrixVariable(name, values.toArray());
	}

	public T addPathVariable(String name, Object... values) {
		return addEntry(pathVariables, name, values);
	}

	public T addPathVariable(String name, Collection<?> values) {
		return addPathVariable(name, values.toArray());
	}

	public T addRequestParameter(String name, Object... values) {
		return addEntry(requestParameters, name, values);
	}

	public T addRequestParameter(String name, Collection<?> values) {
		return addRequestParameter(name, values.toArray());
	}

	public MvcRouting mvc() {
		return new MvcRouting(prepareParameterizedUri());
	}

	public UrlRouting url() {
		return new UrlRouting(prepareParameterizedUri());
	}

	protected abstract T self();

	protected String prepareParameterizedUri() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(uriTemplate);
		if (matrixVariables.containsKey(LAST_MATRIX_VARIABLE)) {
			uriComponentsBuilder = uriComponentsBuilder
					.path(LAST_MATRIX_VARIABLE_PATH_NAME);
		}

		for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
			String key = entry.getKey();
			Object[] values = entry.getValue().toArray(
					new String[entry.getValue().size()]);
			uriComponentsBuilder.queryParam(key, values);
		}

		UriComponents uriComponents = uriComponentsBuilder.build().expand(
				RequestMappingConversionUtils.preparePathVariables(
						pathVariables, matrixVariables));

		return uriComponents.toUriString();
	}

	private T addEntry(MultiValueMap<String, String> map, String key,
			Object... values) {
		if (values != null) {
			for (Object value : values) {
				if (value.getClass().isArray()) {
					// handles arrays of primitives
					for (int i = 0; i < Array.getLength(value); ++i) {
						addEntry(map, key, Array.get(value, i));
					}
				} else if (value instanceof Collection<?>) {
					for (Object item : (Collection<?>) value) {
						addEntry(map, key, item);
					}
				} else if (value instanceof Map<?, ?>) {
					for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
						String textKey = RequestMappingConversionUtils
								.convert(entry.getKey());
						addEntry(map, textKey, entry.getValue());
					}
				} else {
					String textValue = RequestMappingConversionUtils
							.convert(value);
					map.add(key, textValue);
				}
			}
		}
		return self();
	}
}
