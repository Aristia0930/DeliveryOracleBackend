package com.DeliveryOracle.backend.user.security.custom;

import com.DeliveryOracle.backend.user.dto.CustomUser;
import com.DeliveryOracle.backend.user.dto.Users;
import com.DeliveryOracle.backend.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
// 사용자 인증 방식 셋팅하기 위해 사용
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loadUserByUsername() 설정을 해두면 Spring Security가 유저 정보를 읽어온다.
        log.info("login - loadUserByUsername : " + username);

        Users user = userMapper.login(username); //mybatis로 DB 연결을 하고 있기 때문에 user의 id로 DTO에서 넘겨 받는 Users 객체를 조회한다.

        if(user == null) {
            log.info("일치하는 ID가 없습니다!");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + username);
        }

        //예외를 빠져나왔을 경우에는 user id가 존재하는 것이기 때문에 로그를 찍어 user id를 확인 해준다.
        log.info("user : ");
        log.info(user.toString());

        // User -> CustomUser
        CustomUser customUser = new CustomUser(user);

        log.info("customUser : ");
        log.info(customUser.toString());

        return customUser;
    }
}
