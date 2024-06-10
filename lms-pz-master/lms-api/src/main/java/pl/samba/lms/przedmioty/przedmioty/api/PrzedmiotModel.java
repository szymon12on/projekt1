package pl.samba.lms.przedmioty.przedmioty.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.przedmioty.okresy.api.OkresyController;
import pl.samba.lms.przedmioty.przedmioty.Przedmiot;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.uzytkownicy.uzytkownicy.api.UzytkownicyController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class PrzedmiotModel extends RepresentationModel<PrzedmiotModel> {
    private final Integer id;
    private final Integer idProwadzacego;
    private final Integer idOkresu;
    private final String kod;
    private final String nazwa;
    private final Integer limit;
    private final String opis;
    private final String warunkiZaliczenia;
    private final Status status;
    private final Boolean czyRejestrUczn;

    public PrzedmiotModel(Przedmiot p){
        this.id = p.getIdPrzedmiotu();
        this.idProwadzacego = p.getIdProwadzacego();
        this.idOkresu = p.getIdOkresu();
        this.kod = p.getKod();
        this.nazwa = p.getNazwa();
        this.limit = p.getLimit();
        this.opis = p.getOpis();
        this.warunkiZaliczenia = p.getWarunkiZaliczenia();
        this.status = p.getStatus();
        this.czyRejestrUczn = p.getCzyRejestrUczn();
    }

}
