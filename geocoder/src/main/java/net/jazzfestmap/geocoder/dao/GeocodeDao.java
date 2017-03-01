package net.jazzfestmap.geocoder.dao;

import net.jazzfestmap.coords.LatLng;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Сергей on 01.03.2017.
 * Demo-dao для быстрого заполнения БД
 */
@Component
public class GeocodeDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    public Collection<String> getCityNames() {
        return jdbcTemplate.query("SELECT concat(city,', ', country) AS citystr FROM simple_festival_entity " +
                        "WHERE (lat IS NULL) OR (lon IS NULL) OR (lat = 0) OR (lon = 0)",
                (resultSet, i) -> resultSet.getString("citystr"));
    }

    public void saveCityCoords(Map<String, LatLng> coords) {
        List<Object[]> paramList = new ArrayList<>();
        for (Map.Entry<String, LatLng> entry : coords.entrySet()) {
            if (entry.getValue() != null)
                paramList.add(new Object[] {entry.getValue().getLatitude(), entry.getValue().getLongitude(), entry.getKey()});
        }
        jdbcTemplate.batchUpdate("UPDATE simple_festival_entity SET lat = ?, lon = ? WHERE concat(city,', ', country) = ?",
                paramList);
    }
}
