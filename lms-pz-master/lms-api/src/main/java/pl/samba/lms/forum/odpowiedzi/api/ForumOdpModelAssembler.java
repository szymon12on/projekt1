package pl.samba.lms.forum.odpowiedzi.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.forum.odpowiedzi.ForumOdp;

public class ForumOdpModelAssembler extends RepresentationModelAssemblerSupport<ForumOdp, ForumOdpModel> {

    public ForumOdpModelAssembler() {
        super(ForumOdpController.class, ForumOdpModel.class);
    }

    @Override
    protected ForumOdpModel instantiateModel(ForumOdp entity) {
        return new ForumOdpModel(entity);
    }

    @Override
    public ForumOdpModel toModel(ForumOdp entity) {
        return createModelWithId(entity.getIdOdp(), entity);
    }
}