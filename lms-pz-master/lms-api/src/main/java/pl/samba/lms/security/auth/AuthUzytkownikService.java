package pl.samba.lms.security.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.samba.lms.uzytkownicy.uzytkownicy.database.UzytkownikRepository;

@Service
@AllArgsConstructor
public class AuthUzytkownikService implements UserDetailsService {

    private final UzytkownikRepository dataSet;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthUzytkownik(dataSet.getByUnique(username));
    }
}
