package pl.samba.lms.uzytkownicy.uzytkownicy.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;

import pl.samba.lms.uzytkownicy.uzytkownicy.database.UzytkownikRepository;
import pl.samba.lms.uzytkownicy.zdjecie.Zdjecie;

import java.util.Base64;
import java.util.LinkedList;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.UZYTKOWNIK, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UzytkownicyController implements ControllerInterface<Uzytkownik, UzytkownikModel> {

    private final UzytkownikRepository dataSet;


    @GetMapping(PathType.ALL)
    @Override
    public ResponseEntity<CollectionModel<UzytkownikModel>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        Iterable<Uzytkownik> users;

        if(size != null || page != null ){
            String params =  (size == null? "0" : size) + ";" +
                    (page == null? "0" : page);
            users = dataSet.getAll(params);
        }
        else users = dataSet.getAll();


        if(users.iterator().hasNext()){
            LinkedList<UzytkownikModel> userModelsList = new LinkedList<>();

            for (Uzytkownik user :
                    users) {
                userModelsList.add(new UzytkownikModelAssembler().toModel(user));
            }

            CollectionModel<UzytkownikModel> usersModel = CollectionModel.of(userModelsList);
            usersModel.add(WebMvcLinkBuilder
                    .linkTo(methodOn(UzytkownicyController.class).get(size, page))
                    .withRel("uzytkownicy").withTitle("lista_uzytkownikow"));

            return new ResponseEntity<>(usersModel, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(PathType.ID)
    @Override
    public ResponseEntity<UzytkownikModel> get(@PathVariable("id") Integer id) {
        Optional<Uzytkownik> optUzytkownik = Optional.ofNullable(dataSet.getById(id));
        if(optUzytkownik.isPresent()){
            UzytkownikModel model = new UzytkownikModelAssembler().toModel(optUzytkownik.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public ResponseEntity<UzytkownikModel> get(@RequestParam(name = "login") String login){
        login = new String(Base64.getDecoder().decode(login));
        Optional<Uzytkownik> optUzytkownik = Optional.ofNullable(dataSet.getByUnique(login));
        if(optUzytkownik.isPresent()){
            UzytkownikModel model = new UzytkownikModelAssembler().toModel(optUzytkownik.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(PathType.ID)
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Object> post(@RequestBody Uzytkownik data) {
        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(UzytkownicyController.class).get(id))
                        .withRel("nowy").withTitle("uzytkownik")),
                HttpStatus.CREATED);
    }


    @PatchMapping(path=PathType.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody Uzytkownik data
    ){
        Uzytkownik current = dataSet.getById(id);

        if(data.getImie() != null){
            current.setImie(data.getImie());
        }
        if(data.getNazwisko() != null){
            current.setNazwisko(data.getNazwisko());
        }
        if(data.getTytNauk() != null){
            current.setTytNauk(data.getTytNauk());
        }
        if(data.getHaslo() != null){
            current.setHaslo(data.getHaslo());
        }
        if(data.getEmail() != null){
            current.setEmail(data.getEmail());
        }
        if(data.getTelefon() != null){
            current.setTelefon(data.getTelefon());
        }
        if(data.getDataUrodz() != null){
            current.setDataUrodz(data.getDataUrodz());
        }
        if(data.getStatus() != null){
            current.setStatus(data.getStatus());
        }
        if(data.getZdjecie() != null){
            Zdjecie nowe = new Zdjecie(
                    current.getZdjecie().getIdZdjecia(),
                    data.getZdjecie().getPlik(),
                    data.getZdjecie().getExt());
            current.setZdjecie(nowe);
        }
        if(data.getRola() != null){
            current.setRola(data.getRola());
        }

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(UzytkownicyController.class).get(id))
                        .withRel("zakutalizowany").withTitle("uzytkownik")),
                HttpStatus.OK);
    }
}
