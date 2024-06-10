package pl.samba.lms.przedmioty.okresy.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.przedmioty.okresy.Okres;
import pl.samba.lms.przedmioty.okresy.database.OkresRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;



import java.util.LinkedList;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.OKRES, produces = MediaType.APPLICATION_JSON_VALUE)
public class OkresyController implements ControllerInterface<Okres, OkresModel> {

    private final OkresRepository dataSet;
    public OkresyController(OkresRepository dataSet){
        this.dataSet = dataSet;
    }

    @Override
    @GetMapping(PathType.ALL)
    public ResponseEntity<CollectionModel<OkresModel>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false)Integer page
    ) {
        Iterable<Okres> okresy;
        if(size != null || page != null ){
            String params =  (size == null? "0" : size) + ";" +
                    (page == null? "0" : page);
            okresy = dataSet.getAll(params);
        }
        else okresy = dataSet.getAll();

        if(okresy.iterator().hasNext()){
            LinkedList<OkresModel> okresyModele = new LinkedList<>();

            for (Okres okres :
                    okresy) {
                okresyModele.add(new OkresModelAssembler().toModel(okres));
            }

            CollectionModel<OkresModel> modele = CollectionModel.of(okresyModele);
            modele.add(WebMvcLinkBuilder
                    .linkTo(methodOn(OkresyController.class).get(size, page))
                    .withRel("okresy").withTitle("lista_okresow"));

            return new ResponseEntity<>(modele, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    @GetMapping(PathType.ID)
    public ResponseEntity<OkresModel> get(@PathVariable("id") Integer id) {
        Optional<Okres> optOkres = Optional.ofNullable(dataSet.getById(id));
        if(optOkres.isPresent()){
            OkresModel model = new OkresModelAssembler().toModel(optOkres.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    @DeleteMapping(PathType.ID)
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody Okres data) {
        Integer id =  dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(OkresyController.class).get(id))
                        .withRel("nowy").withTitle("okres")),
                HttpStatus.CREATED);
    }

    @Override
    @PatchMapping(path=PathType.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> patch(
            @PathVariable("id")Integer id,
            @RequestBody Okres data) {
        Okres current = dataSet.getById(id);

        if(data.getKod() != null){
            current.setKod(data.getKod());
        }
        if(data.getDataPoczatku() != null){
            current.setDataPoczatku(data.getDataPoczatku());
        }
        if(data.getDataKonca() != null){
            current.setDataKonca(data.getDataKonca());
        }

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(OkresyController.class).get(id))
                        .withRel("zaktualizowany").withTitle("okres")),
                HttpStatus.OK);
    }
}
