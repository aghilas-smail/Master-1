package Annuaire.job;

import Annuaire.model.Groupe;
import Annuaire.model.Person;
import Annuaire.repo.IDirectoryDaoGroup;
import Annuaire.repo.IDirectoryDaoPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class DirectoryManager implements IDirectoryManager {
    @Autowired
    private IDirectoryDaoPerson personRepo;

    @Autowired
    private IDirectoryDaoGroup groupRepo;

    @PostConstruct
    public void init() {
        int numberOfGroups = 1000;
        int numberOfPersonsMin = 10;
        int numberOfPersonsMax = 20;
        int index = 1;
        Date initialDate = new Date(2001, 07, 23);
        Random random = new Random();

        for (int i = 1; i <= numberOfGroups; i++) {
            Groupe groupe = new Groupe("groupe " + i);
            saveGroupe(groupe);
            int numberOfPersons = (int) (Math.random() * (numberOfPersonsMax - numberOfPersonsMin + 1) + numberOfPersonsMin);
            for (int j = 0; j < numberOfPersons; j++) {
                Person person = new Person(
                        null,
                        "Naruto" + index,
                        "uzumaki" + index,
                        "contactNaruto" + index + "@email.com",
                        new Date(initialDate.getTime() + random.nextInt(365) * 24 * 60 * 60 * 1000L), // add random number of days to initial date
                        "https://translate.google.com/" + index + ".google.com",
                        "password" + index,
                        groupe
                );
                savePerson(person);
                index++;
            }
        }
    }

    @Override
    public void savePerson(Person person) {
        personRepo.save(person);
    }

    @Override
    public List<Person> findAllPersons() {
        return (List<Person>) personRepo.findAll();
    }

    @Override
    public void saveGroupe(Groupe grp) {
        groupRepo.save(grp);
    }

    @Override
    public List<Person> findAllPersons(Groupe grp) {
        return (List<Person>) personRepo.findAll();
    }

    @Override
    public List<Groupe> findAllGroupes() {
        return (List<Groupe>) groupRepo.findAll();

    }

    @Override
    public Groupe findGroupeById(long id) {
        return groupRepo.findById(id).get();

    }

    @Override
    public void removeGroupeById(long id) {
        Groupe groupe = findGroupeById(id);
        Set<Person> persons = groupe.getPersons();
        for (Person person : persons) {
            person.setGroupe(null);
        }
        personRepo.saveAll(persons);
        groupRepo.delete(groupe);
    }

    @Override
    public Person findPersonByEmail(String mail) {
        return personRepo.findPersonByEmail(mail);
    }

    @Override
    public List<Groupe> searchGroupByName(String query) {
        return groupRepo.findByNameContainingIgnoreCase(query);
    }

}


