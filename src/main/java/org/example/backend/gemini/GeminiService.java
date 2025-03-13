package org.example.backend.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.search.SearchDao;
import org.example.backend.store.dto.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  GeminiMapper geminiDao;

    @Autowired
    private SearchDao searchDao;


    @Value("${GEMINI_URL}")
    private String apiUrl;

    @Value("${GEMINI_API}")
    private String geminiApiKey;

    public Map<String,List<StoreRegistrationVo>> getContents(String prompt, BigDecimal x, BigDecimal y) {

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        ChatRequest request = new ChatRequest(prompt);
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();


        String [] splitArray = message.split("\n");
        Map<String, List<StoreRegistrationVo>> menuList = new HashMap<>(); // 초기화

        for(int i=0; i<3; i++) {

            int index = splitArray[i].indexOf(":");
            String menuName = splitArray[i].substring(3, index);

            log.info(menuName);
            String menuNames = splitArray[i].substring(3);


            //테이블에 들어가서 그 메뉴 이름있는 가게 목록 보두 불러와야함.
            //이메뉴를 가지고 있는 모든 식당을 조회한다. 스토어 id 를 꺼내오기


            // Set을 사용하여 중복 제거
            List<StoreRegistrationVo> allStores = searchDao.storeList3(x, y, menuName);
            List<StoreRegistrationVo> uniqueStores = new ArrayList<>();
            List<String> storeNames = new ArrayList<>();

            for (StoreRegistrationVo store : allStores) {
                if (!storeNames.contains(store.getStore_name())) {
                    storeNames.add(store.getStore_name());
                    uniqueStores.add(store);
                }
            }

            if (!uniqueStores.isEmpty()) {
                menuList.put(menuNames, uniqueStores);

            }
        }

        return menuList;
    }



    public String order(int id) throws JsonProcessingException {
        List<String> orders= geminiDao.order(id);

        String names=" ";
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> menuNames = new ArrayList<>();
        for (String orderDetail : orders) {
            //제인스 데이터로 받아온 거 변환.
            List<Map<String, Object>> orderList = objectMapper.readValue(orderDetail, new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> menuItem : orderList) {
                String menuName = (String) menuItem.get("menuName");
                menuNames.add(menuName);
                names+=menuName+" ,";
            }
        }
        return names;

    }

    public String menuList() throws JsonProcessingException {
        List<String> menus= geminiDao.menuList();

        String menuName="";
        ObjectMapper objectMapper = new ObjectMapper();

        for (String menu : menus) {
            //제인스 데이터로 받아온 거 변환.

            menuName+=" "+menu+",";
        }

        return menuName;

    }

    @Value("${WEATHER_API}")
    private String apiKey;

    //날씨
    public String weather(){
        //현재 날짜
        LocalDate currentDate = LocalDate.now();
        //몇월인지 확인
         int monthValue=currentDate.getMonthValue();
         String seasonMenu;

        switch (monthValue) {
            case 12:
            case 1:
            case 2:
                seasonMenu = "계절은 겨울로 쌀국수,해물탕,초밥,회,씨푸드 이런 종류의 음식을 선호해";
                break;
            case 3:
            case 4:
            case 5:
                seasonMenu = "계절은 봄으로 한정식,철판구이,볶음밥,낙지,오리훈제,파전,불고기,갈비살,차돌박이,딤섬,비빔밥,돌솥밥,쌈밥,돼지갈비 이런 종류의 음식을 선호해";
                break;
            case 6:
            case 7:
            case 8:
                seasonMenu = "계절은 여름으로 햄버거,삼계탕,백숙,찜닭,냉면,아이스크림,카레,장어구이,전복,치킨,오리훈제 이런 종류의 음식을 선호해";
                break;
            case 9:
            case 10:
            case 11:
                seasonMenu = "계절은 가을으로 맥주,딤섬,설렁탕,곰탕,도가니탕,순두부 이런 종류의 음식을 선호해";
                break;
            default:
                seasonMenu = "알 수 없음";
                break;
        }



        String url = "https://api.openweathermap.org/data/2.5/weather?lat=37.472737&lon=126.885673&appid=" + apiKey + "&units=metric";
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        //날씨
        System.out.println(response.getWeather().get(0).getDescription());
        //기온
        System.out.println(response.getMain().getTemp());

        String weather="날씨는 "+response.getWeather().get(0).getDescription()+"이고 현재기온은 "+response.getMain().getTemp()+"그리고 현재 "+seasonMenu;
        return weather;

    }
}