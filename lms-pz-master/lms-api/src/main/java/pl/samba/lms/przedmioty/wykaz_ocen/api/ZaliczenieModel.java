package pl.samba.lms.przedmioty.wykaz_ocen.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.samba.lms.przedmioty.wykaz_ocen.WykazOcen;
import pl.samba.lms.przedmioty.wykaz_ocen.database.Zaliczenie;

@Getter
public class ZaliczenieModel extends RepresentationModel<ZaliczenieModel> {
    private final String okres;
    private final Iterable<WykazOcen> przedmioty;

    public ZaliczenieModel(Zaliczenie z){
        this.okres = z.getOkres();
        this.przedmioty = z.getPrzedmioty();
    }
}
