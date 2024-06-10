package pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.samba.lms.utils.constants.RodzajeZadan;


@Getter
@AllArgsConstructor
public class OdpowiedzOtwarte implements OdpowiedzInterface {
    private String odpowiedz;
    private Integer punkty;

    @Override
    public String toString(){
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.OTWARTE+"\"," +
                "\"pytanie\":\""+ odpowiedz+"\"," +
                "\"punkty\":" + punkty +
                "}";

    }
}
