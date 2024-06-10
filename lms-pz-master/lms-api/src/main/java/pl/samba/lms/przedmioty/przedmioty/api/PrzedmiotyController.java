package pl.samba.lms.przedmioty.przedmioty.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.przedmioty.przedmioty.Przedmiot;
import pl.samba.lms.przedmioty.przedmioty.database.PrzedmiotRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.PRZEDMIOT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PrzedmiotyController implements ControllerInterface<Przedmiot, PrzedmiotModel> {

    private final PrzedmiotRepository dataSet;


//    @Override
//    @GetMapping(PathType.ALL)
//    public ResponseEntity<CollectionModel<PrzedmiotModel>> get(
//            @RequestParam(required = false) Integer size,
//            @RequestParam(required = false) Integer page
//    ) {
//        Iterable<Przedmiot> przedmioty;
//
//        if(size != null || page != null ){
//            String params =  (size == null? "0" : size) + ";" +
//                    (page == null? "0" : page);
//            przedmioty = dataSet.getAll(params);
//        }
//        else przedmioty = dataSet.getAll();
//
//        if(przedmioty.iterator().hasNext()){
//            List<PrzedmiotModel> listaModeli = new ArrayList<>();
//
//            for(Przedmiot przedmiot: przedmioty){
//                listaModeli.add(new PrzedmiotModelAssembler().toModel(przedmiot));
//            }
//
//            CollectionModel<PrzedmiotModel> kolekcjaModeli = CollectionModel.of(listaModeli);
//
//            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(PrzedmiotyController.class).get(size, page))
//                    .withRel("przedmioty").withTitle("lista_przedmiotow"));
//            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
//        }
//        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//    }

    @GetMapping(PathType.ALL)
    public ResponseEntity<CollectionModel<PrzedmiotModel>> get(
            @RequestParam(required = false) Integer idUcznia,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        String params;
        if(size == null && page == null){
            params = ";;" + (idUcznia == null? "-1" : idUcznia);
        }
        else {
            params =  (size == null? "0" : size) + ";" +
                    (page == null? "0" : page) +
                    (idUcznia == null? "-1" : idUcznia);
        }

        Iterable<Przedmiot> przedmioty = dataSet.getAll(params);
        if(przedmioty.iterator().hasNext()){
            List<PrzedmiotModel> listaModeli = new ArrayList<>();

            for(Przedmiot przedmiot: przedmioty){
                listaModeli.add(new PrzedmiotModelAssembler().toModel(przedmiot));
            }

            CollectionModel<PrzedmiotModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(PrzedmiotyController.class).get(idUcznia,size, page))
                    .withRel("przedmioty").withTitle("lista_przedmiotow"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Deprecated
    @Override
    public ResponseEntity<CollectionModel<PrzedmiotModel>> get(Integer size, Integer page) throws Exception {
        return null;
    }

    @Override
    @GetMapping(PathType.ID)
    public ResponseEntity<PrzedmiotModel> get(@PathVariable("id") Integer id) {
        Optional<Przedmiot> optPrzedmiot = Optional.ofNullable(dataSet.getById(id));
        if(optPrzedmiot.isPresent()){
            PrzedmiotModel model = new PrzedmiotModelAssembler().toModel(optPrzedmiot.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public ResponseEntity<PrzedmiotModel> get(@RequestParam(name = "kod") String kod){
        kod = new String(Base64.getDecoder().decode(kod));
        Optional<Przedmiot> optPrzedmiot = Optional.ofNullable(dataSet.getByUnique(kod));
        if(optPrzedmiot.isPresent()){
            PrzedmiotModel model = new PrzedmiotModelAssembler().toModel(optPrzedmiot.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @Override
    @DeleteMapping(PathType.ID)
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody Przedmiot data) {
        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(PrzedmiotyController.class).get(id))
                        .withRel("nowy")
                        .withTitle("przedmiot")),
                HttpStatus.CREATED);
    }

    @Override
    @PatchMapping(PathType.ID)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody Przedmiot data
    ) {
        Przedmiot current = dataSet.getById(id);

        if(data.getNazwa() != null){
            current.setNazwa(data.getNazwa());
        }
        if(data.getIdProwadzacego() != null){
            current.setIdProwadzacego(data.getIdProwadzacego());
        }
        if(data.getLimit() != null){
            current.setLimit(data.getLimit());
        }
        if(data.getOpis() != null){
            current.setOpis(data.getOpis());
        }
        if(data.getWarunkiZaliczenia() != null){
            current.setWarunkiZaliczenia(data.getWarunkiZaliczenia());
        }
        if(data.getIdOkresu() != null) {
            current.setIdOkresu(data.getIdOkresu());
        }
        if(data.getStatus() != null) {
            current.setStatus(data.getStatus());
        }

        id = dataSet.update(current);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(PrzedmiotyController.class).get(id))
                        .withRel("zaktualizowany")
                        .withTitle("przedmiot")),
                HttpStatus.OK);
    }
}
