import lab.model.Contact;
import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {

	private static final String APPLICATION_CONTEXT_XML_FILE_NAME =
			"classpath:application-context.xml";

	private AbstractApplicationContext context = new ClassPathXmlApplicationContext(
			APPLICATION_CONTEXT_XML_FILE_NAME);

	private Person expectedPerson = getExpectedPerson();

	@Test
	void testInitPerson() {
		assertEquals(expectedPerson, context.getBean("person"));
	}

	static Person getExpectedPerson() {
		List<Contact> contacts = new ArrayList<>();
		for (String s : Arrays.asList("asd@asd.ru", "+55 11 99999-5555")) {
			Contact from = Contact.from(s);
			contacts.add(from);
		}
		return new UsualPerson(
				1,
				"John",
				"Smith",
				new SimpleCountry(1, "Russia", "RU"),
				35,
				1.78F,
				true,
				true,
				contacts);
	}
}
