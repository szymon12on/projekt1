package pl.samba.lms.przedmioty.materialy.api;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.samba.lms.przedmioty.materialy.Material;
import pl.samba.lms.przedmioty.materialy.database.MaterialRepository;
import pl.samba.lms.przedmioty.zadania.zadania.database.ZadanieRepository;
import pl.samba.lms.utils.PathType;
import pl.samba.lms.utils.api.ControllerInterface;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= PathType.MATERIAL, produces= MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MaterialyController implements ControllerInterface<Material, MaterialModel> {

    private final MaterialRepository dataSet;
    private final ZadanieRepository zadaniaDataSet;

    /**
     * Metoda do pobierania materiałów dla konkretnego przedmiotu z uwzględnieniem parametrów
     * takich jak sortowanie, rozmiar strony i kod przedmiotu
     *
     * @param kod Kod przedmiotu w base64
     * @param size Liczba wyników na stronie.
     * @param page Numer strony, liczony od 0.
     * @return Odpowiedź HTTP zawierająca kolekcję modelu zasobów.
     */
    @GetMapping(PathType.ALL)
    public ResponseEntity<CollectionModel<MaterialModel>> get(
            @RequestParam(required = true) String kod,
            @RequestParam(required = false) Integer lp,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        String decodedKod = new String(Base64.getDecoder().decode(kod));

        String params;
        if(size == null && page == null && lp == null){
            params = ";;" + decodedKod + ";";
        }
        else{
            params = (size == null? "0" : size) + ";" +
                    (page == null? "0" : page) + ";" +
                    decodedKod + ";" + lp;
        }
        Iterable<Material> zadania = dataSet.getAll(params);

        if(zadania.iterator().hasNext()){
            List<MaterialModel> listaModeli = new ArrayList<>();

            for(Material przedmiot: zadania){
                listaModeli.add(new MaterialModelAssembler().toModel(przedmiot));
            }

            CollectionModel<MaterialModel> kolekcjaModeli = CollectionModel.of(listaModeli);

            kolekcjaModeli.add(WebMvcLinkBuilder.linkTo(methodOn(MaterialyController.class).get(kod,lp,size, page))
                    .withRel("materialy").withTitle("lista_materialow"));
            return new ResponseEntity<>(kolekcjaModeli, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Deprecated
    @Override
    public ResponseEntity<CollectionModel<MaterialModel>> get(Integer size, Integer page) {
        // BRAK WSPARCIA
        return null;
    }

    @GetMapping(PathType.ID)
    @Override
    public ResponseEntity<MaterialModel> get(@PathVariable("id") Integer id) {
        Optional<Material> optMaterial = Optional.ofNullable(dataSet.getById(id));

        if(optMaterial.isPresent()){
            MaterialModel model = new MaterialModelAssembler().toModel(optMaterial.get());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(PathType.ID)
    @Override
    public void delete(@PathVariable("id") Integer id) {
        dataSet.delete(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Object> post(@RequestBody Material data) {
        Integer id = dataSet.save(data);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(MaterialyController.class).get(id))
                        .withRel("nowy")
                        .withTitle("material")),
                HttpStatus.CREATED);
    }

    @Override

    @PatchMapping(path=PathType.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody Material data
    ) {
        Material current = dataSet.getById(id);

        if(data.getTemat() != null){
            current.setTemat(data.getTemat());
        }
        if(data.getPlik() != null){
            current.setPlik(data.getPlik());
        }
        if(data.getNazwaPliku() != null){
            current.setNazwaPliku(data.getNazwaPliku());
        }
        if(data.getExt() != null){
            current.setExt(data.getExt());
        }
        if(data.getOpis()!= null){
            current.setOpis(data.getOpis());
        }
        if(data.getWidocznosc() != null){
            current.setWidocznosc(data.getWidocznosc());
        }
        if(data.getIdZadania() != null){
            if(!data.getIdZadania().equals(current.getIdZadania())){
                zadaniaDataSet.delete(current.getIdZadania());
                current.setIdZadania(data.getIdZadania());
            }
        }

        id = dataSet.update(current);
        return new ResponseEntity<>(
                EntityModel.of(WebMvcLinkBuilder.
                        linkTo(methodOn(MaterialyController.class).get(id))
                        .withRel("zaktualizowany")
                        .withTitle("material")),
                HttpStatus.OK);
    }
}
