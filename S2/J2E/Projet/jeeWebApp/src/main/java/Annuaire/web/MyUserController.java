package Annuaire.web;

import javax.annotation.PostConstruct;

import Annuaire.job.IDirectoryManager;
import Annuaire.model.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Annuaire.model.Person;
import Annuaire.repo.IDirectoryDaoPerson;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class MyUserController {

	/*
	 * Injection de la DAO de manipulation des personnes.
	 */
	@Autowired
	private IDirectoryManager idm;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/listPersons")
	public String list(Model model) {
		List<Person> persons = idm.findAllPersons();
		model.addAttribute("persons", persons);
		return "personListing";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		Person p = new Person();
		List<Groupe> groupes = idm.findAllGroupes();
		model.addAttribute("person", p);
		model.addAttribute("groupes", groupes);
		return "registration";
	}
	@PostMapping("/registration")
	public String register(@ModelAttribute("person") Person person) {
		idm.savePerson(person);
		return "redirect:/user/listPersons";
	}

	@GetMapping("/profile")
	public String showProfilForm(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String username = userDetails.getUsername();
			Person person = idm.findPersonByEmail(username);
			List<Groupe> groupes = idm.findAllGroupes();
			model.addAttribute("person", person);
			model.addAttribute("groupes", groupes);
			return "editPerson";
		}
		return "redirect:/login";
	}

	@PostMapping("/profile")
	public String editPerson(@ModelAttribute("person") Person person) {
		String plainPassword = person.getPassword();
		if(plainPassword == "") {
			String oldPassword = idm.findPersonByEmail(person.getEmail()).getPassword();
			person.setPassword(oldPassword);
		}
		else {
			String hashedPassword = passwordEncoder.encode(plainPassword);
			person.setPassword(hashedPassword);
		}
		idm.savePerson(person);
		return "redirect:/user/listPersons";
	}
}
