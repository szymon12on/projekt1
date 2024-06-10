package pl.samba.lms.forum.odpowiedzi.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.forum.odpowiedzi.ForumOdp;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ForumOdpRepository extends AbstractCrudRepository<ForumOdp, Integer> {
    public static final String C_ID_ODP = "id_odp";
    public static final String C_ID_WPIS = "id_wpis";
    public static final String C_ID_UZYTK = "id_uzytk";
    public static final String C_TRESC = "tresc";
    public static final String C_DATA_WPIS = "data_wpis";

    public static final String P_ID_WPIS = "p_id_wpis";
    public static final String P_ID_UZYTK = "p_id_uzytk";
    public static final String P_TRESC = "p_tresc";
    public static final String P_DATA_WPIS = "p_data_wpis";

    @Autowired
    public ForumOdpRepository() {
        super("forum_odp", "pk_id_odp");
    }

    @Override
    public Integer save(ForumOdp data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_WPIS, data.getIdWpis());
        inParams.put(P_ID_UZYTK, data.getIdUzytk());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WPIS, data.getDataWpis());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(ForumOdp data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdOdp());
        inParams.put(P_ID_WPIS, data.getIdWpis());
        inParams.put(P_ID_UZYTK, data.getIdUzytk());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WPIS, data.getDataWpis());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<ForumOdp> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<ForumOdp> odpowiedzi = new ArrayList<>();
        for (Map<String, Object> row : resultSet) {
            ForumOdp odp = new ForumOdp(
                    (Integer) row.get(C_ID_ODP),
                    (Integer) row.get(C_ID_WPIS),
                    (Integer) row.get(C_ID_UZYTK),
                    (String) row.get(C_TRESC),
                    (LocalDateTime) row.get(C_DATA_WPIS)
            );

            odpowiedzi.add(odp);
        }
        return odpowiedzi;
    }
}