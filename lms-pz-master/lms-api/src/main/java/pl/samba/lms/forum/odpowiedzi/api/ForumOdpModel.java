package pl.samba.lms.forum.odpowiedzi.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.samba.lms.forum.odpowiedzi.ForumOdp;

import java.time.LocalDateTime;

@Getter
@Relation(value = "ForumOdp", collectionRelation = "ForumOdpowiedzi")
public class ForumOdpModel extends RepresentationModel<ForumOdpModel> {
    private final Integer idOdp;
    private final Integer idWpis;
    private final Integer idUzytk;
    private final String tresc;
    private final LocalDateTime dataWpis;

    public ForumOdpModel(ForumOdp forumOdp) {
        this.idOdp = forumOdp.getIdOdp();
        this.idWpis = forumOdp.getIdWpis();
        this.idUzytk = forumOdp.getIdUzytk();
        this.tresc = forumOdp.getTresc();
        this.dataWpis = forumOdp.getDataWpis();
    }
}