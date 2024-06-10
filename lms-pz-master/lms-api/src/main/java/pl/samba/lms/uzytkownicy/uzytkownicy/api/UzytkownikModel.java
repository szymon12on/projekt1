package pl.samba.lms.uzytkownicy.uzytkownicy.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.utils.constants.Role;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;
import pl.samba.lms.uzytkownicy.zdjecie.api.ZdjeciaController;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilderDslKt.withRel;

@Getter
@Relation(value = "uzytkownik", collectionRelation = "uzytkownicy")
public class UzytkownikModel extends RepresentationModel<UzytkownikModel> {
    private final Integer id;
    private final String imie;
    private final String nazwisko;
    private final String tytNauk;
    private final String login;
    private final String email;
    private final int telefon;
    private final Date dataUrodz;
    private final byte[] zdjecie;
    private final Status status;
    private final Role rola;

    public UzytkownikModel(Uzytkownik u){
        this.id = u.getIdUzytk();
        this.imie = u.getImie();
        this.nazwisko = u.getNazwisko();
        this.tytNauk = u.getTytNauk();
        this.login = u.getLogin();
        this.email = u.getEmail();
        this.telefon = u.getTelefon();
        this.dataUrodz = u.getDataUrodz();
        this.status = u.getStatus();
        this.rola = u.getRola();
        this.zdjecie = u.getZdjecie().getPlik();
    }
}
