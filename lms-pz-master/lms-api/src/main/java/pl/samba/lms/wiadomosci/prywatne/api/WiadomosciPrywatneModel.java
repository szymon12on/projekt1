package pl.samba.lms.wiadomosci.prywatne.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.samba.lms.wiadomosci.prywatne.WiadomosciPrywatne;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class WiadomosciPrywatneModel extends RepresentationModel<WiadomosciPrywatneModel> {
    private final Integer idWiadomosci;
    private final Integer idNadawcy;
    private final Integer idOdbiorcy;
    private final String tresc;
    private final LocalDateTime dataWyslania;
    private final Integer idFlagi;

    public WiadomosciPrywatneModel(WiadomosciPrywatne wp) {
        this.idWiadomosci = wp.getIdWiadomosci();
        this.idNadawcy = wp.getIdNadawcy();
        this.idOdbiorcy = wp.getIdOdbiorcy();
        this.tresc = wp.getTresc();
        this.dataWyslania = wp.getDataWyslania();
        this.idFlagi = wp.getIdFlagi();
    }
}