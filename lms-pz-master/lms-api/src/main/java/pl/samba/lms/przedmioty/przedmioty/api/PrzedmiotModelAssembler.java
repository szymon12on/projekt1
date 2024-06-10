package pl.samba.lms.przedmioty.przedmioty.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.przedmioty.Przedmiot;


public class PrzedmiotModelAssembler extends RepresentationModelAssemblerSupport<Przedmiot, PrzedmiotModel> {
    public PrzedmiotModelAssembler(){
        super(PrzedmiotyController.class, PrzedmiotModel.class);
    }

    @Override
    protected PrzedmiotModel instantiateModel(Przedmiot entity) {
        return new PrzedmiotModel(entity);
    }
    @Override
    public PrzedmiotModel toModel(Przedmiot entity) {
        return createModelWithId(entity.getIdPrzedmiotu(), entity);
    }
}
