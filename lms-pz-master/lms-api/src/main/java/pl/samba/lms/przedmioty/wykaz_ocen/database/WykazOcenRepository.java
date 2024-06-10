package pl.samba.lms.przedmioty.wykaz_ocen.database;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.wykaz_ocen.WykazOcen;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WykazOcenRepository {


    @Getter
    @Autowired
    private JdbcTemplate jdbc;

    private Integer idUcznia;

    public Iterable<Zaliczenie> get(Integer idUcznia){
        this.idUcznia = idUcznia;
        // GET OKRESY
        String select = "SELECT id_okresu, kod  " +
                "FROM lms.okresy o " +
                "WHERE o.id_okresu IN ( " +
                    "SELECT p.id_okresu " +
                    "FROM lms.przedmioty p  " +
                    "WHERE EXISTS ( " +
                    "SELECT *  " +
                    "FROM lms.uczen_przedmiot up " +
                    "WHERE up.id_ucznia = {0} " +
                        "AND up.id_przedm = p.id_przedm " +
                    ") " +
                ")" +
                "ORDER BY o.data_pocz DESC";

        select = MessageFormat.format(select, idUcznia);


        List<Zaliczenie> zaliczenia = jdbc.query(select, this::zaliczenieRowMapper);

        return  zaliczenia;
    }

    Zaliczenie zaliczenieRowMapper(ResultSet rs, int rowNum) throws SQLException{
        // GET OCENY KONCOWE
        String select = "SELECT pu.id_przedm, pu.ocena, p.nazwa, pu.id_ucznia, o.kod " +
                "FROM lms.uczen_przedmiot pu " +
                "JOIN lms.przedmioty p ON pu.id_przedm = p.id_przedm " +
                "JOIN lms.okresy o ON o.id_okresu = p.id_okresu " +
                "WHERE pu.id_ucznia = {0} AND o.id_okresu = {1} " +
                "ORDER BY o.data_pocz DESC;";
        Integer idOkresu = rs.getInt("id_okresu");

        select = MessageFormat.format(select, idUcznia, idOkresu);

        List<WykazOcen> wykazOcen  = jdbc.query(select, this::wykazOcenRowMapper);

        return new Zaliczenie(rs.getString("kod"), wykazOcen);
    }

    WykazOcen wykazOcenRowMapper(ResultSet rs, int rowNum) throws SQLException {
        //GET OCENY CZASTKOWE
        String select = "SELECT oz.ocena " +
                "FROM lms.odpowiedzi_zadania oz " +
                "WHERE oz.id_ucznia = {0} " +
                "AND EXISTS (" +
                    "SELECT * " +
                    "FROM lms.zadania z " +
                    "WHERE z.id_przedm = {1} " +
                    "AND z.id_zadania = oz.id_zadania" +
                ");";
        select = MessageFormat.format(select,
                idUcznia,
                rs.getInt("id_przedm"));

        List<Integer> ocenyCzastkowe = jdbc.query(select,(rs1, rowNum1) -> {
            Integer ocena = rs1.getInt("ocena");
            return ocena == 0 ? null : ocena;
        });

        return new WykazOcen(
                rs.getInt("id_przedm"),
                rs.getString("nazwa"),
                rs.getInt("ocena"),
                ocenyCzastkowe);
    }
}

