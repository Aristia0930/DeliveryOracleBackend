package org.example.backend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.user.dto.CustomUser;
import org.example.backend.user.dto.User;
import org.example.backend.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.user.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
    회원 정보
    [GET] /user/info - 회원정보 조회 (ROLE_USER)
    [POST] /user - 회원가입 ALL
    [PUT] /user - 회원정보 수정 (ROLE_USER)
    [DELETE] /user - 회원탈퇴 (ROLE_ADMIN)
*/
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "유저 등록 / 로그인 api", description = "유저 등록/로그인API")
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
    @Operation(summary = "유저 정보 요청", description = "유저 정보 요청 api=> 유저이름,이메일,권한")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUser customUser) {
        log.info(":::: customUser ::::");
        log.info("customUser : " + customUser);

        User user = customUser.getUser();
        log.info("user : " + user);

        // 인증된 사용자 정보
        if (user != null)
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
    @Operation(summary = "사용자(소비자)회원가입 요청", description = "사용자 회원가입 api")
    public ResponseEntity<?> user(@RequestBody User user) throws Exception {
        log.info("[POST] - /users");
        int result = userService.insert(user);

        if(result > 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else if(result==-7){
            return new ResponseEntity<>("emailFAIL", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    //업체 회원가입
    @PostMapping("/store")
    @Operation(summary = "업체 회원가입 요청", description = "업체 회원가입 api")
    public ResponseEntity<?> join_store(@RequestBody User user) throws Exception {
        log.info("[POST] - /store");
        int result = userService.insert_store(user);

        if(result > 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else if(result==-7){
            return new ResponseEntity<>("emailFAIL", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    //관리자 회원가입
    @PostMapping("/admin")
    @Operation(summary = "관리자 회원가입 요청", description = "관리자 회원가입 api")
    public ResponseEntity<?> join_admin(@RequestBody User user) throws Exception {
        log.info("[POST] - /admin");
        int result = userService.insert_admin(user);

        if(result > 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else if(result==-7){
            return new ResponseEntity<>("emailFAIL", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    //라이더 회원가입
    @PostMapping("/rider")
    @Operation(summary = "라이더 회원가입 요청", description = "라이더 회원가입 api")
    public ResponseEntity<?> join_rider(@RequestBody User user) throws Exception {
        log.info("[POST] - /rider");
        int result = userService.insert_rider(user);

        if(result > 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else if(result==-7){
            return new ResponseEntity<>("emailFAIL", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    //유저 이메일 확인
    @PostMapping("/checkEmail")
    @Operation(summary = "유저 이메일 정보 요청", description = "유저 이메일 정보 요청 api")
    public String checkEmail(@RequestBody Map<String,String> emailInfo){
        String email=emailInfo.get("email");
        System.out.println(email);
        return userService.checkEmail(email);

    }





    /* 회원 정보 수정
        @param user
        @return
        @throws Exception
    */

    //user 권한을 가졌을때만 처리할 수 있도록 하기
//    @Secured("ROLE_USER") // USER 권한 설정
//    @PutMapping("")
//    public ResponseEntity<?> update(@RequestBody User user) throws Exception {
//        log.info("[PUT] - /users");
//        int result = userService.insert(user);

//        if(result > 0) {
//            log.info("회원 수정 성공! - SUCCESS");
//            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//        }
//        else {
//            log.info("회원 수정 실패! - FAIL");
//            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /* 회원 탈퇴
//        @param userId
//        @return
//        @throws Exception
//    */
//
//    //admin 권한을 가졌을때만 처리할 수 있도록 하기
//    @Secured("ROLE_USER") // ADMIN, USER 권한 설정 (기존에 설정된 값은 ADMIN)
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> destroy(@PathVariable("userId") String userId) throws Exception {
//        log.info("[DELETE] - /users/{userId}");
//
//        int result = userService.delete(userId);
//
//        if(result > 0) {
//            log.info("회원 삭제 성공! - SUCCESS");
//            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//        }
//        else {
//            log.info("회원 삭제 실패! - FAIL");
//            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
//        }
//    }
}