package pl.samba.lms.forum.wpisy.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.forum.wpisy.ForumWpis;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ForumWpisRepository extends AbstractCrudRepository<ForumWpis, Integer>{
    public static final String C_ID_WPIS = "id_wpis";
    public static final String C_ID_PRZEDM = "id_przedm";
    public static final String C_ID_UZYTK = "id_uzytk";
    public static final String C_TEMAT = "temat";
    public static final String C_TRESC = "tresc";
    public static final String C_DATA_WPIS = "data_wpis";

    public static final String P_ID_PRZEDM = "p_id_przedm";
    public static final String P_ID_UZYTK = "p_id_uzytk";
    public static final String P_TEMAT = "p_temat";
    public static final String P_TRESC = "p_tresc";
    public static final String P_DATA_WPIS = "p_data_wpis";

    @Autowired
    public ForumWpisRepository() {
        super("forum_wpisy", "pk_id_wpis");
    }

    @Override
    public Integer save(ForumWpis data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_ID_PRZEDM, data.getIdPrzedm());
        inParams.put(P_ID_UZYTK, data.getIdUzytk());
        inParams.put(P_TEMAT, data.getTemat());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WPIS, data.getDataWpis());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(ForumWpis data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());
        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdWpis());
        inParams.put(P_ID_PRZEDM, data.getIdPrzedm());
        inParams.put(P_ID_UZYTK, data.getIdUzytk());
        inParams.put(P_TEMAT, data.getTemat());
        inParams.put(P_TRESC, data.getTresc());
        inParams.put(P_DATA_WPIS, data.getDataWpis());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<ForumWpis> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<ForumWpis> wpisy = new ArrayList<>();
        for (Map<String, Object> row : resultSet) {
            ForumWpis wpis = new ForumWpis(
                    (Integer) row.get(C_ID_WPIS),
                    (Integer) row.get(C_ID_PRZEDM),
                    (Integer) row.get(C_ID_UZYTK),
                    (String) row.get(C_TEMAT),
                    (String) row.get(C_TRESC),
                    ((LocalDateTime) row.get(C_DATA_WPIS))
            );

            wpisy.add(wpis);
        }
        return wpisy;
    }
}