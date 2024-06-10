package pl.samba.lms.uzytkownicy.uzytkownicy.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.uzytkownicy.zdjecie.Zdjecie;
import pl.samba.lms.utils.database.AbstractCrudRepository;
import pl.samba.lms.utils.constants.Role;
import pl.samba.lms.uzytkownicy.uzytkownicy.Uzytkownik;
import pl.samba.lms.uzytkownicy.zdjecie.database.ZdjecieRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bsurma
 */
@Repository
public class UzytkownikRepository extends AbstractCrudRepository<Uzytkownik, Integer> {
    public static final String C_ID_UZYTK = "id_uzytk";
    public static final String C_IMIE = "imie";
    public static final String C_NAZWISKO = "nazwisko";
    public static final String C_TYT_NAUK = "tyt_nauk";
    public static final String C_LOGIN = "login";
    public static final String C_HASLO = "haslo";
    public static final String C_EMAIL = "email";
    public static final String C_TELEFON = "telefon";
    public static final String C_DATA_URODZ = "data_urodz";
    public static final String C_STATUS = "status";
    public static final String C_ROLA = "rola";

    public static final String P_IMIE = "p_imie";
    public static final String P_NAZWISKO = "p_nazwisko";
    public static final String P_TYT_NAUK = "p_tyt_nauk";
    public static final String P_LOGIN = "p_login";
    public static final String P_HASLO = "p_haslo";
    public static final String P_EMAIL = "p_email";
    public static final String P_TELEFON = "p_telefon";
    public static final String P_DATA_URODZ = "p_data_urodz";
    public static final String P_STATUS = "p_status";
    public static final String P_ID_ZDJECIA = "p_id_zdjecia";
    public static final String P_ID_ROLI = "p_id_roli";

    private final ZdjecieRepository dsZdjecia;
    @Autowired
    public UzytkownikRepository(ZdjecieRepository dsZdjecia) {
        super("uzytkownicy","pk_id_uzytk");
        this.dsZdjecia = dsZdjecia;
    }


    @Override
    public Integer save(Uzytkownik data) {
        Integer idZdjecia = 1;
        if(data.getZdjecie() != null){
            idZdjecia = dsZdjecia.save(data.getZdjecie());
        }



        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_IMIE, data.getImie());
        inParams.put(P_NAZWISKO, data.getNazwisko());
        inParams.put(P_TYT_NAUK, data.getTytNauk());
        inParams.put(P_HASLO,
                new BCryptPasswordEncoder().encode(data.getHaslo())
        );
        inParams.put(P_EMAIL, data.getEmail());
        inParams.put(P_TELEFON, data.getTelefon());
        inParams.put(P_DATA_URODZ, data.getDataUrodz());
        inParams.put(P_ID_ZDJECIA, idZdjecia);
        inParams.put(P_ID_ROLI, data.getRola().getId());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(Uzytkownik data) {
        Integer idZdjecia = 1;

        if(data.getZdjecie().getIdZdjecia() == null || data.getZdjecie().getIdZdjecia().equals(1)){
            idZdjecia = dsZdjecia.save(data.getZdjecie());
        }
        else if(!data.getZdjecie().getIdZdjecia().equals(1)){
            idZdjecia = dsZdjecia.update(data.getZdjecie());
        }


        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdUzytk());
        inParams.put(P_IMIE, data.getImie());
        inParams.put(P_NAZWISKO, data.getNazwisko());
        inParams.put(P_TYT_NAUK, data.getTytNauk());
        inParams.put(P_HASLO, data.getHaslo());
        inParams.put(P_EMAIL, data.getEmail());
        inParams.put(P_TELEFON, data.getTelefon());
        inParams.put(P_DATA_URODZ, data.getDataUrodz());
        inParams.put(P_STATUS, Integer.parseInt(data.getStatus().getKod()));
        inParams.put(P_ID_ZDJECIA, idZdjecia);
        inParams.put(P_ID_ROLI, data.getRola().getId());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Uzytkownik> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Uzytkownik> uzytkownicy = new ArrayList<>();
        for (Map<String, Object> row : resultSet) {
            Zdjecie zdjecie = new Zdjecie(
                    (Integer) row.get(ZdjecieRepository.C_ID_ZDJECIA),
                    (byte[]) row.get(ZdjecieRepository.C_PLIK),
                    (String) row.get(ZdjecieRepository.C_EXT));

            Uzytkownik uzytkownik = new Uzytkownik(
                    (Integer) row.get(C_ID_UZYTK),
                    (String) row.get(C_IMIE),
                    (String) row.get(C_NAZWISKO),
                    (String) row.get(C_TYT_NAUK),
                    (String) row.get(C_LOGIN),
                    (String) row.get(C_HASLO),
                    (String) row.get(C_EMAIL),
                    (Integer) row.get(C_TELEFON),
                    (java.sql.Date) row.get(C_DATA_URODZ),
                    Status.getStatusByKod(row.get(C_STATUS).toString()),
                    zdjecie,
                    Role.valueOf((String) row.get(C_ROLA))
            );

            uzytkownicy.add(uzytkownik);
        }
        return uzytkownicy;
    }

}
