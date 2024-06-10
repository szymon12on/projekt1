package pl.samba.lms.utils.constants;

import lombok.Getter;

/**
 * Reprezentuje różne flagi stanu dla  wiadomości i powiadomień.
 * Może być używana do określenia statusu, przejścia przez etapy cyklu życia, czy innych wariantów.
 */
public enum Flagi {
    NIEPRZECZYTANA(1),   // Oznacza, że obiekt nie został jeszcze przeczytany.
    PRZECZYTANA(2),      // Oznacza, że obiekt został przeczytany.
    ZARCHIWIZOWANA(3),   // Oznacza, że obiekt został zarchiwizowany.
    USUNIETA(4),         // Oznacza, że obiekt został usunięty.
    NOWA(5),             // Oznacza, że obiekt jest nowy.
    ROBOCZA(6);           // Oznacza, że obiekt jest wersją roboczą lub tymczasową.

    @Getter
    Integer id;

    private Flagi(Integer id){
        this.id = id;
    }

    public static Flagi getById(Integer id){
        Flagi flaga = null;
        switch (id){
            case 1 -> {
                flaga =  NIEPRZECZYTANA;
            }
            case 2 -> {
                flaga =  PRZECZYTANA;
            }
            case 3 ->{
                flaga =  ZARCHIWIZOWANA;
            }
            case 4 ->{
                flaga =  USUNIETA;
            }
            case 5 ->{
                flaga =  NOWA;
            }
            case 6 -> {
                flaga =  ROBOCZA;
            }
        }
        return flaga;
    }
}

