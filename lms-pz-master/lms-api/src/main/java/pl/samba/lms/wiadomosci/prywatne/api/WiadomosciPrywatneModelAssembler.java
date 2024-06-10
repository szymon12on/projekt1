package pl.samba.lms.wiadomosci.prywatne.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.wiadomosci.prywatne.WiadomosciPrywatne;
import pl.samba.lms.wiadomosci.prywatne.api.WiadomosciPrywatneModel;
import pl.samba.lms.wiadomosci.prywatne.api.WiadomosciPrywatneController;


public class WiadomosciPrywatneModelAssembler extends RepresentationModelAssemblerSupport<WiadomosciPrywatne, WiadomosciPrywatneModel> {
    public WiadomosciPrywatneModelAssembler(){
        super(WiadomosciPrywatneController.class, WiadomosciPrywatneModel.class);
    }

    @Override
    protected WiadomosciPrywatneModel instantiateModel(WiadomosciPrywatne entity) {
        return new WiadomosciPrywatneModel(entity);
    }
    @Override
    public WiadomosciPrywatneModel toModel(WiadomosciPrywatne entity) {
        return createModelWithId(entity.getIdWiadomosci(), entity);
    }
}

