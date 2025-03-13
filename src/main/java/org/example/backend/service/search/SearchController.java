package org.example.backend.service.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.service.OrderListVo;
import org.example.backend.service.OrderVo;
import org.example.backend.store.dto.StoreInformationVo;
import org.example.backend.store.dto.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "조회 api", description = "조회 API")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //음식점 목록 조회(카테고리) 내위치 기반으로 조회하자
    @GetMapping("/CaList")
    @Operation(summary = "음식점 목록 조회 요청", description = "음식점 목록 조회 요청 api")
    public List<StoreRegistrationVo> storeList(@RequestParam("canum") String num, @RequestParam("x") BigDecimal x ,@RequestParam("y") BigDecimal y){
        return searchService.storeList(num,x,y);


    }

    //메뉴정보 불러오기(음식점 상세페이지에서) 상점 아이디 받아올꺼임
    @GetMapping("/menuList")
    @Operation(summary = "선택한 음식점 정보 요청", description = "음식점 정보 요청 api=> 메뉴 목록 ,음식점 이름,음식점 이미지 ")

    public List<StoreInformationVo> menuList(@RequestParam("id") int id){
        return searchService.menuList(id);
    }

    //주문하기
    //고객아이디,상점아이디/주문내역/총가격 이렇게 값이 넘어와야한다.
    @PostMapping("/order")
    @Operation(summary = "주문요청", description = "주문요청 api")

    public int order(@RequestBody OrderVo orderVo) {


        return searchService.order(orderVo);
    }

    //이메일탐색 음식점 상세 정보 페이지
    //웹소켓을 위해 음식점 주인의 이메일 을 탐색한다.
    @GetMapping("/email_shop")
    @Operation(summary = "음식점 주인의 이메일 정보 요청", description = "음식점 주인의 이메일 정보 요청 api")

    public String email(@RequestParam("id") int id){

            return  searchService.email(id);
    }
    //이메일을 검색하여 현재 그 유저가 로그인 중인지 확인하는 절차
    @GetMapping("/emailTrue")
    @Operation(summary = "음식정 주인이 로그인 확인 요청", description = "음식점 주인 로그인 확인 요청 api")

    public String emailTrue(@RequestParam("id") int id){
        System.out.println(searchService.emailTrue(id));
        return  searchService.emailTrue(id);
    }

    // 사용자 주문 정보 가져오기
    @GetMapping("/details")
    @Operation(summary = "사용자 주문 내역 요청", description = "사용자 주문 내역 요청 api")
    public ResponseEntity<List<OrderListVo>> getUserOrders(@RequestParam("userId") int userId) {
        System.out.println(userId);
        List<OrderListVo> orders = searchService.getUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //검색창에서 조회하기
    @GetMapping("/searchList")
    @Operation(summary = "검색창 음식점 검색", description = "검색창 음식점 검색 요청 api")
    public List<StoreRegistrationVo> storeList2( @RequestParam("x") BigDecimal x ,@RequestParam("y") BigDecimal y,@RequestParam("searchTerm") String word) {
        System.out.println("검색조회 실행됨");
        return searchService.storeList2(x, y,word);


    }

    //사용자 리뷰 목록 불러오기
    @GetMapping("review")
    @Operation(summary = "음식점에서 사용자 리뷰 목록 조회 요청", description = "음식점에서 사용자 리뷰 목록 조회 요청 api")
    public List<CommentsVo> review(@RequestParam("id") int id){
        return searchService.review(id);
    }


    }
