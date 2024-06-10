package pl.samba.lms.przedmioty.rejestracja.api;


import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.przedmioty.przedmioty.database.PrzedmiotRepository;
import pl.samba.lms.przedmioty.rejestracja.UczenPrzedmiot;
import pl.samba.lms.przedmioty.rejestracja.database.UczenPrzedmiotRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.uzytkownicy.uzytkownicy.database.UzytkownikRepository;

import java.util.Base64;
import java.util.LinkedList;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= PathType.REJESTRACJA_PRZEDMIOT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UczenPrzedmiotController {
    private final UczenPrzedmiotRepository dataSet;
    private final PrzedmiotRepository przedmiotDataSet;
    private final UzytkownikRepository uzytkownikDataSet;


    /**
     * Endpoint do rejestracji ucznia na przedmiot
     * @param nick nick (login) ucznia w base64, ktory rejestruje sie na przedmiot
     * @param kod kod przedmiotu w base64, na ktory uczen sie rejestruje
     * @return Odpowiedź HTTP zawierająca kolekcję modeli powiazania.
     */

    @PostMapping(PathType.REJESTRUJ)
    public ResponseEntity<Object> rejestrujNaPrzedmiot(
            @RequestParam(required = true) String nick,
            @RequestParam(required = true) String kod
    ){
        String decodedNick = new String(Base64.getDecoder().decode(nick));
        String decodedKod = new String(Base64.getDecoder().decode(kod));
        Integer idUcznia = uzytkownikDataSet.getByUnique(decodedNick).getIdUzytk();
        Integer idPrzedmiotu = przedmiotDataSet.getByUnique(decodedKod).getIdPrzedmiotu();
        UczenPrzedmiot data = new UczenPrzedmiot(
                idPrzedmiotu,
                idUcznia,
                null,
                null);

        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(UczenPrzedmiotController.class).get(id))
                        .withRel("rejestracja_przedmiot")),
                HttpStatus.CREATED);
    }

    /**
     * Endpoint do pobierania listy lub konkrentego powiazania. Mozna pobrac powiazania po:
     * <ul>
     *     <li><b>nicku (loginie) ucznia</b>, wtedy jest to lista wszystkich przedmiotow ucznia</li>
     *     <li><b>kodzie przedmiotu</b>, wtedy jest to lista wszystkich uczniow w danym przedmiocie</li>
     *     <li><b>nicku i kodzie</b>, wtedy pobiera konkretny poziazanie ucznia i przedmiotu</li>
     *     <li><b>bez żadnych paramkodetrów</b>, wtedy pobiera wszytko</li>
     * </ul>
     * @param nick nick (login) ucznia w base64,
     * @param kod kod przedmiotu w base64
     * @param size liczba wyników na stronie.
     * @param page numer strony, liczony od 0.
     * @return Odpowiedź HTTP zawierająca kolekcję modeli powiazania.
     */
    @GetMapping()
    public ResponseEntity<Object> get(
            @RequestParam(required = false) String nick,
            @RequestParam(required = false) String kod,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ){
        Iterable<UczenPrzedmiot> lista;
        String params = null;

        if(nick != null || kod != null || size != null || page != null ){
            Integer uczenId  = -1;
            Integer  przedmiotId = -1;

            if(nick != null){
                String decodedNick = new String(Base64.getDecoder().decode(nick));
                uczenId  = uzytkownikDataSet.getByUnique(decodedNick).getIdUzytk();
            }
            if(kod != null){
                String decodedKod = new String(Base64.getDecoder().decode(kod));
                przedmiotId = przedmiotDataSet.getByUnique(decodedKod).getIdPrzedmiotu();
            }

            params = uczenId + ";" +
                    przedmiotId + ";" +
                    (size == null? "0" : size) + ";" +
                    (page == null? "0" : page);

        }
        lista = dataSet.get(params);

        if(lista.iterator().hasNext()){
            LinkedList<UczenPrzedmiotModel> modelsList = new LinkedList<>();

            for (UczenPrzedmiot data :
                    lista) {
                modelsList.add(new UczenPrzedmiotModelAssembler().toModel(data));
            }

            CollectionModel<UczenPrzedmiotModel> models = CollectionModel.of(modelsList);
            models.add(WebMvcLinkBuilder
                    .linkTo(methodOn(UczenPrzedmiotController.class).get(nick, kod, size, page))
                    .withRel("uczen_przedmiot"));

            return new ResponseEntity<>(models, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to pobierania z bd powiazania o konkretnym numerze id
     * @param id - numer id powiazania
     * @return Odpowiedź HTTP zawierająca model powiazania.
     */
    @GetMapping(PathType.ID)
    public ResponseEntity<Object> get(@PathVariable("id") Integer id){
        Optional<UczenPrzedmiot> optUzytkownik = Optional.ofNullable(dataSet.getById(id));
        if(optUzytkownik.isPresent()){
            UczenPrzedmiotModel model = new UczenPrzedmiotModelAssembler().toModel(optUzytkownik.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    /**
     * Endpoint do wystawiania oceny uczniowi
     * @param ocena ocena
     * @param nick nick (login) ucznia w base64, ktoremu wystawiona jest ocena
     * @param kod kod przedmiotu w base64
     * @return Odpowiedź HTTP zawierająca link do zmodyfikowanego powiazania.
     */

    @PatchMapping(PathType.OCENA)
    public ResponseEntity<Object> wystawOcene(
            @RequestParam(required = true) Integer ocena,
            @RequestParam(required = true) String nick,
            @RequestParam(required = true) String kod
    ){
        String decodedNick = new String(Base64.getDecoder().decode(nick));
        String decodedKod = new String(Base64.getDecoder().decode(kod));
        Integer uczenId = uzytkownikDataSet.getByUnique(decodedNick).getIdUzytk();
        Integer przedmiotId = przedmiotDataSet.getByUnique(decodedKod).getIdPrzedmiotu();
        UczenPrzedmiot data = dataSet.get(uczenId, przedmiotId);
            data.setOcena(ocena);
        Integer id = dataSet.update(data);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder
                        .linkTo(methodOn(UczenPrzedmiotController.class).get(id))
                        .withRel("wystawiona_ocena")),
                HttpStatus.OK);
    }

    /**
     * Endpoint do wyrejestrowania, czyli usuwania oceny i powiazania pomiedzy
     * uczniem i przedmiotem
     * @param nick nick ucznia w base64, ktoremu wystawiona jest ocena
     * @param kod kod przedmiotu w base64
     */

    @DeleteMapping(PathType.WYREJESTRUJ)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam(required = true) String nick,
            @RequestParam(required = true) String kod
    ){
        String decodedNick = new String(Base64.getDecoder().decode(nick));
        String decodedKod = new String(Base64.getDecoder().decode(kod));
        Integer uczenId = uzytkownikDataSet.getByUnique(decodedNick).getIdUzytk();
        Integer przedmiotId = przedmiotDataSet.getByUnique(decodedKod).getIdPrzedmiotu();

        dataSet.delete(uczenId, przedmiotId);
    }

}
