package pl.samba.lms.forum.wpisy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ForumWpis {
    private final Integer idWpis;
    private final Integer idPrzedm;
    private final Integer idUzytk;
    private  String temat;
    private  String tresc;
    private LocalDateTime dataWpis;


    @JsonCreator
    public ForumWpis(
            @JsonProperty("idWpisu") Integer idWpis,
            @JsonProperty("idPrzedmiotu") Integer idPrzedmiotu,
            @JsonProperty("idUzytkownika") Integer idUzytkownika,
            @JsonProperty("temat") String temat,
            @JsonProperty("tresc") String tresc,
            @JsonProperty("dataWpis") LocalDateTime dataWpisu
    ){
         this.idWpis = idWpis;
         this.idPrzedm = idPrzedmiotu;
         this.idUzytk = idUzytkownika;
         this.temat = temat;
         this.tresc = tresc;
         this.dataWpis = dataWpisu;
    }
}
