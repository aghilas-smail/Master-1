package Annuaire.repo;

import Annuaire.model.Groupe;
import Annuaire.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDirectoryDaoGroup extends CrudRepository<Groupe, Long> {


    List<Groupe> findByNameContainingIgnoreCase(String query);
}
