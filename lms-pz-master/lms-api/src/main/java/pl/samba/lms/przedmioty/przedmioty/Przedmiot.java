package pl.samba.lms.przedmioty.przedmioty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.utils.constants.Status;


@Getter
@Setter
public class Przedmiot {
    private final Integer idPrzedmiotu;
    private final String kod;
    private String nazwa;
    private Integer idProwadzacego;
    private Integer limit;
    private String opis;
    private String warunkiZaliczenia;
    private Integer idOkresu;
    private Status status;
    private Boolean czyRejestrUczn;

    @JsonCreator
    public Przedmiot(
            Integer idPrzedmiotu,
            String kod,
            @JsonProperty("nazwa") String nazwa,
            @JsonProperty("idProwadzacego") Integer idProwadzacego,
            @JsonProperty("limit") Integer limit,
            @JsonProperty("opis") String opis,
            @JsonProperty("warunkiZaliczenia") String warunkiZaliczenia,
            @JsonProperty("idOkresu") Integer idOkresu,
            @JsonProperty("status") Status status,
            @JsonProperty("czyRejestrUczn") Boolean czyRejestrUczn
    ){
        this.idPrzedmiotu = idPrzedmiotu;
        this.kod = kod;
        this.nazwa = nazwa;
        this.idProwadzacego = idProwadzacego;
        this.limit = limit;
        this.opis = opis;
        this.warunkiZaliczenia = warunkiZaliczenia;
        this.idOkresu =  idOkresu;
        this.status = status;
        this.czyRejestrUczn = czyRejestrUczn;
    }
}
