package pl.samba.lms.przedmioty.zadania.zadania.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;

public class ZadanieModelAssembler extends RepresentationModelAssemblerSupport<Zadanie, ZadanieModel> {
    public ZadanieModelAssembler(){
        super(ZadaniaController.class, ZadanieModel.class);
    }

    @Override
    protected ZadanieModel instantiateModel(Zadanie entity) {
        return new ZadanieModel(entity);
    }
    @Override
    public ZadanieModel toModel(Zadanie entity) {
        return createModelWithId(entity.getIdZadania(), entity);
    }
}
