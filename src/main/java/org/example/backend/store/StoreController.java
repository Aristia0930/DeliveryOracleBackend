package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    //상점등록
    @PostMapping("/join")
    public String storeJoin(        @RequestParam("name") String name,
                                    @RequestParam("address") String address,
                                    @RequestParam("text") String text,
                                    @RequestParam("img") MultipartFile img,
                                    @RequestParam("storeX") BigDecimal storeX,
                                    @RequestParam("storeY") BigDecimal storeY,
                                    @RequestParam("category") String category){




        //중복 금지 추가하기

        StoreRegistrationVo storeRegistrationVo = new StoreRegistrationVo();
        storeRegistrationVo.setStore_name(name);
        storeRegistrationVo.setStore_address(address);
        storeRegistrationVo.setStore_description(text);
        storeRegistrationVo.setStore_image("이미지");
        storeRegistrationVo.setStore_x(storeX);
        storeRegistrationVo.setStore_y(storeY);
        storeRegistrationVo.setStore_ca(category);
        //유저아이디 나중에는 받아와야한다.
        storeRegistrationVo.setOwner_id(1);

        storeService.storeInsert(storeRegistrationVo);


        return "gkdl";
    }



}
