package ioc;

import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {
	
	private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "ioc.xml";

	private BeanFactory context = new ClassPathXmlApplicationContext(
			APPLICATION_CONTEXT_XML_FILE_NAME);

	private Person expectedPerson = HelloWorldTest.getExpectedPerson();

	@BeforeEach
	void setUp() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.bank.config.code");
	}

	@Test
	void testInitPerson() {
		assertEquals(expectedPerson, context.getBean("person"));
	}
}
