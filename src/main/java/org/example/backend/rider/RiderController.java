package org.example.backend.rider;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Tag(name = "라이더 페이지", description = "라이더 페이지")
@RequestMapping("/rider")
public class RiderController {
    @Autowired
    private RiderService riderService;

    //배달전체 목록 불러오기
    @GetMapping("/order")
    @Operation(summary = "배달 목록 조회", description = "배달 가능한 배달 목록 조회 api")
    public List<RiderVo> orderlist(@RequestParam("x")BigDecimal x,@RequestParam("y")BigDecimal y){

        return riderService.orderlist(x,y);


    }
    //배달 콜 받기
    @PostMapping("/call")
    @Operation(summary = "배달 수락 요청", description = "배달 수락 요청 api")
    public int call(@RequestBody RiderVo riderVo){


        return riderService.call(riderVo);
    }

    //콜 수락 목록 조회 하기
    @GetMapping("/orderCall")
    @Operation(summary = "배달수락 요청", description = "배달 수락 요청 api")
    public List<RiderVo> orderCall(@RequestParam("id") int id){

        return riderService.orderCall(id);


    }

    //배달 완료 하기
    @PostMapping("/order/finish")
    @Operation(summary = "배달 완료 요청", description = "배달 완료 요청 api")
    public int finish(@RequestBody RiderVo riderVo){

        return riderService.finish(riderVo);
    }

    //라이더 완료 내역 출력
    @GetMapping("/riderReceipt")
    @Operation(summary = "배달 완료 내역 조회", description = "배달 완료 내역 조회 api")
    public List<RiderVo> Receipt(@RequestParam("riderId") int riderId){
        return riderService.Receipt(riderId);

    }

    //라이더 매출 그래프
    @GetMapping("/riderRevenue")
    @Operation(summary = "배달 완료 내역 매출 요청", description = "배달 완료 내역 매출 요청 api")
    public List<RiderVo> Revenue(@RequestParam("riderId") int riderId){
//        log.info("현재 배달 내역 조회하기! " + ", order_approval_status (주문 승인 상태 값 조회) : " + deliveryStatus);
        return riderService.Revenue(riderId);
    }

}