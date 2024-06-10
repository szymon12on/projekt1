package pl.samba.lms.przedmioty.zadania.zadania.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.przedmioty.przedmioty.api.PrzedmiotyController;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;
import pl.samba.lms.utils.constants.TypyZadan;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class ZadanieModel extends RepresentationModel<ZadanieModel> {
    private final Integer id;
    private final String przedmiot;
    private final TypyZadan typZadania;
    private final String opis;
    private final LocalDateTime dataWstawienia;
    private final LocalDateTime dataPoczatku;
    private final LocalDateTime dataKonca;
    private final String tresc;
    private List<Integer> idUczniowKtorzyOdpowiedzieli;

    public ZadanieModel(Zadanie z){
        this.id = z.getIdZadania();
        this.typZadania = z.getTypyZadania();
        this.opis = z.getOpis();
        this.dataWstawienia = z.getDataWstawienia();
        this.dataPoczatku = z.getDataPoczatku();
        this.dataKonca = z.getDataKonca();
        this.tresc = z.getTresc().toString();
        this.idUczniowKtorzyOdpowiedzieli = z.getIdUczniowKtorzyOdpowiedzieli();
        this.przedmiot = WebMvcLinkBuilder
                .linkTo(methodOn(PrzedmiotyController.class).get(z.getIdPrzedmiotu()))
                .toUri()
                .toString();
    }
}
