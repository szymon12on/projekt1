package pl.samba.lms.wiadomosci.prywatne.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.wiadomosci.prywatne.WiadomosciPrywatne;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class WiadomosciPrywatneRepository extends AbstractCrudRepository<WiadomosciPrywatne, Integer> {
    public static final String C_ID_WIADOMOSCI = "id_wiadomosci";
    public static final String C_ID_NADAWCY = "id_nadawcy";
    public static final String C_ID_ODIORCY = "id_odbiorcy";
    public static final String C_TRESC = "tresc";
    public static final String C_DATA_WYSLANIA = "data_wyslania";
    public static final String C_ID_FLAGI = "id_flagi";

    public static final String P_ID_NADAWCY = "p_id_nadawcy";
    public static final String P_ID_ODIORCY = "p_id_odbiorcy";
    public static final String P_TRESC = "p_tresc";
    public static final String P_DATA_WYSLANIA = "p_data_wyslania";
    public static final String P_ID_FLAGI = "p_id_flagi";

    @Autowired
    public WiadomosciPrywatneRepository() {
        super("wiadomosci_prywatne", "pk_id_wiadomosci");
    }

    @Override
    public Integer save(WiadomosciPrywatne data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_NADAWCY, data.getIdNadawcy());
        inParams.put(P_ID_ODIORCY, data.getIdOdbiorcy());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WYSLANIA, data.getDataWyslania());
        inParams.put(P_ID_FLAGI, data.getIdFlagi());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(WiadomosciPrywatne data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdWiadomosci());
        inParams.put(P_ID_NADAWCY, data.getIdNadawcy());
        inParams.put(P_ID_ODIORCY, data.getIdOdbiorcy());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WYSLANIA, data.getDataWyslania());
        inParams.put(P_ID_FLAGI, data.getIdFlagi());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<WiadomosciPrywatne> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<WiadomosciPrywatne> wiadomosciPrywatne = new ArrayList<>();
        for (Map<String, Object> row : resultSet) {
            WiadomosciPrywatne wiad = new WiadomosciPrywatne(
                    (Integer) row.get(C_ID_WIADOMOSCI),
                    (Integer) row.get(C_ID_NADAWCY),
                    (Integer) row.get(C_ID_ODIORCY),
                    (String) row.get(C_TRESC),
                    (LocalDateTime) row.get(C_DATA_WYSLANIA),
                    (Integer) row.get(C_ID_FLAGI)
            );

            wiadomosciPrywatne.add(wiad);
        }
        return wiadomosciPrywatne;
    }

    public Iterable<WiadomosciPrywatne> getMessagesBetweenUsers(Integer idUser1, Integer idUser2) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbc())
                .withCatalogName("lms")
                .withProcedureName("get_wiadomosci_prywatne_between_users")
                .declareParameters(
                        new SqlParameter("_iduser1", Types.INTEGER),
                        new SqlParameter("_iduser2", Types.INTEGER))
                .returningResultSet("result", new BeanPropertyRowMapper<>(WiadomosciPrywatne.class));

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("_iduser1", idUser1);
        inParams.put("_iduser2", idUser2);

        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<WiadomosciPrywatne>) result.get("result");
    }
}
