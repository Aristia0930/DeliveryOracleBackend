package org.example.backend.service.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.OrderException;
import org.example.backend.service.PayAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
@RestController
@RequestMapping("/account")
@Tag(name = "계좌정보 api", description = "계좌정보 API")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //계좌잔액확인
    @GetMapping("/amount")
    @Operation(summary = "계좌 잔액 확인 api", description = "계좌 잔액 확인 api")
    public int amount(@RequestParam("id") int id){
        return accountService.amount(id);
    }


    //계좌 금액추가 하고 현재 금액 반환하기
    @PostMapping("/deposit")
    @Operation(summary = "계좌에 포인트 입금요청", description = "계좌에 포인트 입금 api")
    public int deposit(@RequestBody Map<String, Integer> info){
        int id=info.get("id");
        int price=info.get("price");
        System.out.println(price+"가격");
        return accountService.deposit(id,price);

    }

    //계좌 결제

    //집가서 order search의 합쳐야한다 트랜잭션을 걸어주기 위해서
//    @PutMapping("/pay")
//    public ResponseEntity<?> pay(@RequestBody Map<String, Integer> info){
//        int id=info.get("id");
//        System.out.println(id);
//        int price=info.get("price");
//        System.out.println(price);
//
//        try {
//            int result = accountService.pay(id, price);
//            if (result == 1) {
//                return ResponseEntity.ok("SUCCESS");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL");
//            }
//        } catch (IllegalStateException e) {
//            // 잔액 부족 예외 처리
//            return ResponseEntity.ok("Insufficient balance");
//        }
//    }

    @PostMapping("/pay")
    @Operation(summary = "결제 요청", description = "주문시 결제 요청 api")
    public ResponseEntity<?> pay(@RequestBody PayAccountVo payAccountVo){
        int id=payAccountVo.getId();
        System.out.println(id);
        int price=payAccountVo.getPrice();
        System.out.println(price);

        try {
            int result = accountService.pay(payAccountVo);
            if (result == 1) {
                return ResponseEntity.ok("SUCCESS");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL");
            }
        } catch (IllegalStateException e) {
            // 잔액 부족 예외 처리
            return ResponseEntity.ok("Insufficient balance");
        }
        catch (OrderException e){
            return  ResponseEntity.ok("Order failed");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL");
        }
    }


    //등급 확인
    @PostMapping("/rank")
    @Operation(summary = "회원등급 반환요청", description = "회원등급 반환요청 api")
    public String rank(@RequestBody PayAccountVo payAccountVo){
        String name=accountService.rank(payAccountVo);
        if (name!=""){
            return name;
        }
        else{
            return "오류";
        }


    }


}
