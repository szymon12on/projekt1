package pl.samba.lms.uzytkownicy.uzytkownicy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.utils.constants.Role;
import pl.samba.lms.uzytkownicy.zdjecie.Zdjecie;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class Uzytkownik {
    private final Integer idUzytk;
    private String imie;
    private String nazwisko;
    private String tytNauk;
    private final String login;
    private String haslo;
    private String email;
    private Integer telefon;
    private Date dataUrodz;
    private Status status;
    private Zdjecie zdjecie;
    private Role rola;

    public static final String IMIE_FIELD = "imie";
    public static final String NAZWISKO_FIELD = "nazwisko";
    public static final String TYT_NAUK_FIELD = "tytNauk";
    public static final String LOGIN_FIELD = "login";
    public static final String HASLO_FIELD = "haslo";
    public static final String EMAIL_FIELD = "email";
    public static final String TELEFON_FIELD = "telefon";
    public static final String DATA_URODZ_FIELD = "dataUrodz";
    public static final String STATUS_FIELD = "status";
    public static final String ZDJECIE_FIELD = "zdjecie";
    public static final String ROLA_FIELD = "rola";
    public Uzytkownik(
            Integer idUzytk,
            String imie,
            String nazwisko,
            String tytNauk,
            String login,String haslo,
            String email,
            Integer telefon,
            Date dataUrodz,
            Status status,
            Zdjecie zdjecie,
            Role rola) {
        this.idUzytk = idUzytk;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.tytNauk = tytNauk;
        this.login = login;
        this.haslo = haslo;
        this.email = email;
        this.telefon = telefon;
        this.dataUrodz = dataUrodz;
        this.status = status;
        this.zdjecie = zdjecie;
        this.rola = rola;
    }

    // Konstruktor argumentowy z adnotacją @JsonCreator
    @JsonCreator
    public Uzytkownik(
                      @JsonProperty(IMIE_FIELD) String imie,
                      @JsonProperty(NAZWISKO_FIELD) String nazwisko,
                      @JsonProperty(TYT_NAUK_FIELD) String tytNauk,
                      @JsonProperty(HASLO_FIELD) String haslo,
                      @JsonProperty(EMAIL_FIELD) String email,
                      @JsonProperty(TELEFON_FIELD) Integer telefon,
                      @JsonProperty(DATA_URODZ_FIELD) Date dataUrodz,
                      @JsonProperty(STATUS_FIELD) Status status,
                      @JsonProperty(ZDJECIE_FIELD) Zdjecie zdjecie,
                      @JsonProperty(ROLA_FIELD) Role rola) {
        this.idUzytk = null;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.tytNauk = tytNauk;
        this.login = null;
        this.haslo = haslo;
        this.email = email;
        this.telefon = telefon;
        this.dataUrodz = dataUrodz;
        this.status = status;
        this.zdjecie = zdjecie;
        this.rola = rola;
    }
}
