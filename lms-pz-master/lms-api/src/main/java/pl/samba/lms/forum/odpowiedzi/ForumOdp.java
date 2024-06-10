package pl.samba.lms.forum.odpowiedzi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ForumOdp {
    private final Integer idOdp;
    private final Integer idWpis;
    private final Integer idUzytk;
    private  String tresc;
    private  LocalDateTime dataWpis;


    @JsonCreator
    public ForumOdp(
            @JsonProperty("idOdpowiedzi") Integer idOdpowiedzi,
            @JsonProperty("idWpisu") Integer idWpis,
            @JsonProperty("idUzytkownika") Integer idUzytkownika,
            @JsonProperty("tresc") String tresc,
            @JsonProperty("dataWpis") LocalDateTime dataWpisu
    ){
        this.idWpis = idWpis;
        this.idOdp = idOdpowiedzi;
        this.idUzytk = idUzytkownika;
        this.tresc = tresc;
        this.dataWpis = dataWpisu;
    }
}

