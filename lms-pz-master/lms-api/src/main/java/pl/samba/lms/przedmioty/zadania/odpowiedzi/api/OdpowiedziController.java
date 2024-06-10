package pl.samba.lms.przedmioty.zadania.odpowiedzi.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.przedmioty.zadania.ZadanieUtils;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.Odpowiedz;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.database.OdpowiedzRepository;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje.OdpowiedzInterface;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;
import pl.samba.lms.przedmioty.zadania.zadania.database.ZadanieRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= PathType.ODPOWIEDZ, produces=MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OdpowiedziController implements ControllerInterface<Odpowiedz, OdpowiedzModel> {

    private final OdpowiedzRepository dataSet;
    private final ZadanieRepository zadanieDataSet;

    /**
     * Metoda do pobierania zadań dla konkretnego przedmiotu z uwzględnieniem parametrów
     * takich jak sortowanie, rozmiar strony
     *
     * @param kod Kod przedmiotu w base64
     * @param login Login uzytkownika w base64
     * @param size Liczba wyników na stronie.
     * @param page Numer strony, liczony od 0.
     * @return Odpowiedź HTTP zawierająca kolekcję modelu zasobów.
     */

    @GetMapping(PathType.ALL)
    public ResponseEntity<CollectionModel<OdpowiedzModel>> get(
            @RequestParam(required = false) String kod,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        String decodedKod = null;
        String decodedLogin = null;
        if(kod != null){
            decodedKod = new String(Base64.getDecoder().decode(kod));
        }
        if(login != null){
            decodedLogin = new String(Base64.getDecoder().decode(login));
        }
        String params;
        if(size == null && page == null){
            params = ";;";
            if(decodedKod != null) params += decodedKod +";";
            else params += ";";
            if(decodedLogin != null) params += decodedLogin;
        }
        else{
            params = (size == null? "0" : size) + ";" +
                    (page == null? "0" : page) + ";";
            if(decodedKod != null) params += decodedKod +";";
            else params += ";";
            if(decodedLogin != null) params += decodedLogin;
        }

        Iterable<Odpowiedz> odpowiedzi = dataSet.getAll(params);

        if(odpowiedzi.iterator().hasNext()){
            List<OdpowiedzModel> listaModeli = new ArrayList<>();

            for(Odpowiedz przedmiot: odpowiedzi){
                listaModeli.add(new OdpowiedzModelAssembler().toModel(przedmiot));
            }

            CollectionModel<OdpowiedzModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(OdpowiedziController.class).get(kod,login,size, page))
                    .withRel("odpowiedzi").withTitle("lista_odpowiedzi"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @Deprecated
    @Override
    public ResponseEntity<CollectionModel<OdpowiedzModel>> get(Integer size, Integer page) {
        //BRAK WSPARCIA
        return null;
    }


    @GetMapping(PathType.ID)
    @Override
    public ResponseEntity<OdpowiedzModel> get(@PathVariable("id") Integer id) {
        Optional<Odpowiedz> opt = Optional.ofNullable(dataSet.getById(id));

        if (opt.isPresent()) {
            OdpowiedzModel model = new OdpowiedzModelAssembler().toModel(opt.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping(PathType.ID)
    @Override
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Object> post(Odpowiedz data) throws Exception{
        Zadanie zadanie = zadanieDataSet.getById(data.getIdZadania());

        List<OdpowiedzInterface> sprawdzone = ZadanieUtils.sprawdzZadaniaZamkniete(
                zadanie.getTresc(),
                data.getTresc()
        );

        data.setTresc(sprawdzone);

        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(OdpowiedziController.class).get(id))
                        .withRel("nowa")
                        .withTitle("odpowiedz")),
                HttpStatus.CREATED);
    }

    @PatchMapping(path = PathType.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody Odpowiedz data
    ) throws Exception {
        Odpowiedz current = dataSet.getById(id);

        if (data.getTresc() != null) {
            Zadanie zadanie = zadanieDataSet.getById(current.getIdZadania());

            List<OdpowiedzInterface> sprawdzone = ZadanieUtils.sprawdzZadaniaZamkniete(
                    zadanie.getTresc(),
                    data.getTresc()
            );

            current.setTresc(sprawdzone);
        }
        if (data.getKomentarz() != null) {
            current.setKomentarz(data.getKomentarz());
        }
        if (data.getOcena() != null) {
            current.setOcena(data.getOcena());
        }
        if (data.getDataOcenienia() != null) {
            current.setDataOcenienia(data.getDataOcenienia());
        }

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(OdpowiedziController.class).get(id))
                        .withRel("zaktualizowane")
                        .withTitle("odpowiedz")),
                HttpStatus.OK);
    }

    /**
     * Endpoint do oceniania zadania przez nauczyciela
     * @param id numer id odpowiedzi
     * @param data RequestBody zawierajace tresc, ocene i komentarz. Tresc powinna uwzgledniac punktacje za zadnia otwarte
     * @return Odpowiedz HTTP z linkiem do ocenionej odpowiedzi
     */

    @PatchMapping(path = PathType.OCEN, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> wystawOcene(
            @PathVariable("id") Integer id,
            @RequestBody OcenaRequest data
    ) {
        Odpowiedz current = dataSet.getById(id);

        if(data.getTresc() != null){
            current.setTresc(ZadanieUtils.odpowiedziFactory(data.tresc));
        }
        if(data.getKomentarz() != null)
            current.setKomentarz(data.komentarz);
        if(data.getOcena() != null){
            current.setOcena(data.ocena);
        }
        current.setDataOcenienia(LocalDateTime.now());

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(OdpowiedziController.class).get(id))
                        .withRel("oceniona")
                        .withTitle("odpowiedz")),
                HttpStatus.OK);
    }

    @AllArgsConstructor
    @Getter
    private static class OcenaRequest {
        private String komentarz;
        private Integer ocena;
        private String tresc;
    }
}