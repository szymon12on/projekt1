package pl.samba.lms.powiadomienia.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.powiadomienia.Powiadomienie;
import pl.samba.lms.powiadomienia.database.PowiadomienieRepository;
import pl.samba.lms.przedmioty.okresy.Okres;
import pl.samba.lms.przedmioty.okresy.database.OkresRepository;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;
import pl.samba.lms.przedmioty.zadania.zadania.api.ZadaniaController;
import pl.samba.lms.przedmioty.zadania.zadania.api.ZadanieModel;
import pl.samba.lms.przedmioty.zadania.zadania.api.ZadanieModelAssembler;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;
import pl.samba.lms.utils.constants.Flagi;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.POWIADOMIENIE, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PowiadomieniaController implements ControllerInterface<Powiadomienie, PowiadomienieModel> {

    private final PowiadomienieRepository dataSet;

    /**
     * Metoda do pobierania zadań dla konkretnego przedmiotu z uwzględnieniem parametrów
     * takich jak sortowanie, rozmiar strony
     *
     * @param login Login użytkownika w base64
     * @param size Liczba wyników na stronie.
     * @param page Numer strony, liczony od 0.
     * @return Odpowiedź HTTP zawierająca kolekcję modelu zasobów.
     */
    @GetMapping(PathType.ALL)
    public ResponseEntity<CollectionModel<PowiadomienieModel>> get(
            @RequestParam(required = true) String login,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        String decodedLogin = new String(Base64.getDecoder().decode(login));

        String params;
        if(size == null && page == null){
            params = ";;" + decodedLogin;
        }
        else{
            params = (size == null? "0" : size) + ";" +
                    (page == null? "0" : page) + ";" +
                    decodedLogin;
        }
        Iterable<Powiadomienie> zadania = dataSet.getAll(params);

        if(zadania.iterator().hasNext()){
            List<PowiadomienieModel> listaModeli = new ArrayList<>();

            for(Powiadomienie przedmiot: zadania){
                listaModeli.add(new PowiadomienieModelAssembler().toModel(przedmiot));
            }

            CollectionModel<PowiadomienieModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(ZadaniaController.class).get(login,size, page))
                    .withRel("powiadomienia").withTitle("lista_powiadomien"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Deprecated
    @Override
    public ResponseEntity<CollectionModel<PowiadomienieModel>> get(Integer size, Integer page) {
        // BRAK WSPARCIA
        return null;
    }

    @Override
    @GetMapping(PathType.ID)
    public ResponseEntity<PowiadomienieModel> get(@PathVariable("id") Integer id) {
        Optional<Powiadomienie> optPowiadomienie = Optional.ofNullable(dataSet.getById(id));
        if(optPowiadomienie.isPresent()){
            PowiadomienieModel model = new PowiadomienieModelAssembler().toModel(optPowiadomienie.get());
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
    public ResponseEntity<Object> post(@RequestBody Powiadomienie data) {
        return null;
    }

    @Override
    public ResponseEntity<Object> patch(Integer id, Powiadomienie data) throws Exception {
        return null;
    }

    @PatchMapping(path=PathType.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestParam String flaga) throws Exception {
        String upperFlaga = flaga.toUpperCase(Locale.ROOT);

        Flagi nowaFlaga = Flagi.valueOf(upperFlaga);

        Powiadomienie current = dataSet.getById(id);

        current.setFlaga(nowaFlaga);

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(PowiadomieniaController.class).get(id))
                        .withRel("zaktualizowane").withTitle("powiadomienie")),
                HttpStatus.OK);
    }


}