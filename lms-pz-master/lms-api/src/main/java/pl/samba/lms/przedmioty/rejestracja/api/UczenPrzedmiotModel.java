package pl.samba.lms.przedmioty.rejestracja.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.samba.lms.przedmioty.przedmioty.api.PrzedmiotyController;
import pl.samba.lms.przedmioty.rejestracja.UczenPrzedmiot;
import pl.samba.lms.uzytkownicy.uzytkownicy.api.UzytkownicyController;
import pl.samba.lms.uzytkownicy.zdjecie.api.ZdjeciaController;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Relation(value = "uczen_i_przedmiot", collectionRelation = "powiazania")
public class UczenPrzedmiotModel extends RepresentationModel<UczenPrzedmiotModel> {

    private Integer ocena;
    private LocalDateTime dataWystawOceny;

    @JsonCreator
    public UczenPrzedmiotModel(UczenPrzedmiot data){
        this.ocena =  data.getOcena();
        this.dataWystawOceny = data.getDataWystawOceny();

        add(WebMvcLinkBuilder
                .linkTo(methodOn(UzytkownicyController.class)
                        .get(data.getIdUcznia()))
                .withRel("uczen"));
        add(WebMvcLinkBuilder
                .linkTo(methodOn(PrzedmiotyController.class)
                    .get(data.getIdPrzedmiotu()))
                .withRel("przedmiot"));
    }
 }
