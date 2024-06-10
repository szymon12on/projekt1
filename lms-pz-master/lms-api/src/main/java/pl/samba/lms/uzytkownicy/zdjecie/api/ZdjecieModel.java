package pl.samba.lms.uzytkownicy.zdjecie.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.samba.lms.uzytkownicy.zdjecie.Zdjecie;

@Getter
public class ZdjecieModel extends RepresentationModel<ZdjecieModel> {
        private final Integer id;
        private final byte[] plik;
        private final String ext;

        public ZdjecieModel(Zdjecie z){
            this.id = z.getIdZdjecia();
            this.plik = z.getPlik();
            this.ext =z.getExt();
        }

}
