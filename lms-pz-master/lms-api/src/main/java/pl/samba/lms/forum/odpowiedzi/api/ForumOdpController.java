package pl.samba.lms.forum.odpowiedzi.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.samba.lms.forum.odpowiedzi.ForumOdp;
import pl.samba.lms.forum.odpowiedzi.database.ForumOdpRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.ROOT + "/forum/odpowiedzi", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class ForumOdpController implements ControllerInterface<ForumOdp, ForumOdpModel> {

    private final ForumOdpRepository dataSet;

    @Override
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<ForumOdpModel>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        Iterable<ForumOdp> odpowiedzi;

        if (size != null || page != null) {
            String params = (size == null ? "0" : size) + ";" +
                    (page == null ? "0" : page);
            odpowiedzi = dataSet.getAll(params);

            if (odpowiedzi == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            odpowiedzi = dataSet.getAll();
        }

        if (odpowiedzi.iterator().hasNext()) {
            List<ForumOdpModel> listaModeli = new ArrayList<>();

            for (ForumOdp odpowiedz : odpowiedzi) {
                listaModeli.add(new ForumOdpModelAssembler().toModel(odpowiedz));
            }

            CollectionModel<ForumOdpModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(pl.samba.lms.forum.odpowiedzi.api.ForumOdpController.class).get(size, page))
                    .withRel("odpowiedzi").withTitle("lista_odpowiedzi"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping(PathType.ID)
    public ResponseEntity<ForumOdpModel> get(@PathVariable("id") Integer id) {
        Optional<ForumOdp> optionalForumOdp = Optional.ofNullable(dataSet.getById(id));
        if (optionalForumOdp.isPresent()) {
            ForumOdpModel model = new ForumOdpModelAssembler().toModel(optionalForumOdp.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody ForumOdp data) {
        // Sprawdź, czy data odpowiedzi jest null i ustaw bieżącą datę
        if (data.getDataWpis() == null) {
            data.setDataWpis(LocalDateTime.now());
        }

        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(ForumOdpController.class).get(id))
                        .withRel("nowa")
                        .withTitle("odpowiedź")),
                HttpStatus.CREATED);
    }

    @Override
    @PatchMapping(PathType.ID)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody ForumOdp data
    ) {
        ForumOdp current = dataSet.getById(id);

        if(data.getTresc() != null){
            current.setTresc(data.getTresc());
        }

        id = dataSet.update(current);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(ForumOdpController.class).get(id))
                        .withRel("zaktualizowana")
                        .withTitle("odpowiedź")),
                HttpStatus.OK);
    }

    @Override
    @DeleteMapping(PathType.ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }
}
