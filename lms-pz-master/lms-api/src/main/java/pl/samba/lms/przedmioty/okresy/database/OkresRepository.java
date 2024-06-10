package pl.samba.lms.przedmioty.okresy.database;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.samba.lms.przedmioty.okresy.Okres;
import pl.samba.lms.utils.database.AbstractCrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OkresRepository extends AbstractCrudRepository<Okres, Integer> {
    public static final String C_ID_OKRESU = "id_okresu";
    public static final String C_KOD = "kod";
    public static final String C_DATA_POCZ = "data_pocz";
    public static final String C_DATA_KONC = "data_konc";

    public static final String P_KOD = "p_kod";
    public static final String P_DATA_POCZ = "p_data_pocz";
    public static final String P_DATA_KONC = "p_data_konc";
    public OkresRepository(){
        super("okresy", "pk_id_okresu");
    }
    @Override
    public Integer save(Okres data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getInsertProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(P_KOD, data.getKod());
        inParams.put(P_DATA_POCZ, data.getDataPoczatku());
        inParams.put(P_DATA_KONC, data.getDataKonca());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Integer update(Okres data) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(super.getJdbc())
                .withSchemaName(getSCHEMA())
                .withProcedureName(super.getUpdateProcName());

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(super.getPkColumnName(), data.getIdOkresu());
        inParams.put(P_KOD, data.getKod());
        inParams.put(P_DATA_POCZ, data.getDataPoczatku());
        inParams.put(P_DATA_KONC, data.getDataKonca());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (Integer) result.get(super.getPkColumnName());
    }

    @Override
    public Iterable<Okres> resultMapper(Iterable<Map<String, Object>> resultSet) {
        List<Okres> okresy = new ArrayList<>();
        for(Map<String, Object> row: resultSet){
            Okres okres = new Okres(
                    (Integer) row.get(C_ID_OKRESU),
                    (String) row.get(C_KOD),
                    (LocalDateTime) row.get(C_DATA_POCZ),
                    (LocalDateTime) row.get(C_DATA_KONC)
            );

            okresy.add(okres);
        }

        return okresy;
    }
}
