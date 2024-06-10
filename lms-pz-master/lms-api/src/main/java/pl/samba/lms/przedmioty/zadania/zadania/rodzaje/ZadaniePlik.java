package pl.samba.lms.przedmioty.zadania.zadania.rodzaje;

import lombok.AllArgsConstructor;
import pl.samba.lms.utils.constants.RodzajeZadan;


import java.util.Base64;

@AllArgsConstructor
public class ZadaniePlik implements ZadanieInterface {
    private String pytanie;
    private byte[] plik;
    private Integer punkty;

    @Override
    public String toString() {
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.PLIK +"\"," +
                "\"pytanie\":\""+ pytanie+"\"," +
                "\"plik\":\""+ Base64.getEncoder().encodeToString(plik) +"\"," +
                "\"punkty\":" + punkty +
                "}";
    }
}
