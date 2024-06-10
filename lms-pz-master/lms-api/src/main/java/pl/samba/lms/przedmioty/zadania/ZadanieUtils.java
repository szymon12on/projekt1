package pl.samba.lms.przedmioty.zadania;

import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje.*;
import pl.samba.lms.przedmioty.zadania.zadania.rodzaje.*;
import pl.samba.lms.utils.constants.RodzajeZadan;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZadanieUtils {
    private static final String TYP = "typ";
    private static final String PYTANIE = "pytanie";
    private static final String ODPOWIEDZ = "odpowiedz";
    private static final String POPRAWNE_ODPOWIEDZI = "poprawneOdp";
    private static final String PLIK = "plik";
    private static final String PUNKTY = "punkty";

    public static List<ZadanieInterface> zadaniaFactory(String json){
        List<ZadanieInterface> zadanieList = new ArrayList<>();

        Gson gson = new Gson();
        ArrayList<Map<String, Object>> jsonMapList
                = gson.fromJson(json, ArrayList.class);
        for (Map<String, Object> jsonObject:
                jsonMapList) {
            RodzajeZadan typ = RodzajeZadan.valueOf((String) jsonObject.get(TYP));
            ZadanieInterface zadanie = null;
            switch (typ){
                case OTWARTE -> {
                    zadanie = new ZadanieOtwarte(
                            (String) jsonObject.get(PYTANIE),
                            ((Double) jsonObject.get(PUNKTY)).intValue()
                    );

                }
                case ZAMKNIETE -> {
                    List<String> odpowiedzi = (List<String>) jsonObject.get(ODPOWIEDZ);
                    List<Double> poprawneOdp = (List<Double>) jsonObject.get(POPRAWNE_ODPOWIEDZI);

                    zadanie = new ZadanieZamkniete(
                            (String) jsonObject.get(PYTANIE),
                            odpowiedzi,
                            poprawneOdp,
                            (Double) jsonObject.get(PUNKTY)
                    );
                }
                case PRAWDA_FALSZ -> {
                    zadanie = new ZadaniePrawdaFalsz(
                            (String) jsonObject.get(PYTANIE),
                            Boolean.getBoolean((String) jsonObject.get(ODPOWIEDZ)),
                            (Double) jsonObject.get(PUNKTY)
                    );
                }
                case PLIK -> {
                    zadanie = new ZadaniePlik(
                            (String) jsonObject.get(PYTANIE),
                            ((String) jsonObject.get(PLIK)).getBytes(StandardCharsets.UTF_8),
                            ((Double) jsonObject.get(PUNKTY)).intValue()
                    );
                }

            }
            zadanieList.add(zadanie);
        }
        return zadanieList;
    }

    public static List<OdpowiedzInterface> odpowiedziFactory(String json){
        List<OdpowiedzInterface> odpowiedzList = new ArrayList<>();

        Gson gson = new Gson();
        ArrayList<Map<String, Object>> jsonMapList
                = gson.fromJson(json, ArrayList.class);
        for (Map<String, Object> jsonObject:
                jsonMapList) {
            RodzajeZadan typ = RodzajeZadan.valueOf((String) jsonObject.get(TYP));
            OdpowiedzInterface odpowiedz = null;
            switch (typ){
                case OTWARTE -> {
                    odpowiedz = new OdpowiedzOtwarte(
                            (String) jsonObject.get(ODPOWIEDZ),
                            ((Double) jsonObject.get(PUNKTY)).intValue()
                    );

                }
                case ZAMKNIETE -> {
                    List<Double> odpowiedzi = (List<Double>) jsonObject.get(ODPOWIEDZ);

                    odpowiedz = new OdpowiedzZamkniete(
                            odpowiedzi,
                            (Double) jsonObject.get(PUNKTY)
                    );
                }
                case PRAWDA_FALSZ -> {
                    odpowiedz = new OdpowiedzPrawdaFalsz(
                            Boolean.getBoolean((String) jsonObject.get(ODPOWIEDZ)),
                            (Double) jsonObject.get(PUNKTY)
                    );
                }
                case PLIK -> {
                    String temp = (String) jsonObject.get(ODPOWIEDZ);
                    odpowiedz = new OdpowiedzPlik(
                            Base64.decodeBase64((String) jsonObject.get(ODPOWIEDZ)),
                            ((Double) jsonObject.get(PUNKTY)).intValue()
                    );
                }

            }
            odpowiedzList.add(odpowiedz);
        }
        return odpowiedzList;
    }

    /**
     * Metoda statyczna, sluzaca do sprawdzania pytan zamknietych oraz typu prawda-falsz
     * <p>Zadania ocenianie są następująco:</p>
     * <ul>
     *     <li>Zadania prawda-fałsz otrzymuje 100% punktów za poprawna odpowiedź, w przeciwnym wypadku 0</li>
     *     <li>Zadania zamknięte otrzymuje x% puntów za x poprawnych odpowiedzi</li>
     * </ul>
     * @param zadania Treść zadań
     * @param odpowiedzi Treść odpowiedzi
     * @return Lista odpowiedzi z sprawdzonymi odpowiedziami zamknietymi
     * @throws Exception rzucany, gdy liczba odpowiedzi jest różna od liczby zadań
     */
    public static List<OdpowiedzInterface> sprawdzZadaniaZamkniete(
            List<ZadanieInterface> zadania,
            List<OdpowiedzInterface> odpowiedzi
    ) throws Exception {
        List<OdpowiedzInterface> ocenione = new ArrayList<>();

        if(zadania.size() != odpowiedzi.size())
            throw new Exception("Liczba zadań jest różna od liczby odpowiedzi!");
        int size = zadania.size();

        for(int i = 0; i < size; i++){
            ZadanieInterface zadanie = zadania.get(i);
            if(zadanie.getClass().equals(ZadanieZamkniete.class)){
                ZadanieZamkniete zz = (ZadanieZamkniete) zadanie;
                OdpowiedzZamkniete odpowiedz = (OdpowiedzZamkniete) odpowiedzi.get(i);

                List<Double> odp = odpowiedz.getOdpowiedzi();
                List<Integer> sprawdzone = new ArrayList<>(odp.size());

                List<Double> poprOdp = zz.getPoprawneOdp();

                for (int j = 0; j < odp.size(); j++) {
                    Integer nrOdp = odp.get(j).intValue();

                    for (int k = 0; k < poprOdp.size(); k++) {
                        Integer nrPopr = poprOdp.get(k).intValue();
                        if(nrOdp.equals(nrPopr)) {
                            sprawdzone.add(1);
                            break;
                        }
                    }
                }

                Integer suma = sprawdzone.stream().reduce(0, Integer::sum);

                Double punkty = (suma.doubleValue() / poprOdp.size()) * zz.getPunkty();

                odpowiedz.setPunkty(punkty);
            }
            else if(zadanie.getClass().equals(ZadaniePrawdaFalsz.class)){
                ZadaniePrawdaFalsz zpf = (ZadaniePrawdaFalsz) zadanie;
                OdpowiedzPrawdaFalsz odpowiedz = (OdpowiedzPrawdaFalsz) odpowiedzi.get(i);

                if(odpowiedz.getOdpowiedz().equals(zpf.getOdpowiedz())){
                    odpowiedz.setPunkty(zpf.getPunkty());
                }
                else{
                    odpowiedz.setPunkty((double) 0);
                }
            }

            ocenione.add(odpowiedzi.get(i));
        }

        return ocenione;
    }
}
