package net.exacode.spring.web.processor.example.conversion;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import net.exacode.spring.web.processor.example.TestConfiguration;
import net.exacode.spring.web.processor.example.conversion.ColorControllerTest.ColorConversionConfiguration;
import net.exacode.spring.web.processor.example.util.ColorToStringConverter;
import net.exacode.spring.web.processor.example.util.StringToColorConverter;
import net.exacode.spring.web.processor.shared.routing.MvcRouting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ColorConversionConfiguration.class,
		TestConfiguration.class })
public class ColorControllerTest {

	@Configuration
	public static class ColorConversionConfiguration {
		@Bean
		public FormattingConversionServiceFactoryBean conversionService() {
			FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
			conversionService.setConverters(getConverters());
			return conversionService;
		}

		private Set<Converter<?, ?>> getConverters() {
			Set<Converter<?, ?>> converters = new HashSet<Converter<?, ?>>();
			converters.add(new ColorToStringConverter());
			converters.add(new StringToColorConverter());
			return converters;
		}
	}

	@Test
	public void shouldConvertColorToRgbFormat() {
		Color color = Color.ORANGE;

		assertThat(ColorController_.getImage(color).mvc()).isEqualTo(
				new MvcRouting("/image?color=rgb(255,200,0)"));
	}

}
