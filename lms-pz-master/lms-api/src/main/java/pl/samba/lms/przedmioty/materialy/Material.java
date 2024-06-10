package pl.samba.lms.przedmioty.materialy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Material {
    private final Integer idMaterialu;
    private final Integer idPrzedmiotu;
    private LocalDateTime dataWstawienia;
    private final Integer lp;
    private String temat;
    private byte[] plik;
    private String nazwaPliku;
    private String ext;
    private String opis;
    private Boolean widocznosc;
    private Integer idZadania;

    @JsonCreator
    public Material(
            @JsonProperty("idPrzedmiotu") Integer idPrzedmiotu,
            @JsonProperty("dataWstawienia") LocalDateTime dataWstawienia,
            @JsonProperty("temat") String temat,
            @JsonProperty("plik") byte[] plik,
            @JsonProperty("nazwaPliku") String nazwaPliku,
            @JsonProperty("ext") String ext,
            @JsonProperty("opis") String opis,
            @JsonProperty("widocznosc") Boolean widocznosc,
            @JsonProperty("idZadania") Integer idZadania
    ){
        this.idMaterialu = null;
        this.idPrzedmiotu = idPrzedmiotu;
        this.dataWstawienia = dataWstawienia;
        this.lp = null;
        this.temat = temat;
        this.plik = plik;
        this.nazwaPliku = nazwaPliku;
        this.ext = ext;
        this.opis = opis;
        this.widocznosc = widocznosc;
        this.idZadania = idZadania;
    }
}
