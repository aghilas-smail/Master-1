package Annuaire.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    private String name;


    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "groupe",
            cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @ToString.Exclude
    private Set<Person> persons;
    public Groupe(String name) {
        this.name = name;
    }
    @Transactional
    public void  fillSet(Person p){
        if(persons == null) {
            persons = new HashSet<>();
        }
        persons.add(p);
        p.setGroupe(this);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


}
