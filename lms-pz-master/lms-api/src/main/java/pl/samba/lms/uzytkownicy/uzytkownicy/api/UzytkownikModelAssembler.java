package pl.samba.lms.uzytkownicy.uzytkownicy.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;

public class UzytkownikModelAssembler extends RepresentationModelAssemblerSupport<Uzytkownik, UzytkownikModel> {
    public UzytkownikModelAssembler(){
        super(UzytkownicyController.class, UzytkownikModel.class);
    }

    @Override
    protected UzytkownikModel instantiateModel(Uzytkownik entity) {
        return new UzytkownikModel(entity);
    }
    @Override
    public UzytkownikModel toModel(Uzytkownik entity) {
        return createModelWithId(entity.getIdUzytk(), entity);
    }
}
