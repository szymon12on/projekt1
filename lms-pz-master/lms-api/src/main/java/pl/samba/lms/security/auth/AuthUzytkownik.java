package pl.samba.lms.security.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class AuthUzytkownik implements UserDetails {

    private Uzytkownik uzytkownik;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(uzytkownik.getRola().name()));
    }

    @Override
    public String getPassword() {
        return uzytkownik.getHaslo();
    }

    @Override
    public String getUsername() {
        return uzytkownik.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return uzytkownik.getStatus().equals(Status.AKTYWNY);
    }
}
