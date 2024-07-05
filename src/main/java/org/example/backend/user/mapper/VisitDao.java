package org.example.backend.user.mapper;

import org.example.backend.user.dto.VisitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VisitDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Integer> checkMapper = (rs, rowNum) -> rs.getInt(1);


    //사용자 오늘 방문했는지 체크하기
    public int check(VisitVo visitVo){
        String sql="select count(*) from visitors where email=? and visit_date=?;";
        try{

            return jdbcTemplate.queryForObject(sql,checkMapper,visitVo.getEmail(),visitVo.getVisitDate());

        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }
    //사용자 기록하기
    public void insert(VisitVo visitVo) {
        String sql = "INSERT INTO visitors (email, visit_date) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, visitVo.getEmail(), visitVo.getVisitDate());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
