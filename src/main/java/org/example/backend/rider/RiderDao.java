package org.example.backend.rider;


import org.example.backend.service.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RiderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //배달전체 목록 불러오기
    public List<RiderVo> orderlist(){


        String sql = "SELECT \n" +
                "    o.order_id,\n" +
                "    o.store_id,\n" +
                "    s.store_name,\n" +
                "    u.Email AS store_owner_email,\n" +
                "    o.user_x,\n" +
                "    o.user_y,\n" +
                "    s.store_x,\n" +
                "    s.store_y\n" +
                "FROM \n" +
                "    OrderInformation o\n" +
                "JOIN \n" +
                "    StoreRegistration s ON o.store_id = s.store_id\n" +
                "JOIN \n" +
                "    UserInformation u ON s.owner_id = u.user_id\n" +
                "WHERE  o.order_approval_status = 1;";
        List<RiderVo> riderVos=new ArrayList<RiderVo>();
        RowMapper<RiderVo> rowMapper= BeanPropertyRowMapper.newInstance(RiderVo.class);
        try {
            riderVos=jdbcTemplate.query(sql, rowMapper);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return riderVos;
    }
}
