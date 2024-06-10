package pl.samba.lms.przedmioty.materialy.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.przedmioty.materialy.Material;

public class MaterialModelAssembler extends RepresentationModelAssemblerSupport<Material, MaterialModel> {
    public MaterialModelAssembler(){
        super(MaterialyController.class, MaterialModel.class);
    }

    @Override
    protected MaterialModel instantiateModel(Material entity) {
        return new MaterialModel(entity);
    }
    @Override
    public MaterialModel toModel(Material entity) {
        return createModelWithId(entity.getIdMaterialu(), entity);
    }
}
