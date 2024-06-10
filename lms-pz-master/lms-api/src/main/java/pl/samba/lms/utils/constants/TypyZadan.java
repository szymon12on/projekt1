package pl.samba.lms.utils.constants;

import lombok.Getter;

/**
 * Enumeracja TypyZadan określa typ całości zadania - rekordu w bazie danych w tabeli 'lms.zadania'
 */
public enum TypyZadan {
    ZADANIE(1),
    TEST(2),
    EGZAMIN(3);

    @Getter
    Integer id;

    private TypyZadan(Integer id){
        this.id = id;
    }

    public static TypyZadan getById(Integer id){
        TypyZadan typ = null;
        switch (id) {
            case 1 -> {
                typ = ZADANIE;
            }
            case 2 -> {
                typ = TEST;
            }
            case 3 -> {
                typ = EGZAMIN;
            }
        }
        return typ;
    }
}
