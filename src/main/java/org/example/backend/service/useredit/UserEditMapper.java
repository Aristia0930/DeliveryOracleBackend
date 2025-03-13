package org.example.backend.service.useredit;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.backend.user.dto.User;

@Mapper
public interface UserEditMapper {

    User select(@Param("userId") int userId);

    int updateName(@Param("userId") int userId, @Param("nickname") String nickname);

    int updatePass(@Param("userId") int userId, @Param("password") String password);
}
