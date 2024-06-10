package pl.samba.lms.security.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.security.jwt.JwtService;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.http.LoginRequest;
import pl.samba.lms.utils.http.LoginResponse;
import pl.samba.lms.utils.http.HttpResponse;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;
import pl.samba.lms.uzytkownicy.uzytkownicy.database.UzytkownikRepository;

@RestController
@RequestMapping(path= PathType.AUTH, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {
    private final UzytkownikRepository dataSet;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtil;

    @PostMapping(PathType.REGISTER)
    public ResponseEntity<Object> register(@RequestBody Uzytkownik uzytkownik) throws Exception {
        Integer id = dataSet.save(uzytkownik);
        if(id != null){
            HttpResponse response = new HttpResponse(HttpStatus.CREATED,"Konto utworzone", null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else throw new Exception("Błąd utworzenia konta");
    }


    @PostMapping(PathType.LOGIN)
    public ResponseEntity<Object> login(@RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getLogin(),
                                request.getHaslo())
                );
        String login = authentication.getName();

        Uzytkownik uzytkownik = dataSet.getByUnique(login);
        String token = jwtUtil.createToken(uzytkownik);
        LoginResponse response = new LoginResponse(uzytkownik.getIdUzytk(),uzytkownik.getLogin(),token);

        return ResponseEntity.ok(response);
    }

}
