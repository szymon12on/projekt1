package pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.utils.constants.RodzajeZadan;


@AllArgsConstructor
@Getter
public class OdpowiedzPrawdaFalsz implements OdpowiedzInterface {
    private Boolean odpowiedz;
    @Setter
    private Double punkty;

    @Override
    public String toString(){
        return  "{" +
                "\"typ\":\""+ RodzajeZadan.PRAWDA_FALSZ +"\"," +
                "\"odpowiedz\":\""+ odpowiedz +"\"," +
                "\"punkty\":" + punkty +
                "}";
    }

}
