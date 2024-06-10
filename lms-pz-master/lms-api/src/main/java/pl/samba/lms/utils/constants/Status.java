package pl.samba.lms.utils.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeracja Status reprezentująca różne stany dla przedmiotow i uzytkownikow.
 * Każdy status posiada odpowiadający mu kod identyfikacyjny.
 */
@Getter
public enum Status {
    DO_ZATWIERDZENIA("2AP"),  // Oczekuje na zatwierdzenie.
    ZATWIERDZONY("APR"),      // Został zatwierdzony.
    ODRZUCONY("REJ"),         // Został odrzucony.
    TRWAJACY("ACT"),          // Jest aktualnie w trakcie.
    ZAKONCZONY("END"),        // Zakończony.
    AKTYWNY("1"),             // Aktywny-tylko uzytkownik.
    NIEAKTYWNY("0");         // Nieaktywny-tylko uzytkownik.

    private String kod;
    private Status(String kod){
        this.kod = kod;
    }

    /**
     * Metoda statyczna do uzyskiwania obiektu Status na podstawie przekazanego kodu identyfikacyjnego.
     *
     * @param kod Kod identyfikacyjny dla poszukiwanego statusu.
     * @return Obiekt Status odpowiadający przekazanemu kodowi, lub null, jeśli kod nie został odnaleziony.
     */
    public static Status getStatusByKod(String kod) {
        for (Status status : values()) {
            if (status.getKod().equals(kod)) {
                return status;
            }
        }
        return null;
    }
}
