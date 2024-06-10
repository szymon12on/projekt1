package pl.samba.lms.przedmioty.zadania.zadania;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.przedmioty.zadania.ZadanieUtils;
import pl.samba.lms.przedmioty.zadania.zadania.rodzaje.ZadanieInterface;
import pl.samba.lms.utils.constants.TypyZadan;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Zadanie {
    private final Integer idZadania;
    private final Integer idPrzedmiotu;
    private String opis;
    private TypyZadan typyZadania;
    private LocalDateTime dataWstawienia;
    private LocalDateTime dataPoczatku;
    private LocalDateTime dataKonca;
    private List<Integer> idUczniowKtorzyOdpowiedzieli;

    private List<ZadanieInterface> tresc;

    @JsonCreator
    public Zadanie(
            @JsonProperty("idZadania") Integer idZadania,
            @JsonProperty("idPrzedmiotu") Integer idPrzedmiotu,
            @JsonProperty("opis") String opis,
            @JsonProperty("typZadania") TypyZadan typZadania,
            @JsonProperty("dataWstawienia") LocalDateTime dataWstawienia,
            @JsonProperty("dataPoczatku") LocalDateTime dataPoczatku,
            @JsonProperty("dataKonca") LocalDateTime dataKonca,
            @JsonProperty("idUczniowKtorzyOdpowiedzieli") List<Integer> idUczniowKtorzyOdpowiedzieli,
            @JsonProperty("tresc") String jsonTresc
    ){
        this.idZadania = idZadania;
        this.idPrzedmiotu = idPrzedmiotu;
        this.opis = opis;
        this.typyZadania = typZadania;
        this.dataWstawienia = dataWstawienia;
        this.dataPoczatku = dataPoczatku;
        this.dataKonca = dataKonca;
        this.idUczniowKtorzyOdpowiedzieli = (idUczniowKtorzyOdpowiedzieli == null) ? new ArrayList<>() : idUczniowKtorzyOdpowiedzieli;
        if (jsonTresc == null){
            this.tresc = null;
        }
        else this.tresc = ZadanieUtils.zadaniaFactory(jsonTresc);
    }
}
