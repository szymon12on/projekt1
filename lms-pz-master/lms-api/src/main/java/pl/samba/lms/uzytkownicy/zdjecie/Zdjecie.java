package pl.samba.lms.uzytkownicy.zdjecie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.samba.lms.utils.Util;

@Getter
@AllArgsConstructor
public class Zdjecie {
    private final Integer idZdjecia;
    private final byte[] plik;
    private final String ext;

    @JsonCreator
    public Zdjecie(@JsonProperty("plik") byte[] plik){
        this.idZdjecia = null;
        this.plik =  plik;
        this.ext = Util.getFileExtension(plik);
    }
}
