package com.DeliveryOracle.backend.user.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//user 테이블과 매핑시킬 DTO
public class Users {

    private int no;
    private String userId;
    private String userPw;
    private String userPwCheck; //pw확인
    private String name;
    private String email;
    private Date regDate;
    private Date updDate;
    private int enabled; //활성화(휴면 회원) 여부

    // 권한 목록
    List<UserAuth> authList;

    //기본 생성자 생성
    public Users() {

    }

    //필요한 생성자
    public Users(Users user) {
        this.no = user.getNo();
        this.userId = user.getUserId();
        this.userPw = user.getUserPw();
        this.name = user.getName();
        this.email = user.getEmail();
        this.authList = user.getAuthList();
    }
}
