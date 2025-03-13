package org.example.backend.comments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* 댓글 목록 : [GET] /comments/{comments_id}
   댓글 등록 : [POST] /reply
   댓글 수정 : [PUT]  /reply
   댓글 삭제 : [DELETE] /reply
 */

@Slf4j
@RestController
@Tag(name = "댓글 컨트롤러", description = "댓글 API")
@RequestMapping("/comments") //엔드포인트 생성
public class CommentsController {

    //서비스를 먼저 등록(자동주입) 한다
    @Autowired
    private CommentsService commentsService;

    /** 댓글 목록 조회
     @return
     @throws Exception
     */

    //댓글 목록 조회
    @GetMapping("/list")
    @Operation(summary = "음식 주문 페이지에서 댓글 조회", description = "음식 주문 페이지에서 업체 작성한 유저 댓글 조회")
    public List<CommentsVo> list(@RequestParam("store_id") int store_id) throws Exception {
        return commentsService.list(store_id);
    }

     /* 댓글 목록 조회


        @param commentsVo
        @return
        @throws Exception
     */

    //댓글(단건) 조회
    @GetMapping("/{comment_id}")
    @Operation(summary = "특정댓글 조회", description = "댓글 번호를 이용하여 특정 댓글 조회")
    public ResponseEntity<CommentsVo> selectById(@PathVariable("comment_id") int comment_id) throws Exception {
        CommentsVo comment = commentsService.selectById(comment_id);
        if(comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //기존 댓글 등록
    @PostMapping("")
    @Operation(summary = "댓글 등록", description = "댓글 등록 api")
    public ResponseEntity<String> insert(@RequestBody CommentsVo commentsVo ,@RequestParam("orderid") int id) throws Exception {
//        System.out.println(commentsVo.getAuthor_name());
//        System.out.println(commentsVo.getAuthor_id());
        // 데이터 요청
        int result = commentsService.insert(commentsVo,id);
        if(result > 0) {
            //데이터 처리 성공
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED); //CREATED : 201번 상태코드가 들어있음
        }
        return new ResponseEntity<>("FAIL", HttpStatus.OK); //OK : 200번 상태코드가 들어있음
    }

    //댓글 수정
    @PutMapping("/Edit")
    @Operation(summary = "댓글 수정", description = "댓글 수정 api")
    public ResponseEntity<String> update(@RequestBody CommentsVo commentsVo) {

        try {
            int result = commentsService.update(commentsVo);
            log.info("댓글 업데이트 result 값 확인 : " + result);
            if(result > 0) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info("댓글 업데이트 에러 : ", e);
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    @DeleteMapping("/ucv/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제 api")
    public ResponseEntity<String> updateCommentVisibility(@PathVariable("commentId") int commentId) {
        try {
            int result = commentsService.updateCommentVisibility(commentId);
            log.info("댓글 삭제 (가시성 상태 변경) result 값 확인 : " + result);
            if (result > 0) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            log.info("댓글 delete(가시성 update) 에러 : ", e);
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //대댓글 등록
    @PostMapping("/reply")
    @Operation(summary = "업체가 답글 등록", description = "유저가 작성한 댓글에 답글 작성 api")
    public ResponseEntity<String> insertReply(@RequestBody CommentsVo commentsVo) throws Exception {
        //대댓글 등록은 depth 값을 2로 설정해두었기 때문에 2로 지정한다.
        commentsVo.setDepth(2);

        int result = commentsService.insertReply(commentsVo);

        if(result > 0) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }

    //대댓글 수정
    @PutMapping("/reply")
    @Operation(summary = "답글수정", description = "답글 수정 api")
    public ResponseEntity<String> updateReply(@RequestBody CommentsVo commentsVo) {

        try {
            int result = commentsService.updateReply(commentsVo);
            log.info("대댓글 업데이트 result 값 확인 : " + result);
            if(result > 0) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info("대댓글 업데이트 에러 : ", e);
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    @DeleteMapping("/replyUrv/{comment_id}")
    @Operation(summary = "답글 삭제", description = "답글 삭제 api")
    public ResponseEntity<String> updateReplyVisibility(@PathVariable("comment_id") int commentId) {
        try {
            int result = commentsService.updateReplyVisibility(commentId);
            log.info("대댓글 삭제 (가시성 상태 변경) result 값 확인 : " + result);
            if (result > 0) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            log.info("대댓글 delete(가시성 update) 에러 : ", e);
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
