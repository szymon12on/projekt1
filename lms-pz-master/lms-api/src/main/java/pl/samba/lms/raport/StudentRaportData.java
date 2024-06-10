package pl.samba.lms.raport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentRaportData {
    private String Imie;
    private String Nazwisko;
    private List<Integer> OcenyCzastkowe; // Lista ocen cząstkowych
    private Integer OcenaKoncowa; // Ocena końcową

    @JsonCreator
    public StudentRaportData(
            @JsonProperty("imie") String name,
            @JsonProperty("nazwisko") String surname,
            @JsonProperty("ocenyCzastkowe") List<Integer> partialGrades,
            @JsonProperty("ocenaKoncowa") Integer  finalGrade) {
        this.Imie = name;
        this.Nazwisko = surname;
        this.OcenyCzastkowe = partialGrades;
        this.OcenaKoncowa = finalGrade;
    }
}

