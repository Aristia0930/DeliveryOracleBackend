package org.example.backend.comments.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

//댓글
@Getter
@Setter
public class CommentsVo {
    private int comment_id; //댓글 번호(id), 자동증가하는 키
    private int store_id; //업체 id
    private int author_id; //작성자(id), user_id 참조
    private String author_name; //작성자명
    private String content; //댓글 내용
    private int rating; //평점(1 ~ 5 사이의 값)
    private int visibility_status; //가시성 상태, 0 or 1
    private Timestamp creation_date; //작성 날짜, (기본값 현재 시간)
    private Timestamp modification_date; //수정 날짜, (기본값 현재 시간, 수정시 자동갱신)
    private int reply_id; //대댓글 id
    private int depth; //댓글의 경우 1로 표현, 대댓글의 경우 2로 표현
}
