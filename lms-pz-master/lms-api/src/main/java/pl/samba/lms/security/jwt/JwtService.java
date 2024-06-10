package pl.samba.lms.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private final String secret;
    private final JwtParser jwtParser;

    @Autowired
    public JwtService(Environment env){
        this.secret = env.getProperty("security.auth.secret");
        this.jwtParser = Jwts.parser().setSigningKey(secret);
    }

    public String createToken(Uzytkownik uzytkownik) {
        Claims claims = Jwts.claims().setSubject(uzytkownik.getLogin());
        claims.put(Uzytkownik.IMIE_FIELD,uzytkownik.getImie());
        claims.put(Uzytkownik.NAZWISKO_FIELD,uzytkownik.getNazwisko());
        claims.put(Uzytkownik.EMAIL_FIELD, uzytkownik.getEmail());
        Date tokenCreateTime = new Date();
        long accessTokenValidity = 60 * 60 * 1000;
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String tokenNaglowek = "Authorization";
        String bearerToken = request.getHeader(tokenNaglowek);

        String tokenPrefix = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(tokenPrefix.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        return claims.getExpiration().after(new Date());
    }

}
