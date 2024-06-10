package pl.samba.lms.przedmioty.rejestracja.database;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.rejestracja.UczenPrzedmiot;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class UczenPrzedmiotRepository extends AbstractCrudRepository<UczenPrzedmiot, Integer> {
    private static final String C_ID_OCENY = "id_encji";
    private static final String C_ID_PRZEM = "id_przedm";
    private static final String C_ID_UCZNIA = "id_ucznia";
    private static final String C_OCENA = "ocena";
    private static final String C_DATA_WYSTAW_OC = "data_wystaw_oc";

    private static final String P_ID_UCZNIA = "p_id_ucznia";
    private static final String P_ID_PRZEDM = "p_id_przedm";
    private static final String P_OCENA = "p_ocena";

    public UczenPrzedmiotRepository() {
        super("uczen_przedmiot","pk_id_encji");
    }

    @Override
    public Integer save(UczenPrzedmiot data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_UCZNIA, data.getIdUcznia());
        inParams.put(P_ID_PRZEDM, data.getIdPrzedmiotu());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    public Iterable<UczenPrzedmiot> get(String requestParams) {
        /*
         * requestParamsTable
         * [0] id_uzytk
         * [1] id_przedm
         * [2] size
         * [3] page
         * */
        Integer idUzytkownika = null;
        Integer idPrzededmiotu = null;
        Integer page = null;
        Integer size = null;

        if(requestParams != null){
            String[] requestParamsTable = requestParams.split(";");
            idUzytkownika = Integer.parseInt(requestParamsTable[0]);
            if(idUzytkownika.equals(-1)){
                idUzytkownika = null;
            }

            idPrzededmiotu = Integer.parseInt(requestParamsTable[1]);
            if(idPrzededmiotu.equals(-1)){
                idPrzededmiotu = null;
            }
            size = Integer.parseInt(requestParamsTable[2]);
            page = Integer.parseInt(requestParamsTable[3]);
        }

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(P_ID_UCZNIA, idUzytkownika);
        inParams.put(P_ID_PRZEDM, idPrzededmiotu);
        inParams.put(getP_PAGE_SIZE(), size);
        inParams.put(getP_PAGE(), page);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + super.getTableName() + "'!");
        else return resultMapper(resultSet);
    }

    public UczenPrzedmiot get(Integer idUcznia, Integer idPrzedmiotu){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(P_ID_UCZNIA, idUcznia);
        inParams.put(P_ID_PRZEDM, idPrzedmiotu);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla idUcznia=" + idUcznia +", idPrzedmiotu="+idPrzedmiotu+ "!");
        else return resultMapper(resultSet).iterator().next();
    }
    @Override
    public UczenPrzedmiot getById(Integer id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), id);
        inParams.put(P_ID_UCZNIA,  null);
        inParams.put(P_ID_PRZEDM,  null);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id = " + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }
    @Override
    public Integer update(UczenPrzedmiot data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdEncji());
        inParams.put(P_ID_UCZNIA, data.getIdUcznia());
        inParams.put(P_ID_PRZEDM, data.getIdPrzedmiotu());
        inParams.put(P_OCENA, data.getOcena());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    /**
     * Metoda do usuwania powiązania pomiędzy uczniem i przedmiotem
     * korzystać zamiast {@link pl.samba.lms.przedmioty.rejestracja.database.UczenPrzedmiotRepository#delete(Integer)}
     * @param idUcznia id ucznia
     * @param idPrzemiotu id przedmiotu
     * @return true
     */
    public boolean delete(Integer idUcznia, Integer idPrzemiotu) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getDeleteProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_UCZNIA, idUcznia);
        inParams.put(P_ID_PRZEDM, idPrzemiotu);

        jdbcCall.execute(inParams);
        return true;
    }

    @Override
    public Iterable<UczenPrzedmiot> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<UczenPrzedmiot> list = new ArrayList<>();
        for(Map<String, Object> row: resultSet){
            UczenPrzedmiot data = new UczenPrzedmiot(
                    (Integer) row.get(C_ID_OCENY),
                    (Integer) row.get(C_ID_PRZEM),
                    (Integer) row.get(C_ID_UCZNIA),
                    (Integer) row.get(C_OCENA),
                    (LocalDateTime) row.get(C_DATA_WYSTAW_OC)
            );
            list.add(data);
        }
        return list;
    }
    /*
    ======NIE DZIAŁAJACE METODY=======
     */
    @Override
    @Deprecated
    public boolean delete(Integer id) {
        // brak procedury usuwającej po id encji
        return false;
    }
    @Override
    @Deprecated
    public Iterable<UczenPrzedmiot> getAll() {
        //brak procedury
        return  null;
    }
    @Override
    @Deprecated
    public Iterable<UczenPrzedmiot> getAll(String requestParams) {
        //brak procedury
        return  null;
    }
}
