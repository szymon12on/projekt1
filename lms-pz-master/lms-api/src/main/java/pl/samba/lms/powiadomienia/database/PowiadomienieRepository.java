package pl.samba.lms.powiadomienia.database;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.powiadomienia.Powiadomienie;
import pl.samba.lms.przedmioty.przedmioty.database.PrzedmiotRepository;
import pl.samba.lms.przedmioty.zadania.zadania.Zadanie;
import pl.samba.lms.utils.constants.Flagi;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PowiadomienieRepository  extends  AbstractCrudRepository<Powiadomienie, Integer> {

    public static final String C_ID_POWIADOM = "id_powiadom";
    public static final String C_ID_ODBIORCY = "id_odbiorcy";
    public static final String C_DATA_WSTAW = "data_wstaw";
    public static final String C_TRESC = "tresc";
    public static final String C_ID_FLAGI = "id_flagi";

    public static final String P_ID_FLAGI = "p_id_flagi";
    public static final String P_LOGIN = "p_login";

    public PowiadomienieRepository() {
        super("powiadomienia","pk_id_powiadom");
    }

    @Override
    public Iterable<Powiadomienie> getAll(String requestParams) {
        /*
         * requestParamsTable
         * [0] size
         * [1] page
         * [2] login
         * */

        String[] requestParamsTable = requestParams.split(";");
        Integer size = requestParamsTable[0].isEmpty() ? null : Integer.parseInt(requestParamsTable[0]);
        Integer page = requestParamsTable[1].isEmpty() ? null : Integer.parseInt(requestParamsTable[1]);
        String login = requestParamsTable[2];

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(getP_PAGE_SIZE(), size);
        inParams.put(getP_PAGE(), page);
        inParams.put(P_LOGIN, login);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + super.getTableName() + "' dla size = " + size + ", page = " + page + ", kod = '"+login +"'!");
        else return resultMapper(resultSet);
    }

    @Override
    public Powiadomienie getById(Integer id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), id);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        inParams.put(P_LOGIN, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id = " + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Deprecated
    @Override
    public Integer save(Powiadomienie data) {
        /**
         * BRAK WSPARCIA
         * POWIADOMIENIA DODAWANA W BAZIE DANYCH
         */
        return null;
    }

    @Override
    public Integer update(Powiadomienie data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdPowiadomienia());
        inParams.put(P_ID_FLAGI, data.getFlaga().getId());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Powiadomienie> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Powiadomienie> powiadomienia = new ArrayList<>();

        for (Map<String, Object> row: resultSet) {
            powiadomienia.add(new Powiadomienie(
                    (Integer) row.get(C_ID_POWIADOM),
                    (Integer) row.get(C_ID_ODBIORCY),
                    (LocalDateTime) row.get(C_DATA_WSTAW),
                    (String) row.get(C_TRESC),
                    Flagi.getById((Integer) row.get(C_ID_FLAGI))
            ));
        }
        return powiadomienia;
    }
}
