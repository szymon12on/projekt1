package pl.samba.lms.przedmioty.wykaz_ocen;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WykazOcen {
    private Integer idPrzedmiotu;
    private String nazwa;
    private Integer ocenaKocnowa;
    private List<Integer> ocenyCzastkowe;


}
