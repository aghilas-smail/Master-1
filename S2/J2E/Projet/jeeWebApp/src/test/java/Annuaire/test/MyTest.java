package Annuaire.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Annuaire.model.Groupe;
import Annuaire.repo.IDirectoryDaoGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import Annuaire.model.Person;
import Annuaire.repo.IDirectoryDaoPerson;
import Annuaire.Starter;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
public class MyTest {

	@Autowired
	IDirectoryDaoPerson daoPerson;
	@Autowired
	IDirectoryDaoGroup daoGroup;

	@Test
	public void testPerson() {
		var p1 = new Person("person 1");
		p1.setEmail("p1@gmail.com");
		Date d = new Date(2001,01,02);
		p1.setBirthDay(d);
		p1.setPassword("pwd");
		p1.setWebsite("site.com");
		/* test ajout */
		var p = daoPerson.save(p1);
		assertTrue(p.getId() > 0);
		/* test find */
		var p2 = daoPerson.findById(p.getId());
		assertEquals(p2.get().getName(),"person 1");
		/*Search by name test */
		List<Person> l = new ArrayList<>() ;
		l.add(p1);

		assertEquals( daoPerson.searchByName(p2.get().getName()), l);
	}

	@Test
	public void testGroupe() {
		Groupe g = new Groupe();
		g.setName("group1");


		/* test ajout */
		assertTrue(g.getId() == null);
		g = daoGroup.save(g);
		assertTrue(g.getId() > 0);

		/* test find */
		var g1 = daoGroup.findById(g.getId());
		assertEquals(g1.get().getName(), "group1");

		/* test modification */

		g.setName("group2");
		daoGroup.save(g);
		var g2 = daoGroup.findById(g.getId());
		assertEquals(g2.get().getName(), "group2");
	}

}
