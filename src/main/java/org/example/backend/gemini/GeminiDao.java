package org.example.backend.gemini;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GeminiDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> order(int id) {
        String sql = "SELECT order_details \n" +
                "FROM orderinformation \n" +
                "WHERE customer_id = ? \n" +
                "ORDER BY order_date DESC \n" +
                "LIMIT 5;";
        List<String> order_info = new ArrayList<>();

        try {
            order_info = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("order_details");
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return order_info;

    }

    public List<String> menuList() {

        String sql="select menu_name from storeinformation;";
        List<String> menus = new ArrayList<>();
        try {
            menus = jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("menu_name");
                }
            });
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return menus;

    }

}
