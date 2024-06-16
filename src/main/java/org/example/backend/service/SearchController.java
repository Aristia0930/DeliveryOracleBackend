package org.example.backend.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //음식점 목록 조회(카테고리) 내위치 기반으로 조회하자
    @GetMapping("/CaList")
    public List<StoreRegistrationVo> storeList(@RequestParam("canum") String num, @RequestParam("x") BigDecimal x ,@RequestParam("y") BigDecimal y){

        log.info("카테고리 조회");
        return searchService.storeList(num,x,y);


    }

    //메뉴정보 불러오기(음식점 상세페이지에서) 상점 아이디 받아올꺼임
    @GetMapping("/menuList")
    public List<StoreInformationVo> menuList(@RequestParam("") int id){
        log.info(":::: 음식점 상세페이지 정보 불러오기 ::::");
        return searchService.menuList(id);
    }

    //주문하기
    //고객아이디,상점아이디/주문내역/총가격 이렇게 값이 넘어와야한다.
    @PostMapping("/order")
    public int order(@RequestBody OrderVo orderVo) {
        log.info(":::: 주문하기 ::::");
        return searchService.order(orderVo);
    }




}
