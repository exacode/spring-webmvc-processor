package net.exacode.spring.web.processor.shared.routing;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base for spring tests.
 * <p>
 * Spring tests support IoC and operate on in-memory database.
 * 
 * @author <a href="mailto:mendelski.pawel@gmail.com">Paweł Mendelski</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public abstract class BaseSpringTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
