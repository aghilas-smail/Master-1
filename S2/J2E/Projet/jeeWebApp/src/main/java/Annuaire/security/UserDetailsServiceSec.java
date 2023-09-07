package Annuaire.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Annuaire.model.Person;
import Annuaire.repo.IDirectoryDaoPerson;

@Service
public class UserDetailsServiceSec implements UserDetailsService {

    @Autowired
    private IDirectoryDaoPerson personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(" Username " + username);
        Person person = personRepository.findPersonByEmail(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(person.getEmail(), person.getPassword(), authorities);
    }
}
