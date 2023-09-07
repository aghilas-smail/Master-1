package Annuaire.web;

import java.util.List;
import java.util.Set;

import Annuaire.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Annuaire.model.Groupe;
import Annuaire.job.IDirectoryManager;

@Controller
@RequestMapping("/groupes")
public class MyGroupeController {

    @Autowired
    private IDirectoryManager idm;

    @GetMapping("")
    public String listeGroupes(Model model) {
        List<Groupe> groupes = idm.findAllGroupes();
        model.addAttribute("groupes", groupes);
        return "groupeListing";
    }

    @GetMapping("/addGroupe")
    public String addGroupe(Model model) {
        Groupe g = new Groupe();
        model.addAttribute("groupe", g);
        return "editGroupe";
    }

    @PostMapping("/addGroupe")
    public String saveGroupe(@ModelAttribute("groupe") Groupe groupe) {
        idm.saveGroupe(groupe);
        return "redirect:/groupes";
    }

    @GetMapping("/editGroupe")
    public String editGroupe(@RequestParam("id") Long id, Model model) {
        Groupe groupe = idm.findGroupeById(id);
        model.addAttribute("groupe", groupe);
        return "editGroupe";
    }

    @PostMapping("/editGroupe")
    public String updateGroupe(@ModelAttribute("groupe") Groupe groupe) {
        idm.saveGroupe(groupe);
        return "redirect:/groupes";
    }

    @GetMapping("/removeGroupe")
    public String removeGroupe(@RequestParam("id") Long id) {
        idm.removeGroupeById(id);
        return "redirect:/groupes";
    }

    @GetMapping("/show")
    public String showGroupePersons(@RequestParam("id") Long id, Model model) {
        Groupe groupe = idm.findGroupeById(id);
        Set<Person> persons = groupe.getPersons();
        model.addAttribute("groupe", groupe);
        model.addAttribute("persons", persons);
        return "view";
    }

    @GetMapping("/rechercher")
    public String searchGroupe(@ModelAttribute Groupe groupe, Model model) {
        List<Groupe> groupes = idm.searchGroupByName(groupe.getName());
        model.addAttribute("groupes", groupes);
        return "groupeListing";

    }
}
