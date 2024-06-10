package pl.samba.lms.forum.wpisy.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.forum.wpisy.ForumWpis;
import pl.samba.lms.forum.wpisy.database.ForumWpisRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.ROOT + "/forum/wpisy", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class ForumWpisyController implements ControllerInterface<ForumWpis, ForumWpisModel> {

    private final ForumWpisRepository dataSet;

    @Override
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<ForumWpisModel>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        Iterable<ForumWpis> wpisy;

        if (size != null || page != null) {
            String params = (size == null ? "0" : size) + ";" +
                    (page == null ? "0" : page);
            wpisy = dataSet.getAll(params);

            if (wpisy == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            wpisy = dataSet.getAll();
        }

        if (wpisy.iterator().hasNext()) {
            List<ForumWpisModel> listaModeli = new ArrayList<>();

            for (ForumWpis wpis : wpisy) {
                listaModeli.add(new ForumWpisModelAssembler().toModel(wpis));
            }

            CollectionModel<ForumWpisModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(ForumWpisyController.class).get(size, page))
                    .withRel("wpisy").withTitle("lista_wpisow"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping(PathType.ID)
    public ResponseEntity<ForumWpisModel> get(@PathVariable("id") Integer id) {
        Optional<ForumWpis> optionalForumWpis = Optional.ofNullable(dataSet.getById(id));
        if (optionalForumWpis.isPresent()) {
            ForumWpisModel model = new ForumWpisModelAssembler().toModel(optionalForumWpis.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody ForumWpis data) {
        // Sprawdź, czy data wpisu jest nullem i ustaw bieżącą datę
        if (data.getDataWpis() == null) {
            data.setDataWpis(LocalDateTime.now());
        }
        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(ForumWpisyController.class).get(id))
                        .withRel("nowy")
                        .withTitle("wpis")),
                HttpStatus.CREATED);
    }

    @Override
    @PatchMapping(PathType.ID)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody ForumWpis data
    ) {
        ForumWpis current = dataSet.getById(id);

        if(data.getTemat() != null){
            current.setTemat(data.getTemat());
        }
        if(data.getTresc() != null){
            current.setTresc(data.getTresc());
        }

        id = dataSet.update(current);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(ForumWpisyController.class).get(id))
                        .withRel("zaktualizowany")
                        .withTitle("wpis")),
                HttpStatus.OK);
    }

    @Override
    @DeleteMapping(PathType.ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }
}