package pl.samba.lms.powiadomienia.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.powiadomienia.Powiadomienie;
import pl.samba.lms.przedmioty.okresy.Okres;
import pl.samba.lms.przedmioty.przedmioty.api.PrzedmiotyController;
import pl.samba.lms.utils.constants.Flagi;
import pl.samba.lms.uzytkownicy.uzytkownicy.api.UzytkownicyController;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class PowiadomienieModel extends RepresentationModel<PowiadomienieModel> {
    private final Integer id;
    private final LocalDateTime dataWstawienia;
    private final String tresc;
    private final Flagi flaga;


    public PowiadomienieModel(Powiadomienie p){
        id= p.getIdPowiadomienia();
        this.dataWstawienia = p.getDataWstawienia();
        this.tresc = p.getTresc();
        this.flaga = p.getFlaga();

        add(WebMvcLinkBuilder.linkTo(
                methodOn(UzytkownicyController.class)
                        .get(p.getIdOdbiorcy())).withRel("odbiorca"));

    }

}
