package pl.samba.lms.przedmioty.rejestracja;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UczenPrzedmiot {
    private final Integer idEncji;
    private final Integer idPrzedmiotu;
    private final Integer idUcznia;
    private Integer ocena;
    private final LocalDateTime dataWystawOceny;

    @JsonCreator
    public UczenPrzedmiot(
            @JsonProperty("idPrzedmiotu") Integer idPrzedmiotu,
            @JsonProperty("idUcznia") Integer idUcznia,
            @JsonProperty("ocena") Integer ocena,
            @JsonProperty("dataWystawOceny") LocalDateTime dataWystawOceny
    ){
        this.idEncji = null;
        this.idPrzedmiotu = idPrzedmiotu;
        this.idUcznia = idUcznia;
        this.ocena =  ocena;
        this.dataWystawOceny = dataWystawOceny;
    }
 }
