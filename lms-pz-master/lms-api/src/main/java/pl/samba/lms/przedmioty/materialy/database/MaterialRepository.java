package pl.samba.lms.przedmioty.materialy.database;

import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.materialy.Material;
import pl.samba.lms.przedmioty.przedmioty.database.PrzedmiotRepository;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MaterialRepository extends AbstractCrudRepository<Material, Integer> {

    public static final String C_ID_MATER = "id_mater";
    public static final String C_ID_PRZEDM = "id_przedm";
    public static final String C_DATA_WSTAW = "data_wstaw";
    public static final String C_LP = "lp";
    public static final String C_TEMAT = "temat";
    public static final String C_PLIK = "plik";
    public static final String C_NAZWA_PLIKU = "nazwa_pliku";
    public static final String C_EXT = "ext";
    public static final String C_OPIS = "opis";
    public static final String C_WIDOCZNOSC = "widocznosc";
    public static final String C_ID_ZADANIA = "id_zadania";

    public static final String P_ID_PRZEDM = "p_id_przedm";
    public static final String P_LP = "p_lp";
    public static final String P_TEMAT = "p_temat";
    public static final String P_PLIK = "p_plik";
    public static final String P_NAZWA_PLIKU = "p_nazwa_pliku";
    public static final String P_EXT = "p_ext";
    public static final String P_OPIS = "p_opis";
    public static final String P_WIDOCZNOSC = "p_widocznosc";
    public static final String P_ID_ZADANIA = "p_id_zadania";

    public MaterialRepository() {
        super("materialy","pk_id_mater");
    }

    @Override
    public Iterable<Material> getAll(String requestParams) {
        /*
         * requestParamsTable
         * [0] size
         * [1] page
         * [2] kod
         * [3] lp
         * */

        String[] requestParamsTable = requestParams.split(";");
        Integer size = requestParamsTable[0].isEmpty() ? null : Integer.parseInt(requestParamsTable[0]);
        Integer page = requestParamsTable[1].isEmpty() ? null : Integer.parseInt(requestParamsTable[1]);
        String kod = requestParamsTable[2];
        Integer lp =  null;
        if(requestParamsTable.length > 3){
            lp = Integer.parseInt(requestParamsTable[3]);
        }
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(getP_PAGE_SIZE(), size);
        inParams.put(getP_PAGE(), page);
        inParams.put(PrzedmiotRepository.P_KOD, kod);
        inParams.put(P_LP, lp);
        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + super.getTableName() + "' dla size = " + size + ", page = " + page + ", kod = '"+kod+"'!");
        else return resultMapper(resultSet);
    }

    @Override
    public Material getById(Integer id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), id);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        inParams.put(PrzedmiotRepository.P_KOD, null);
        inParams.put(P_LP, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id = " + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Override
    public Integer save(Material data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_PRZEDM, data.getIdPrzedmiotu());
        inParams.put(P_TEMAT, data.getTemat());
        inParams.put(P_PLIK, data.getPlik());
        inParams.put(P_NAZWA_PLIKU, data.getNazwaPliku());
        inParams.put(P_EXT, data.getExt());
        inParams.put(P_OPIS, data.getOpis());
        inParams.put(P_WIDOCZNOSC, data.getWidocznosc());
        inParams.put(P_ID_ZADANIA, data.getIdZadania());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(Material data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdMaterialu());
        inParams.put(P_ID_PRZEDM, data.getIdPrzedmiotu());
        inParams.put(P_TEMAT, data.getTemat());
        inParams.put(P_PLIK, data.getPlik());
        inParams.put(P_NAZWA_PLIKU, data.getNazwaPliku());
        inParams.put(P_EXT, data.getExt());
        inParams.put(P_OPIS, data.getOpis());
        inParams.put(P_WIDOCZNOSC, data.getWidocznosc());
        inParams.put(P_ID_ZADANIA, data.getIdZadania());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Material> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Material> materialy = new ArrayList<>();

        for (Map<String, Object> row : resultSet){
            materialy.add(new Material(
                    (Integer) row.get(C_ID_MATER),
                    (Integer) row.get(C_ID_PRZEDM),
                    (LocalDateTime) row.get(C_DATA_WSTAW),
                    (Integer) row.get(C_LP),
                    (String) row.get(C_TEMAT),
                    (byte[]) row.get(C_PLIK),
                    (String) row.get(C_NAZWA_PLIKU),
                    (String) row.get(C_EXT),
                    (String) row.get(C_OPIS),
                    (Boolean) row.get(C_WIDOCZNOSC),
                    (Integer) row.get(C_ID_ZADANIA)
            ));

        }
        return materialy;
    }
}
