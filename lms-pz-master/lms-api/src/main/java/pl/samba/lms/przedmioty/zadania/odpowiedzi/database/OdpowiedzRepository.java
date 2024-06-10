package pl.samba.lms.przedmioty.zadania.odpowiedzi.database;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.przedmioty.database.PrzedmiotRepository;
import pl.samba.lms.przedmioty.zadania.ZadanieUtils;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.Odpowiedz;
import pl.samba.lms.przedmioty.zadania.odpowiedzi.rodzaje.OdpowiedzInterface;
import pl.samba.lms.utils.database.AbstractCrudRepository;
import pl.samba.lms.uzytkownicy.uzytkownicy.database.UzytkownikRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OdpowiedzRepository extends AbstractCrudRepository<Odpowiedz, Integer> {
    public static final String C_ID_ODPOWIEDZI = "id_odpowiedzi";
    public static final String C_ID_ZADANIA = "id_zadania";
    public static final String C_ID_UCZNIA = "id_ucznia";
    public static final String C_TRESC = "tresc";
    public static final String C_KOMENTARZ = "koment";
    public static final String C_OCENA = "ocena";
    public static final String C_DATA_WSTAW = "data_wstaw";
    public static final String C_DATA_OCENY = "data_oceny";

    public static final String P_ID_ZADANIA = "p_id_zadania";
    public static final String P_ID_UCZNIA = "p_id_ucznia";
    public static final String P_TRESC = "p_tresc";
    public static final String P_KOMENTARZ = "p_koment";
    public static final String P_OCENA = "p_ocena";
    public static final String P_DATA_OCENY = "p_data_oceny";

    public OdpowiedzRepository() {
        super("odpowiedzi_zadania", "pk_id_odpowiedzi");
    }


    @Override
    public Iterable<Odpowiedz> getAll(String requestParams) {
        /*
         * requestParamsTable
         * [0] size
         * [1] page
         * [2] kod
         * [3] login
         * */

        String[] requestParamsTable = requestParams.split(";");
        Integer size = requestParamsTable[0].isEmpty() ? null : Integer.parseInt(requestParamsTable[0]);
        Integer page = requestParamsTable[1].isEmpty() ? null : Integer.parseInt(requestParamsTable[1]);
        String kod = requestParamsTable[2].isEmpty() ? null: requestParamsTable[2];
        String login = null;

        if(requestParamsTable.length > 3){
            login = requestParamsTable[3];
        }

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), null);
        inParams.put(getP_PAGE_SIZE(), size);
        inParams.put(getP_PAGE(), page);
        inParams.put(PrzedmiotRepository.P_KOD, kod);
        inParams.put(UzytkownikRepository.P_LOGIN, login);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych w tabeli '" + super.getTableName() + "' dla size=" + size + ", page=" + page + ", kod='"+kod+"', login='"+login+"'!");
        else return resultMapper(resultSet);
    }

    @Override
    public Odpowiedz getById(Integer id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getReadProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), id);
        inParams.put(getP_PAGE_SIZE(), null);
        inParams.put(getP_PAGE(), null);
        inParams.put(PrzedmiotRepository.P_KOD, null);
        inParams.put(UzytkownikRepository.P_LOGIN, null);

        Map<String, Object> result = jdbcCall.execute(inParams);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

        if(resultSet.isEmpty()) throw new NoSuchElementException("Brak danych dla klucza głównego id = " + id + "!");
        else return resultMapper(resultSet).iterator().next();
    }

    @Override
    public Integer save(Odpowiedz data) {
        String tresc = data.getTresc().toString();

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(getInsertProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_ZADANIA, data.getIdZadania());
        inParams.put(P_ID_UCZNIA, data.getIdUcznia());
        inParams.put(P_TRESC,
                Base64.getEncoder().encode(tresc.getBytes(StandardCharsets.UTF_8))
        );
        inParams.put(P_KOMENTARZ, data.getKomentarz());
        inParams.put(P_OCENA, data.getOcena());
        inParams.put(P_DATA_OCENY, data.getDataOcenienia());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(Odpowiedz data) {
        String tresc = data.getTresc().toString();

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(getUpdateProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(getPkColumnName(), data.getIdOdpowiedzi());
        inParams.put(P_ID_ZADANIA, data.getIdZadania());
        inParams.put(P_ID_UCZNIA, data.getIdUcznia());
        inParams.put(P_TRESC,
                Base64.getEncoder().encode(tresc.getBytes(StandardCharsets.UTF_8))
        );
        inParams.put(P_KOMENTARZ, data.getKomentarz());
        inParams.put(P_OCENA, data.getOcena());
        inParams.put(P_DATA_OCENY, data.getDataOcenienia());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Odpowiedz> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Odpowiedz> odpowiedzi = new ArrayList<>();

        for(Map<String, Object> row: resultSet){
            byte[] trescBytes = Base64.getDecoder().decode((byte[]) row.get(C_TRESC));

            List<OdpowiedzInterface> odpowiedziList = ZadanieUtils.odpowiedziFactory(
                    new String(trescBytes, StandardCharsets.UTF_8)
            );

            odpowiedzi.add(new Odpowiedz(
                    (Integer) row.get(C_ID_ODPOWIEDZI),
                    (Integer) row.get(C_ID_ZADANIA),
                    (Integer) row.get(C_ID_UCZNIA),
                    odpowiedziList,
                    (String) row.get(C_KOMENTARZ),
                    (Integer) row.get(C_OCENA),
                    (LocalDateTime) row.get(C_DATA_WSTAW),
                    (LocalDateTime) row.get(C_DATA_OCENY)
            ));
        }
        return odpowiedzi;
    }
}
