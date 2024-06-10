package pl.samba.lms.przedmioty.zadania.odpowiedzi.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.Odpowiedz;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;

public class OdpowiedzModelAssembler extends RepresentationModelAssemblerSupport<Odpowiedz, OdpowiedzModel> {
    public OdpowiedzModelAssembler(){
        super(OdpowiedziController.class, OdpowiedzModel.class);
    }

    @Override
    protected OdpowiedzModel instantiateModel(Odpowiedz entity) {
        return new OdpowiedzModel(entity);
    }
    @Override
    public OdpowiedzModel toModel(Odpowiedz entity) {
        return createModelWithId(entity.getIdOdpowiedzi(), entity);
    }
}
