package pl.samba.lms.forum.wpisy.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.samba.lms.forum.wpisy.ForumWpis;

import java.time.LocalDateTime;

@Getter
@Relation(value = "ForumWpis", collectionRelation = "ForumWpisy")
public class ForumWpisModel extends RepresentationModel<ForumWpisModel> {
    private final Integer idWpis;
    private final Integer idPrzedm;
    private final Integer idUzytk;
    private final String temat;
    private final String tresc;
    private final LocalDateTime dataWpis;

    public ForumWpisModel(ForumWpis forumWpis) {
        this.idWpis = forumWpis.getIdWpis();
        this.idPrzedm = forumWpis.getIdPrzedm();
        this.idUzytk = forumWpis.getIdUzytk();
        this.temat = forumWpis.getTemat();
        this.tresc = forumWpis.getTresc();
        this.dataWpis = forumWpis.getDataWpis();
    }
}