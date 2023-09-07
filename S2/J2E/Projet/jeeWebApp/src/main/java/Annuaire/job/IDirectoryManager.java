package Annuaire.job;

import Annuaire.model.Groupe;
import Annuaire.model.Person;
//import Annuaire.security.User;

import java.util.List;

public interface IDirectoryManager {

    public void savePerson(Person person);
    public List<Person> findAllPersons();
    public void saveGroupe(Groupe grp);
    public List<Person> findAllPersons(Groupe grp);
    public List<Groupe> findAllGroupes();
    public Groupe findGroupeById(long id);
    public void removeGroupeById(long id);
    Person findPersonByEmail(String mail);
   List<Groupe> searchGroupByName(String query);
}
