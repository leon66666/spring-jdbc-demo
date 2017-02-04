package zhongqiu.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zhongqiu.bean.Person;
import zhongqiu.service.PersonServiceBean;

public class Spring_JDBCTest {
	private static PersonServiceBean personServiceBean;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			personServiceBean = (PersonServiceBean) cxt.getBean("personService");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		for (int i = 0; i < 5; i++)
			personServiceBean.save(new Person("小王" + i));
	}

	@Test
	public void getPerson() {
		Person person = personServiceBean.getPerson(2);
		System.out.println(person.getName());
	}

	@Test
	public void update() {
		Person person = personServiceBean.getPerson(2);
		person.setName("张xx");
		personServiceBean.update(person);
	}

	@Test
	public void delete() {
		personServiceBean.delete(1);
	}

	@Test
	public void delete_uncheckedexception() {
		personServiceBean.delete_UncheckedException(3);
	}

	@Test
	public void delete_checkedexception() {
		try {
			personServiceBean.delete_CheckedException(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPersons() {
		for (Person person : personServiceBean.getPersons()) {
			System.out.println(person.getName());
		}
	}
}
