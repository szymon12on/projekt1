package pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje;

import lombok.AllArgsConstructor;
import pl.samba.lms.utils.constants.RodzajeZadan;


import java.util.Base64;

@AllArgsConstructor
public class OdpowiedzPlik implements OdpowiedzInterface {
    private byte[] plik;
    private Integer punkty;

    @Override
    public String toString() {
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.PLIK +"\"," +
                "\"odpowiedz\":\""+ Base64.getEncoder().encodeToString(plik) +"\"," +
                "\"punkty\":" + punkty +
                "}";
    }
}
