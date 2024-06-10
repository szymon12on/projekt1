package pl.samba.lms.przedmioty.okresy;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@RequiredArgsConstructor
public class Okres {
    private final Integer idOkresu;
    private String kod;
    private LocalDateTime dataPoczatku;
    private LocalDateTime dataKonca;

    @JsonCreator
    public Okres(
            Integer idOkresu,
            @JsonProperty("kod") String kod,
            @JsonProperty("dataPoczatku")LocalDateTime dataPoczatku,
            @JsonProperty("dataKonca")LocalDateTime dataKonca
    ) {
        this.idOkresu = idOkresu;
        this.kod = kod;
        this.dataPoczatku = dataPoczatku;
        this.dataKonca = dataKonca;
    }
}
