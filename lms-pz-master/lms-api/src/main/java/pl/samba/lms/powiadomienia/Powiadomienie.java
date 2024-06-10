package pl.samba.lms.powiadomienia;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.samba.lms.utils.constants.Flagi;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Powiadomienie {
    private final Integer idPowiadomienia;
    private final Integer idOdbiorcy;
    private final LocalDateTime dataWstawienia;
    private final String tresc;
    private Flagi flaga;
}
