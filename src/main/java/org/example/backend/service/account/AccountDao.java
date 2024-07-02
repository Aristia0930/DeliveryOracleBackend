package org.example.backend.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //계좌금액학인하기
    public int amount(int id){
        String sql="select account_amount from account where owner_id=?;";
        int rs=-1;
        try{
            rs=jdbcTemplate.queryForObject(sql,Integer.class,id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

    //계좌넘버 확인
    public int accountId(int id){
        String sql="select account_id from account where owner_id=?;";
        int rs=-1;
        try{
            rs=jdbcTemplate.queryForObject(sql,Integer.class,id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }



    public int deposit(int id,int price){
        String sql="INSERT INTO accountstatus (account_id,amount,type) " +
                "VALUES (?,?,?);";
        int rs=-1;
        try{
            jdbcTemplate.update(sql,id,price,"입금");
            rs=1;

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

    public int withdraw(int id,int price){
        System.out.println("결제 실행중");
        String sql="INSERT INTO accountstatus (account_id,amount,type) " +
                "VALUES (?,?,?);";
        int rs=-1;
        try{
            jdbcTemplate.update(sql,id,price,"결제");
            System.out.println("성공");
            rs=1;

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

}
