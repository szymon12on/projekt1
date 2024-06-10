package pl.samba.lms.przedmioty.zadania.zadania.rodzaje;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.samba.lms.utils.constants.RodzajeZadan;


@AllArgsConstructor
@Getter
public class ZadaniePrawdaFalsz implements ZadanieInterface {
    private String pytanie;
    private Boolean odpowiedz;
    private Double punkty;

    @Override
    public String toString(){
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.PRAWDA_FALSZ +"\"," +
                "\"pytanie\":\""+ pytanie+"\"," +
                "\"odpowiedz\":\""+ odpowiedz+"\"," +
                "\"punkty\":" + punkty +
                "}";
    }

}
