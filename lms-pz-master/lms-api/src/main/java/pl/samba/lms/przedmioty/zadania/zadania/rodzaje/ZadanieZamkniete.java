package pl.samba.lms.przedmioty.zadania.zadania.rodzaje;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.samba.lms.utils.constants.RodzajeZadan;


import java.util.List;

@AllArgsConstructor
@Getter
public class ZadanieZamkniete implements ZadanieInterface {
    private String pytanie;
    private List<String> odpowiedzi;
    private List<Double> poprawneOdp;
    private Double punkty;

    @Override
    public String toString(){
        String odpowiedziString = "[";
        int i = 1;
        for (String odpowiedz:
             odpowiedzi) {
            odpowiedziString += "\""+ odpowiedz + "\"";
            if(i < odpowiedzi.size()){
                odpowiedziString += ",";
            }
            i++;
        }
        odpowiedziString += "]";

        String poprawneOdpString = "[";
        i = 1;
        for (Double poprawnaOdp:
                poprawneOdp) {
            poprawneOdpString += poprawnaOdp.intValue();
            if(i < poprawneOdp.size()){
                poprawneOdpString += ",";
            }
            i++;
        }
        poprawneOdpString += "]";

        return  "{" +
                "\"typ\":\""+ RodzajeZadan.ZAMKNIETE+"\"," +
                "\"pytanie\":\""+ pytanie+"\"," +
                "\"odpowiedz\":"+ odpowiedziString + "," +
                "\"poprawneOdp\":"+ poprawneOdpString + "," +
                "\"punkty\":" + punkty +
                "}";
    }

}
