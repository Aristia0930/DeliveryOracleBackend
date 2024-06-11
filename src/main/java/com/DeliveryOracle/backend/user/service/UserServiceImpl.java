package com.DeliveryOracle.backend.user.service;

import com.DeliveryOracle.backend.user.dto.UserAuth;
import com.DeliveryOracle.backend.user.dto.Users;
import com.DeliveryOracle.backend.user.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service //서비스임을 알리는 어노테이션, 서비스로 빈이 등록되어 있어야 비즈니스 로직으로 사용가능하다.
//UserService의 실제 구현체 (클래스)
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder; //스프링시큐리티에 비밀번호 암호화 기능을 가져와서 자동주입 시킨다.

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    /* 회원 등록 (회원가입)
        1. 비밀번호 암호화
        2. 회원 등록
        3. 권한 등록
    */
    @Override
    public int insert(Users user) throws Exception {
        //비번 암호화
        String userPw = user.getUserPw(); //Users dto에서 넘어온 패스워드를 꺼내야한다.
        String encodedPw = passwordEncoder.encode(userPw);
        user.setUserPw(encodedPw);

        //회원 등록
        int result = userMapper.insert(user);

        //권한 등록
        if(result > 0) {
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(user.getUserId());
            userAuth.setAuth("ROLE_USER"); //기본 권한 : 사용자 권한(ROLE_USER)
            result = userMapper.insertAuth(userAuth);
        }
        return result;
    }

    //회원 조회
    @Override
    public Users select(int userNo) throws Exception {
        return userMapper.select(userNo);
    }

    //로그인
    @Override
    public void login(Users user, HttpServletRequest request) throws Exception {
        String username = user.getUserId();
        String password = user.getUserPw();
        log.info("username : " + username);
        log.info("password : " + password);

        // AuthenticationManager (인증을 관리하는 객체) : 이것을 사용하면 id, pw가 일치하는지 확인해주며 SpringSecurity Context에 등록을 시킬수가 있다.
        // ID, PW 인증 토큰을 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // 토큰에 요청 정보 등록
        token.setDetails(new WebAuthenticationDetails(request));//request 객체를 넣는 이유는 dto에서 넘어온 id, pw값이 실제로 요청 정보로 넘어온게 맞는지 확인시켜주는 작업을 하기 위해 사용한다.

        // 토큰을 이용한 인증 요청 - 로그인
        Authentication authentication = authenticationManager.authenticate(token); //토큰을 가지고 authenticationManager에 authenticate 메소드로 인증을 요청한다.

        log.info("인증 여부 : " + authentication.isAuthenticated());

        User authUser = (User) authentication.getPrincipal();
        log.info("인증된 사용자 ID : " + authUser.getUsername());

        // 시큐리티 컨텍스트 인증된 사용자 등록 : 인증된 사용된 정보가 담겨야 하는 영역
        //세션에 등록하여 사용하거나 시큐리티에 등록하려면
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //회원 정보 수정
    @Override
    public int update(Users user) throws Exception {
        //비밀번호 암호화
        String userPw = user.getUserPw();
        String encodedPw = passwordEncoder.encode(userPw);
        user.setUserPw(encodedPw);

        int result = userMapper.update(user);

        return result;
    }

    //회원 삭제 (회원 탈퇴)
    @Override
    public int delete(String userId) throws Exception {
        return userMapper.delete(userId);
    }
}
