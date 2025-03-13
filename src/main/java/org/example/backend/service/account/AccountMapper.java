package org.example.backend.service.account;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.backend.service.AccountVO;

@Mapper
public interface AccountMapper {

    int amount(@Param("id") int id);

    int accountId(@Param("id") int id);

    int deposit(@Param("id") int id, @Param("price") int price);

    int withdraw(@Param("id") int id, @Param("price") int price);

    String rank(@Param("accountId") int accountId);
}
