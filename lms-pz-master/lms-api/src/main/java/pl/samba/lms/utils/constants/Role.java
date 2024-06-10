package pl.samba.lms.utils.constants;

import lombok.Getter;

public enum Role {
    ADMIN(1),
    NAUCZYCIEL(3),
    UCZEN(2);

    @Getter
    private Integer id;

    private Role(Integer id){
        this.id = id;
    }
    /**
     * Metoda statyczna do uzyskiwania obiektu Role na podstawie przekazanego identyfikatora.
     *
     * @param id Identyfikator roli.
     * @return Obiekt Role odpowiadający przekazanemu identyfikatorowi, lub null, jeśli identyfikator nie został odnaleziony.
     */
    public static Role getRolaById(Integer id) {
        for (Role rola : values()) {
            if (rola.getId().equals(id)) {
                return rola;
            }
        }
        return null;
    }
}
