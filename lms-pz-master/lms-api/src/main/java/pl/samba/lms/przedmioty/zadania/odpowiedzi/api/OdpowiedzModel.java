package pl.samba.lms.przedmioty.zadania.odpowiedzi.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.Odpowiedz;
import pl.samba.lms.przedmioty.zadania.zadania.api.ZadaniaController;
import pl.samba.lms.uzytkownicy.uzytkownicy.api.UzytkownicyController;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class OdpowiedzModel extends RepresentationModel<OdpowiedzModel> {
    private final Integer id;
    private final Integer idZadania;
    private final Integer idUcznia;
    private String tresc;
    private String komentarz;
    private Integer ocena;
    private LocalDateTime dataWstawienia;
    private LocalDateTime dataOcenienia;


    public OdpowiedzModel(Odpowiedz o){
        this.id = o.getIdOdpowiedzi();
        this.idZadania = o.getIdZadania();
        this.idUcznia = o.getIdUcznia();
        this.tresc = o.getTresc().toString();
        this.komentarz = o.getKomentarz();
        this.ocena = o.getOcena();
        this.dataWstawienia = o.getDataWstawienia();
        this.dataOcenienia = o.getDataOcenienia();
    }
}
