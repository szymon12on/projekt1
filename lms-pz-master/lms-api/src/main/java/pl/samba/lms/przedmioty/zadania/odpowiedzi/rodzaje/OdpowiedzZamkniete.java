package pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.utils.constants.RodzajeZadan;


import java.util.List;

@AllArgsConstructor
@Getter
public class OdpowiedzZamkniete implements OdpowiedzInterface {
    private List<Double> odpowiedzi;
    @Setter
    private Double punkty;

    @Override
    public String toString(){
        String odpowiedziString = "[";
        int i = 1;
        for (Double odpowiedz:
                odpowiedzi) {
            odpowiedziString += odpowiedz.intValue();
            if(i < odpowiedzi.size()){
                odpowiedziString += ",";
            }
            i++;
        }
        odpowiedziString += "]";

        return  "{" +
                "\"typ\":\""+ RodzajeZadan.ZAMKNIETE+"\"," +
                "\"odpowiedz\":"+ odpowiedziString + "," +
                "\"punkty\":" + punkty +
                "}";
    }

}
