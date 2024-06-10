package pl.samba.lms.powiadomienia.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.powiadomienia.Powiadomienie;
import pl.samba.lms.przedmioty.okresy.Okres;

public class PowiadomienieModelAssembler extends RepresentationModelAssemblerSupport<Powiadomienie, PowiadomienieModel> {
    public PowiadomienieModelAssembler(){
        super(PowiadomieniaController.class, PowiadomienieModel.class);
    }

    @Override
    protected PowiadomienieModel instantiateModel(Powiadomienie entity) {
        return new PowiadomienieModel(entity);
    }
    @Override
    public PowiadomienieModel toModel(Powiadomienie entity) {
        return createModelWithId(entity.getIdPowiadomienia(), entity);
    }
}
