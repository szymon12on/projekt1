package pl.samba.lms.przedmioty.wykaz_ocen.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.samba.lms.przedmioty.wykaz_ocen.WykazOcen;
import pl.samba.lms.przedmioty.wykaz_ocen.database.WykazOcenRepository;
import pl.samba.lms.przedmioty.wykaz_ocen.database.Zaliczenie;
import pl.samba.lms.utils.PathType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = PathType.WYKAZ_OCEN, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class WykazOcenController {

    WykazOcenRepository dataSet;

    @GetMapping(PathType.ID)
    ResponseEntity<CollectionModel<ZaliczenieModel>> get(@PathVariable("id") Integer id){

        Iterable<Zaliczenie> zaliczenia = dataSet.get(id);
        if(zaliczenia.iterator().hasNext()){
            List<ZaliczenieModel> listaModeli = new ArrayList<>();

            for(Zaliczenie zaliczenie: zaliczenia){
                listaModeli.add(new ZaliczenieModel(zaliczenie));
            }

            CollectionModel<ZaliczenieModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(WykazOcenController.class).get(id))
                    .withRel("oceny").withTitle("lista_ocen"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
}
