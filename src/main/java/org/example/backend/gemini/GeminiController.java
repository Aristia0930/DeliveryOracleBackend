package org.example.backend.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/chat")
    public ResponseEntity<?> gemini(@RequestParam("id") int id, @RequestParam("x") BigDecimal x , @RequestParam("y") BigDecimal y) throws JsonProcessingException {

        String orderList=geminiService.order(id);
        String menuList=geminiService.menuList();
        System.out.println(menuList);
        System.out.println(orderList);
        String weather=geminiService.weather();


        try {
            if (orderList==null){
                System.out.println("주문없음");
                return ResponseEntity.ok().body(geminiService.getContents("오늘의 "+weather+" 음식 메뉴를 추천받고 싶어 주문할수 있는 음식들은 목록은" + menuList + "주문 가능한 음식 중 추천할 메뉴 3개만 추천해줘야해 다른 음식을 추천해주면 안되는거야 그리고 이때 대답결과는 1.메뉴이름:메뉴추천이유 이런 형식으로 나타내줘 그리고 단어 사이에 * 이 모양은 사용하지 말아죠 그리고 무조건 내가 가지고 있는 메뉴 목록안에서만 추천해줘야해", x, y));
            }
            else {
                return ResponseEntity.ok().body(geminiService.getContents("오늘의 "+weather+" 이고 현재까지 내가 먹은 음식들은" + orderList + "주문할수 있는 음식들은 목록은" + menuList + "주문 가능한 음식 중 날씨와 기온에 맞게 추천할 메뉴 3개만 추천해줘야해 그리고 추천해준는 목록중 하나 이상은 내가 주문 목록을 보고 내 취향에 맞게 추천해줘야해 다른 음식을 추천해주면 안되는거야 그리고 이때 대답결과는 1.메뉴이름:메뉴추천이유 이런 형식으로 나타내줘 그리고 단어 사이에 * 이 모양은 사용하지 말아죠 그리고 무조건 내가 가지고 있는 메뉴 목록안에서만 추천해줘야해", x, y));
            }
            }
        catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chat2")
    public ResponseEntity<?> weather() throws JsonProcessingException {


        try {

            return ResponseEntity.ok().body(geminiService.weather());


        }
        catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    }

