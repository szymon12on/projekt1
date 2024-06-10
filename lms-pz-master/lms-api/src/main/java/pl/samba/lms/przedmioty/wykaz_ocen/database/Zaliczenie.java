package pl.samba.lms.przedmioty.wykaz_ocen.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.samba.lms.przedmioty.wykaz_ocen.WykazOcen;

@Getter
@AllArgsConstructor
public class Zaliczenie {
    private final String okres;
    private final Iterable<WykazOcen> przedmioty;
}
