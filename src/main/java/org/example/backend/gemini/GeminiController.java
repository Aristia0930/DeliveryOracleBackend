package org.example.backend.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "구글ai", description = "구글ai API")
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/chat")
    @Operation(summary = "ai 메뉴 추천", description = "ai 메뉴 추천 으로 현재 내주문내역 날씨 기온 계절 분기 에 따른 음식 선호도에 따른 음식 추천 시스템")
    public ResponseEntity<?> gemini(@RequestParam("id") int id, @RequestParam("x") BigDecimal x , @RequestParam("y") BigDecimal y) throws JsonProcessingException {

        String orderList=geminiService.order(id);
        String menuList=geminiService.menuList();
        System.out.println(menuList);
        System.out.println(orderList);
        String weather=geminiService.weather();

        //테스트 하기위한 코드
//        for (int i=0 ; i<50;i++){
//            log.info(i+1+"번");
//            geminiService.getContents("오늘의 "+weather+" 이고 현재까지 내가 먹은 음식들은" + orderList + "주문할수 있는 음식들은 목록은" + menuList + "주문 가능한 음식 중 날씨와 기온과 내 주문내역에 맞게 추천할 메뉴 3개만 추천해줘야해 이때 대답결과는 1.메뉴이름:메뉴추천이유 이런 형식으로 나타내줘 그리고 단어 사이에 * 이 모양은 사용하지 말아죠 그리고 무조건 내가 가지고 있는 메뉴 목록안에서만 추천해줘야해", x, y);
//
//            try {
//                // 10초 대기
//                Thread.sleep(15000); // 10000밀리초 = 10초
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        try {
            if (orderList==null){
                System.out.println("주문없음");
                return ResponseEntity.ok().body(geminiService.getContents("오늘의 "+weather+" 음식 메뉴를 추천받고 싶어 주문할수 있는 음식들은 목록은" + menuList + "주문 가능한 음식 중 추천할 메뉴 3개만 추천해줘야해 다른 음식을 추천해주면 안되는거야 그리고 이때 대답결과는 1.메뉴이름:메뉴추천이유 이런 형식으로 나타내줘 그리고 단어 사이에 * 이 모양은 사용하지 말아죠 그리고 무조건 내가 가지고 있는 메뉴 목록안에서만 추천해줘야해", x, y));
            }
            else {
                return ResponseEntity.ok().body(geminiService.getContents("오늘의 "+weather+" 이고 현재까지 내가 주문한 음식들은" + orderList + "주문할수 있는 음식들은 목록은" + menuList + "주문 가능한 음식 중 내가 주문한 내역에과 연관있고, 날씨와 기온에 맞게 추천할 메뉴 3개를 추천해 주고 중복되는 음식은 추천하지마 그리고 이때 대답결과는 1.메뉴이름:메뉴추천이유 이런 형식으로 나타내줘 그리고 단어 사이에 * 이 모양은 사용하지 말아죠 그리고 무조건 내가 가지고 있는 메뉴 목록안에서만 추천해줘야해", x, y));
            }
            }
        catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chat2")
    @Operation(summary = "날씨 기온 api", description = "날씨 기온 api")
    public ResponseEntity<?> weather() throws JsonProcessingException {


        try {

            return ResponseEntity.ok().body(geminiService.weather());


        }
        catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    }

