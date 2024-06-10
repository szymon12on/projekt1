package pl.samba.lms.przedmioty.przedmioty.database;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.przedmioty.Przedmiot;
import pl.samba.lms.utils.constants.Status;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.util.*;

@Repository
public class PrzedmiotRepository extends AbstractCrudRepository<Przedmiot, Integer> {

    public static final String C_ID_PRZEDM = "id_przedm";
    public static final String C_KOD = "kod";
    public static final String C_NAZWA = "nazwa";
    public static final String C_ID_PROW = "id_prow";
    public static final String C_LIMIT_MIEJSC = "limit_miejsc";
    public static final String C_OPIS = "opis";
    public static final String C_WAR_ZALICZ = "war_zalicz";
    public static final String C_ID_OKRESU = "id_okresu";
    public static final String C_KOD_STATUS = "kod_status";
    public static final String C_REJESTR_UCZN = "rejestr_uczn";

    public static final String P_NAZWA = "p_nazwa";
    public static final String P_ID_PROW = "p_id_prow";
    public static final String P_LIMIT_MIEJSC = "p_limit_miejsc";
    public static final String P_KOD = "p_kod";
    public static final String P_OPIS = "p_opis";
    public static final String P_WAR_ZALICZ = "p_war_zalicz";
    public static final String P_ID_OKRESU = "p_id_okresu";
    public static final String P_KOD_STATUS = "p_kod_status";
    public static final String P_REJESTR_UCZN = "p_rejestr_uczn";
    public static final String P_ID_UCZNIA = "p_id_ucznia";

    public PrzedmiotRepository() {
        super("przedmioty", "pk_id_przedm");
    }

    @Override
    public Iterable<Przedmiot> getAll(String requestParams) {
        /*
         * requestParamsTable
         * [0] size
         * [1] page
         * */
        String[] requestParamsTable = requestParams.split(";");
        Integer size = requestParamsTable[0].isEmpty() ? null : Integer.parseInt(requestParamsTable[0]);
        Integer page = requestParamsTable[1].isEmpty() ? null : Integer.parseInt(requestParamsTable[1]);
        Integer idUcznia = Integer.parseInt(requestParamsTable[2]) == -1 ? null : Integer.parseInt(requestParamsTable[2]);


        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(getP_PAGE_SIZE(), size);
        inParams.put(getP_PAGE(), page);
        inParams.put(getP_UNIQUE(), null);
        inParams.put(P_ID_UCZNIA, idUcznia);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + super.getTableName() + "' dla size=" + size + ", page=" + page + "!");
        else return resultMapper(resultSet);
    }

    @Override
    public Przedmiot getById(Integer id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), id);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        inParams.put(getP_UNIQUE(), null);
        inParams.put(P_ID_UCZNIA, null);
        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id=" + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Override
    public Przedmiot getByUnique(String unique){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        inParams.put(getP_UNIQUE(), unique);
        inParams.put(P_ID_UCZNIA, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza unikalnego '" + unique+ "'!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Override
    public Integer save(Przedmiot data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_NAZWA, data.getNazwa());
        inParams.put(P_ID_PROW, data.getIdProwadzacego());
        inParams.put(P_LIMIT_MIEJSC, data.getLimit());
        inParams.put(P_OPIS, data.getOpis());
        inParams.put(P_WAR_ZALICZ, data.getWarunkiZaliczenia());
        inParams.put(P_ID_OKRESU, data.getIdOkresu());
        inParams.put(P_KOD_STATUS, data.getStatus().getKod());
        inParams.put(P_REJESTR_UCZN, data.getCzyRejestrUczn());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(Przedmiot data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdPrzedmiotu());
        inParams.put(P_NAZWA, data.getNazwa());
        inParams.put(P_ID_PROW, data.getIdProwadzacego());
        inParams.put(P_LIMIT_MIEJSC, data.getLimit());
        inParams.put(P_OPIS, data.getOpis());
        inParams.put(P_WAR_ZALICZ, data.getWarunkiZaliczenia());
        inParams.put(P_ID_OKRESU, data.getIdOkresu());
        inParams.put(P_KOD_STATUS, data.getStatus().getKod());
        inParams.put(P_REJESTR_UCZN, data.getCzyRejestrUczn());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Przedmiot> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Przedmiot> przedmiotList = new ArrayList<>();

        for (Map<String, Object> row:
            resultSet ) {
            Przedmiot przedmiot = new Przedmiot(
                    (Integer) row.get(C_ID_PRZEDM),
                    (String) row.get(C_KOD),
                    (String) row.get(C_NAZWA),
                    (Integer) row.get(C_ID_PROW),
                    (Integer) row.get(C_LIMIT_MIEJSC),
                    (String) row.get(C_OPIS),
                    (String) row.get(C_WAR_ZALICZ),
                    (Integer) row.get(C_ID_OKRESU),
                    Status.getStatusByKod((String) row.get(C_KOD_STATUS)),
                    (Boolean) row.get(C_REJESTR_UCZN)
            );
            przedmiotList.add(przedmiot);
        }

        return przedmiotList;
    }
}
