package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class StatusDAO {
    private String status;
    private Timestamp timestamp;

    public static StatusDAO toDAO(ResultSet rs) throws SQLException {
        return new StatusDAO(
                rs.getString("status_status"),
                rs.getTimestamp("status_timestamp")
        );
    }

    public static List<StatusDAO> toDAOs(ResultSet rs) throws SQLException {
        String[] statusArr = (String[])rs.getArray("statuses_status").getArray();
        Timestamp[] timestampArr = (Timestamp[]) rs.getArray("statuses_status").getArray();

        if (statusArr == null || timestampArr == null) {
            return null;
        }

        List<StatusDAO> daos = new ArrayList<>();
        for (int i = 0; i < statusArr.length; i++) {
            daos.add(new StatusDAO(statusArr[i], timestampArr[i] ));
        }

        return daos;
    }

}
