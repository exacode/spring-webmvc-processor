package org.springframework.web.processor.example.util;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;

public class StringToColorConverter implements Converter<String, Color> {
	private static final Pattern RGB_PATTERN = Pattern
			.compile("rgb\\(\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*\\)");

	private static final Pattern HEX_PATTERN = Pattern.compile(
			"\\#([A-F0-9]{2})([A-F0-9]{2})([A-F0-9]{2})",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern SHORT_HEX_PATTERN = Pattern.compile(
			"\\#([A-F0-9])([A-F0-9])([A-F0-9])", Pattern.CASE_INSENSITIVE);

	@Override
	public Color convert(String text) {
		// transparent and rgba(0, 0, 0, 0) (transparent in Safari 3)
		if (text.equals("transparent") || text.equals("rgba(0, 0, 0, 0)"))
			return null;

		// rgb(num,num,num)
		Matcher matcher = RGB_PATTERN.matcher(text);
		if (matcher.matches())
			return new Color(Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)));

		// #aaaaaa
		matcher = HEX_PATTERN.matcher(text);
		if (matcher.matches())
			return new Color(Integer.parseInt(matcher.group(1), 16),
					Integer.parseInt(matcher.group(2), 16), Integer.parseInt(
							matcher.group(3), 16));

		// #aaa
		matcher = SHORT_HEX_PATTERN.matcher(text);
		if (matcher.matches())
			return new Color(Integer.parseInt(matcher.group(1), 16) * 11,
					Integer.parseInt(matcher.group(2), 16) * 11,
					Integer.parseInt(matcher.group(3), 16) * 11);

		try {
			// See if it is a named color that matches a constant in Color.
			return (Color) Color.class.getField(text.toUpperCase()).get(
					Color.class);
		} catch (Exception e) {
			// Anything else is unparsable.
			return null;
		}
	}
}
