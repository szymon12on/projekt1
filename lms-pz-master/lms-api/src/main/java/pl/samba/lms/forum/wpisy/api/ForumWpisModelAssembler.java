package pl.samba.lms.forum.wpisy.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.forum.wpisy.ForumWpis;

public class ForumWpisModelAssembler extends RepresentationModelAssemblerSupport<ForumWpis, ForumWpisModel> {

    public ForumWpisModelAssembler() {
        super(ForumWpisyController.class, ForumWpisModel.class);
    }

    @Override
    protected ForumWpisModel instantiateModel(ForumWpis entity) {
        return new ForumWpisModel(entity);
    }

    @Override
    public ForumWpisModel toModel(ForumWpis entity) {
        return createModelWithId(entity.getIdWpis(), entity);
    }
}