package pl.samba.lms.przedmioty.zadania.odpowiedzi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje.OdpowiedzInterface;
import pl.samba.lms.przedmioty.zadania.ZadanieUtils;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Odpowiedz {
    private final Integer idOdpowiedzi;
    private final Integer idZadania;
    private final Integer idUcznia;
    private List<OdpowiedzInterface> tresc;
    private String komentarz;
    private Integer ocena;
    private LocalDateTime dataWstawienia;
    private LocalDateTime dataOcenienia;

    @JsonCreator
    public Odpowiedz(
            @JsonProperty("idOdpowiedzi") Integer idOdpowiedzi,
            @JsonProperty("idZadania") Integer idZadania,
            @JsonProperty("idUcznia") Integer idUcznia,
            @JsonProperty("tresc") String jsonTresc,
            @JsonProperty("komentarz") String komentarz,
            @JsonProperty("ocena") Integer ocena,
            @JsonProperty("dataWstawienia") LocalDateTime dataWstawienia,
            @JsonProperty("dataOcenienia") LocalDateTime dataOcenienia
            ){
        this.idOdpowiedzi = idOdpowiedzi;
        this.idZadania = idZadania;
        this.idUcznia = idUcznia;
        if(jsonTresc != null) {
            this.tresc = ZadanieUtils.odpowiedziFactory(jsonTresc);
        }
        this.komentarz = komentarz;
        this.ocena = ocena;
        this.dataWstawienia = dataWstawienia;
        this.dataOcenienia = dataOcenienia;
    }

}
