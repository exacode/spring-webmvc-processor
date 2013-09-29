package org.springframework.web.processor.example.util;

import java.awt.Color;

import org.springframework.core.convert.converter.Converter;

public class ColorToStringConverter implements Converter<Color, String> {

	@Override
	public String convert(Color color) {
		StringBuilder builder = new StringBuilder("rgb(");
		builder.append(color.getRed()).append(",");
		builder.append(color.getGreen()).append(",");
		builder.append(color.getBlue()).append(")");
		return builder.toString();
	}
}
