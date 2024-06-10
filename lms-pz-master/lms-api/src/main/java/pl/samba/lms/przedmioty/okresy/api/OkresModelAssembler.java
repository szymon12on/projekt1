package pl.samba.lms.przedmioty.okresy.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.okresy.Okres;
public class OkresModelAssembler extends RepresentationModelAssemblerSupport<Okres, OkresModel> {
    public OkresModelAssembler(){
        super(OkresyController.class, OkresModel.class);
    }

    @Override
    protected OkresModel instantiateModel(Okres entity) {
        return new OkresModel(entity);
    }
    @Override
    public OkresModel toModel(Okres entity) {
        return createModelWithId(entity.getIdOkresu(), entity);
    }
}
