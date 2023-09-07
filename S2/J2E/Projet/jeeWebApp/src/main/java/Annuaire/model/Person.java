package Annuaire.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;
	@Basic()
	private Date birthDay;

	@Basic()
	private String website;

	@Column(nullable = false)
	private String password;


	@ManyToOne
	@JoinColumn(name="group_id", nullable=true)
	private Groupe groupe;
	public Person(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
		result = 31 * result + (website != null ? website.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

}
