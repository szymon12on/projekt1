package pl.samba.lms.przedmioty.okresy.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.samba.lms.przedmioty.okresy.Okres;

import java.time.LocalDateTime;

@Getter
public class OkresModel extends RepresentationModel<OkresModel> {
    private final Integer id;
    private final String kod;
    private final LocalDateTime dataPoczatku;
    private final LocalDateTime dataKonca;


    public OkresModel(Okres o){
        this.id = o.getIdOkresu();
        this.kod = o.getKod();
        this.dataPoczatku = o.getDataPoczatku();
        this.dataKonca = o.getDataKonca();
    }

}
