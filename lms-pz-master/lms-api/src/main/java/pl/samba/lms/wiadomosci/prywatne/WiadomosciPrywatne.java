package pl.samba.lms.wiadomosci.prywatne;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class WiadomosciPrywatne {
    private  Integer idWiadomosci;
    private Integer idNadawcy;
    private Integer idOdbiorcy;
    private String tresc;
    private LocalDateTime dataWyslania;
    private Integer idFlagi;


    public WiadomosciPrywatne() {

    }

    @JsonCreator
    public WiadomosciPrywatne(
            @JsonProperty("idWiadomosci") Integer idWiadomosci,
            @JsonProperty("idNadawcy") Integer idNadawcy,
            @JsonProperty("idOdbiorcy") Integer idOdbiorcy,
            @JsonProperty("tresc") String tresc,
            @JsonProperty("dataWyslania") LocalDateTime dataWyslania,
            @JsonProperty("idFlagi") Integer idFlagi
    ){
        this.idWiadomosci = idWiadomosci;
        this.idNadawcy = idNadawcy;
        this.idOdbiorcy = idOdbiorcy;
        this.tresc = tresc;
        this.dataWyslania = dataWyslania;
        this.idFlagi = idFlagi;
    }
}
