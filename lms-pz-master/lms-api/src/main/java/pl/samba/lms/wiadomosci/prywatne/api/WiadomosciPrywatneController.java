package pl.samba.lms.wiadomosci.prywatne.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.wiadomosci.prywatne.WiadomosciPrywatne;
import pl.samba.lms.wiadomosci.prywatne.database.WiadomosciPrywatneRepository;
import pl.samba.lms.utils.api.ControllerInterface;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "api/wiadomosci/prywatne", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class WiadomosciPrywatneController implements ControllerInterface<WiadomosciPrywatne, WiadomosciPrywatneModel> {

    private final WiadomosciPrywatneRepository dataSet;
    @Override
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<WiadomosciPrywatneModel>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        Iterable<WiadomosciPrywatne> wiadomosci;

        if(size != null || page != null ){
            String params =  (size == null? "0" : size) + ";" +
                    (page == null? "0" : page);
            wiadomosci = dataSet.getAll(params);
        } else {
            wiadomosci = dataSet.getAll();
        }

        if(wiadomosci.iterator().hasNext()){
            List<WiadomosciPrywatneModel> listaModeli = new ArrayList<>();

            for(WiadomosciPrywatne wiadomosc: wiadomosci){
                listaModeli.add(new WiadomosciPrywatneModelAssembler().toModel(wiadomosc));
            }

            CollectionModel<WiadomosciPrywatneModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(WiadomosciPrywatneController.class).get(size, page))
                    .withRel("wiadomosci_prywatne").withTitle("lista_wiadomosci_prywatnych"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<WiadomosciPrywatneModel> get(@PathVariable("id") Integer id) {
        WiadomosciPrywatne wiadomosc = dataSet.getById(id);
        if (wiadomosc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        WiadomosciPrywatneModel model = new WiadomosciPrywatneModelAssembler().toModel(wiadomosc);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/between-users")
    public ResponseEntity<CollectionModel<WiadomosciPrywatneModel>> getMessagesBetweenUsers(
            @RequestParam Integer idUser1,
            @RequestParam Integer idUser2
    ) {
        Iterable<WiadomosciPrywatne> messages = dataSet.getMessagesBetweenUsers(idUser1, idUser2);

        if (!messages.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<WiadomosciPrywatneModel> listaModeli = new ArrayList<>();
        for (WiadomosciPrywatne message : messages) {
            listaModeli.add(new WiadomosciPrywatneModelAssembler().toModel(message));
        }

        CollectionModel<WiadomosciPrywatneModel> kolekcjaModeli = CollectionModel.of(listaModeli);

        return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody WiadomosciPrywatne wiadomosc) {
        Integer id = dataSet.save(wiadomosc);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(WiadomosciPrywatneController.class).get(id))
                        .withRel("nowa")
                        .withTitle("wiadomosc_prywatna")),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody WiadomosciPrywatne updateData
    ) {
        WiadomosciPrywatne current = dataSet.getById(id);

        if (current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Aktualizacja pól tylko jeśli zostały przekazane w żądaniu
        if (updateData.getTresc() != null) {
            current.setTresc(updateData.getTresc());
        }
        if (updateData.getDataWyslania() != null) {
            current.setDataWyslania(updateData.getDataWyslania());
        }
        if (updateData.getIdFlagi() != null) {
            current.setIdFlagi(updateData.getIdFlagi());
        }

        dataSet.update(current);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(WiadomosciPrywatneController.class).get(id))
                        .withRel("zaktualizowana")
                        .withTitle("wiadomosc_prywatna")),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}/mark-read")
    public ResponseEntity<Object> markAsRead(@PathVariable("id") Integer id) {
        WiadomosciPrywatne wiadomosc = dataSet.getById(id);
        if (wiadomosc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        wiadomosc.setIdFlagi(2); // Ustawienie flagi na "przeczytane"
        dataSet.update(wiadomosc);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(WiadomosciPrywatneController.class).get(id))
                        .withRel("przeczytana")
                        .withTitle("wiadomosc_prywatna")),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}/mark-unread")
    public ResponseEntity<Object> markAsUnread(@PathVariable("id") Integer id) {
        WiadomosciPrywatne wiadomosc = dataSet.getById(id);
        if (wiadomosc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        wiadomosc.setIdFlagi(1); // Ustawienie flagi na "nieprzeczytane"
        dataSet.update(wiadomosc);

        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(WiadomosciPrywatneController.class).get(id))
                        .withRel("nieprzeczytana")
                        .withTitle("wiadomosc_prywatna")),
                HttpStatus.OK);
    }

}