package pl.samba.lms.przedmioty.rejestracja.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.przedmioty.Przedmiot;
import pl.samba.lms.przedmioty.przedmioty.api.PrzedmiotModel;
import pl.samba.lms.przedmioty.przedmioty.api.PrzedmiotyController;
import pl.samba.lms.przedmioty.rejestracja.UczenPrzedmiot;


public class UczenPrzedmiotModelAssembler extends RepresentationModelAssemblerSupport<UczenPrzedmiot, UczenPrzedmiotModel> {
    public UczenPrzedmiotModelAssembler(){
        super(UczenPrzedmiotController.class, UczenPrzedmiotModel.class);
    }

    @Override
    protected UczenPrzedmiotModel instantiateModel(UczenPrzedmiot entity) {
        return new UczenPrzedmiotModel(entity);
    }
    @Override
    public UczenPrzedmiotModel toModel(UczenPrzedmiot entity) {
        return createModelWithId(entity.getIdPrzedmiotu(), entity);
    }
}
