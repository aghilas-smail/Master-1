package Annuaire.repo;

import java.util.List;

import Annuaire.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import Annuaire.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IDirectoryDaoPerson extends CrudRepository<Person, Long> {

	@Query("SELECT p FROM Person p WHERE p.name = :nameKey")
	List<Person> searchByName(@Param("nameKey") String name);
	@Query("SELECT p FROM Person p WHERE p.name = :nameKey")
	Person findPersonByName(@Param("nameKey") String name);
	Person findPersonByEmail(String mail);

}
