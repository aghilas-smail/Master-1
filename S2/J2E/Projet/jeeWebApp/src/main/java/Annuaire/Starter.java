package Annuaire;

import Annuaire.repo.IDirectoryDaoGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import Annuaire.repo.IDirectoryDaoPerson;


@SpringBootApplication
public class Starter extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(Starter.class, args);

		IDirectoryDaoPerson daoPerson = ctx.getBean(IDirectoryDaoPerson.class);

		IDirectoryDaoGroup daoGroup = ctx.getBean(IDirectoryDaoGroup.class);


        /*daoPerson.save(new Person("p1"));

		daoGroup.save(new Groupe("Friends"));

		List<Person> personList = daoPerson.searchByName("Idriss");

		System.out.println(personList.get(0).getName());

		System.out.println("Find All method : \n");

		daoPerson.findAll().forEach(p->System.out.println("Person  : " + p.getName()));*/

		//List<Groupe> groupeList = daoGroup.findAll();
		//Groupe g = daoGroup.getById( (long)493);
		/*for (Person p : g.getPersons()
		) {
			System.out.println("personne "+ p.getLastName());
		}*/
		//for (Groupe g : groupeList
		//) {
		//	System.out.println("Group Name : "+g.getName());
		//}

	}

}
