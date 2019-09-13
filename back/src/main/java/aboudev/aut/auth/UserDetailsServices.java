package aboudev.aut.auth;

import aboudev.aut.entities.User;
import aboudev.aut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public UserDetailsServices(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByUsername(s).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username : " + s));
        return UserPrincipal.build(user);
    }
}
