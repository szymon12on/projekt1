package pl.samba.lms.przedmioty.wykaz_ocen.api;


import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.samba.lms.przedmioty.wykaz_ocen.WykazOcen;

import java.util.List;

@Getter
public class WykazOcenModel extends RepresentationModel<WykazOcenModel> {
    private final Integer idPrzedmiotu;
    private final String nazwa;
    private final Integer ocenaKocnowa;
    private final List<Integer> ocenyCzastkowe;

    public WykazOcenModel(WykazOcen z){
        this.idPrzedmiotu =  z.getIdPrzedmiotu();
        this.nazwa = z.getNazwa();
        this.ocenaKocnowa = z.getOcenaKocnowa();
        this.ocenyCzastkowe = z.getOcenyCzastkowe();
    }

}
