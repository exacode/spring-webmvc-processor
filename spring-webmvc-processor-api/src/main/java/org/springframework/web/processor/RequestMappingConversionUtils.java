package org.springframework.web.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.core.convert.ConversionService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class RequestMappingConversionUtils {
	public static String convert(Object value) {
		ConversionService conversionService = RequestMappingProcessorConfiguration.CONVERSION_SERVICE;
		if (conversionService != null
				&& conversionService.canConvert(value.getClass(), String.class)) {
			return conversionService.convert(value, String.class);
		}
		return value.toString();
	}

	public static String convertMatrixVariables(
			MultiValueMap<String, String> multiValueMap) {
		if (multiValueMap == null || multiValueMap.isEmpty()) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Entry<String, List<String>> entry : multiValueMap.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			if (key != null && key.length() > 0 && values != null
					&& !values.isEmpty()) {
				builder.append(";");
				builder.append(key);
				builder.append("=");
				boolean firstValue = true;
				for (Object value : values) {
					if (!firstValue) {
						builder.append(",");
					} else {
						firstValue = false;
					}
					String textValue = convert(value);
					builder.append(textValue);
				}

			}
		}
		return builder.toString();
	}

	private static String convertList(List<String> values) {
		if (values.isEmpty()) {
			return "";
		} else if (values.size() == 1) {
			return values.get(0);
		} else {
			return convert(values);
		}
	}

	public static Map<String, String> preparePathVariables(
			MultiValueMap<String, String> simplePathVariables,
			Map<String, MultiValueMap<String, String>> matrixStringVariables) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		Set<String> visitedNames = new HashSet<String>();
		for (Entry<String, List<String>> entry : simplePathVariables.entrySet()) {
			String key = entry.getKey();
			String pathVariableValue = convertList(entry.getValue());
			String matrixVariableValue = convertMatrixVariables(matrixStringVariables
					.get(key));
			if (matrixVariableValue != null && !matrixVariableValue.isEmpty()) {
				pathVariableValue = pathVariableValue + matrixVariableValue;
			}
			pathVariables.put(key, pathVariableValue);
			visitedNames.add(key);
		}
		for (Entry<String, MultiValueMap<String, String>> entry : matrixStringVariables
				.entrySet()) {
			String key = entry.getKey();
			if (!visitedNames.contains(key)) {
				String matrixVariableValue = convertMatrixVariables(matrixStringVariables
						.get(key));
				if (matrixVariableValue != null
						&& !matrixVariableValue.isEmpty()) {
					pathVariables.put(key, matrixVariableValue);
				}
			}
		}
		return pathVariables;
	}

	public static MultiValueMap<String, String> convertValues(
			MultiValueMap<String, Object> params) {
		MultiValueMap<String, String> converted = new LinkedMultiValueMap<String, String>();
		for (Entry<String, List<Object>> entry : params.entrySet()) {
			String key = entry.getKey();
			for (Object value : entry.getValue()) {
				String convertedValue = RequestMappingConversionUtils
						.convert(value);
				converted.add(key, convertedValue);
			}
		}
		return converted;
	}
}
