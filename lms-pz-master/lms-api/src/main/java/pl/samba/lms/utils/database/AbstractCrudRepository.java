package pl.samba.lms.utils.database;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Abstrakcyjna klasa służąca do dostarczenia podstawowej implementacji operacji CRUD
 *  dla repozytorium .
 *
 * @param <T> Typ obiektów zarządzanych przez repozytorium.
 * @param <K> Typ unitókalnego identyfikatora dla obiekw.
 * @author bsurma
 */
public abstract class AbstractCrudRepository<T, K> implements CrudRepositoryInterface<T, K>{

    @Getter
    private final static String SCHEMA = "lms";
    @Getter
    private final static String P_PAGE_SIZE = "p_size";
    @Getter
    private final static String P_PAGE = "p_page";
    @Getter
    private final String P_UNIQUE = "p_unique";
    @Getter
    private final String tableName;
    @Getter
    private final String pkColumnName;


    @Getter
    private final String readProcName;
    @Getter
    private final String insertProcName;
    @Getter
    private final String updateProcName;
    @Getter
    private final String deleteProcName;

    @Getter
    @Autowired
    private JdbcTemplate jdbc;

    public AbstractCrudRepository(
            String tableName,
            String pkColumnName
    ){
        this.tableName = tableName;
        this.pkColumnName = pkColumnName;

        readProcName = tableName + "_read";
        insertProcName = tableName + "_ins";
        updateProcName = tableName + "_upd";
        deleteProcName = tableName + "_del";
    }
    @Override
    public Iterable<T> getAll() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc)
                .withSchemaName(getSCHEMA())
                .withProcedureName(readProcName);
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(pkColumnName, null);
        inParams.put(P_PAGE_SIZE, null);
        inParams.put(P_PAGE, null);
        inParams.put(P_UNIQUE, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + tableName + "'!");
        else return resultMapper(resultSet);
    }

    @Override
    public Iterable<T> getAll(String requestParams) {
        /*
         * requestParamsTable
         * [0] size
         * [1] page
         * */
        String[] requestParamsTable = requestParams.split(";");
        int size = Integer.parseInt(requestParamsTable[0]);
        int page = Integer.parseInt(requestParamsTable[1]);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc)
                .withSchemaName(getSCHEMA())
                .withProcedureName(readProcName);
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(pkColumnName, null);
        inParams.put(P_PAGE_SIZE, size);
        inParams.put(P_PAGE, page);
        inParams.put(P_UNIQUE, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + tableName + "' dla size=" + size + ", page=" + page + "!");
        else return resultMapper(resultSet);

    }

    @Override
    public T getById(K id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc)
                .withSchemaName(getSCHEMA())
                .withProcedureName(readProcName);
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(pkColumnName, id);
        inParams.put(P_PAGE_SIZE, null);
        inParams.put(P_PAGE, null);
        inParams.put(P_UNIQUE, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id=" + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Override
    public T getByUnique(String unique){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc)
                .withSchemaName(getSCHEMA())
                .withProcedureName(readProcName);
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(pkColumnName, null);
        inParams.put(P_PAGE_SIZE, null);
        inParams.put(P_PAGE, null);
        inParams.put(P_UNIQUE, unique);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza unikalnego '" + unique+ "'!");
        else return resultMapper(resultSet).iterator().next();
    }
    @Override
    public boolean delete(K id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc)
                .withSchemaName(getSCHEMA())
                .withProcedureName(deleteProcName);
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(pkColumnName, id);

        jdbcCall.execute(inParams);
        return true;
    }


}
