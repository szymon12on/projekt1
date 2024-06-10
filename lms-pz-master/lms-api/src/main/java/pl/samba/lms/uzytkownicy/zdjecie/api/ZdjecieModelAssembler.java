package pl.samba.lms.uzytkownicy.zdjecie.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.uzytkownicy.zdjecie.Zdjecie;

public class ZdjecieModelAssembler extends RepresentationModelAssemblerSupport<Zdjecie, ZdjecieModel> {
    public ZdjecieModelAssembler(){
        super(ZdjeciaController.class, ZdjecieModel.class);
    }

    @Override
    protected ZdjecieModel instantiateModel(Zdjecie entity) {
        return new ZdjecieModel(entity);
    }
    @Override
    public ZdjecieModel toModel(Zdjecie entity) {
        return createModelWithId(entity.getIdZdjecia(), entity);
    }
}
