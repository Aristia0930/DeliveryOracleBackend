package com.DeliveryOracle.backend.user.controller;

import com.DeliveryOracle.backend.user.dto.CustomUser;
import com.DeliveryOracle.backend.user.dto.Users;
import com.DeliveryOracle.backend.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
    회원 정보
    [GET] /users/info - 회원정보 조회 (ROLE_USER)
    [POST] /users - 회원가입 ALL
    [PUT] /users - 회원정보 수정 (ROLE_USER)
    [DELETE] /users - 회원탈퇴 (ROLE_ADMIN)
*/
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    //서비스 요청을 해야한다.
    @Autowired
    private UserService userService;

    /* 사용자 정보 조회
       @param customer
       @return
    */
    @Secured("ROLE_USER") // USER 권한 설정
    @GetMapping("/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUser customUser) {
        log.info(":::: customUser ::::");
        log.info("customUser : " + customUser);

        Users user = customUser.getUser();
        log.info("user : " + user);

        // 인증된 사용자 정보
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);

        // 인증 되지 않았을 경우
        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    /* 회원가입
        @param entity
        @return
        @throws Exception
    */

    @PostMapping("")
    public ResponseEntity<?> join(@RequestBody Users user) throws Exception {
        log.info("[POST] - /users");
        int result = userService.insert(user);

        if(result > 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    /* 회원 정보 수정
        @param user
        @return
        @throws Exception
    */
    //user 권한을 가졌을때만 처리할 수 있도록 하기
    @Secured("ROLE_USER") // USER 권한 설정
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Users user) throws Exception {
        log.info("[PUT] - /users");
        int result = userService.insert(user);

        if(result > 0) {
            log.info("회원 수정 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원 수정 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    /* 회원 탈퇴
        @param userId
        @return
        @throws Exception
    */

    //admin 권한을 가졌을때만 처리할 수 있도록 하기
    @Secured("ROLE_ADMIN") // ADMIN 권한 설정
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> destroy(@PathVariable("userId") String userId) throws Exception {
        log.info("[DELETE] - /users/{userId}");

        int result = userService.delete(userId);

        if(result > 0) {
            log.info("회원 삭제 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원 삭제 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }
}