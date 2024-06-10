package pl.samba.lms.utils;

/**
 * Klasa służąca do określania ścieżek w api
 * @author bsurma
 */
public class PathType {
    public static final String ALL = "/all";
    public static final String ID = "/{id}";

    public static final String ROOT = "/api";

    public static final String AUTH = ROOT + "/v1/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";

    public static final String UZYTKOWNIK = ROOT + "/uzytkownik";
    public static final String ZDJECIE = ROOT + "/zdjecie";

    public static final String WYKAZ_OCEN = ROOT + "/uczen/oceny";

    /** PRZEDMIOT */
    public static final String PRZEDMIOT = ROOT + "/przedmiot";

    public static final String OKRES = PRZEDMIOT + "/okres";

    public static final String ZADANIE = PRZEDMIOT + "/zadanie";
    public static final String AKTYWNE = "/aktywne";
    public static final String ODPOWIEDZ = ZADANIE + "/odpowiedz";
    public static final String OCEN = ID + "/ocen";

    public static final String REJESTRACJA_PRZEDMIOT = PRZEDMIOT + "/uczen";
    public static final String REJESTRUJ = "/rejestruj";
    public static final String OCENA = "/ocena";
    public static final String WYREJESTRUJ = "/wyrejestruj";

    public static final String MATERIAL = PRZEDMIOT + "/material";

    public static final String POWIADOMIENIE = ROOT + "/powiadomienie";
}
