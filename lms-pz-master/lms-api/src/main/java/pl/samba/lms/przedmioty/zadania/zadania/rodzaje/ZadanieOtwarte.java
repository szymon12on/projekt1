package pl.samba.lms.przedmioty.zadania.zadania.rodzaje;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.samba.lms.utils.constants.RodzajeZadan;


@Getter
@AllArgsConstructor
public class ZadanieOtwarte implements ZadanieInterface {
    private String pytanie;
    private Integer punkty;

    @Override
    public String toString(){
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.OTWARTE+"\"," +
                "\"pytanie\":\""+ pytanie+"\"," +
                "\"punkty\":" + punkty +
                "}";

    }
}
