package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.io.File;
import java.util.UUID;
import java.io.IOException;
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    private static final String URL="C:\\Users\\KOSTA\\Desktop\\finalfr\\src\\imgs\\";

    //상점등록
    @PostMapping("/join")
    public String storeJoin(        @RequestParam("name") String name,
                                    @RequestParam("address") String address,
                                    @RequestParam("text") String text,
                                    @RequestParam("img") MultipartFile img,
                                    @RequestParam("storeX") BigDecimal storeX,
                                    @RequestParam("storeY") BigDecimal storeY,
                                    @RequestParam("category") String category) throws IOException {

        //이미지 저장하기
        String saveName=null;
        if (img != null && !img.isEmpty()) {
            File uploadDir= new File(URL);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
                System.out.println("디렉토리 파일 생성");
            }

            //원본파일이름
            String originalName= img.getOriginalFilename();
            //파일 이름 UUID를 사용 하여 재정의
            UUID uuid = UUID.randomUUID();
            saveName=uuid.toString()+"_"+originalName;

            //파일생성
            File saveFile=new File(URL+saveName);
            img.transferTo(saveFile);
        }
        StoreRegistrationVo storeRegistrationVo = new StoreRegistrationVo();
        storeRegistrationVo.setStore_name(name);
        storeRegistrationVo.setStore_address(address);
        storeRegistrationVo.setStore_description(text);
        storeRegistrationVo.setStore_image(saveName);
        storeRegistrationVo.setStore_x(storeX);
        storeRegistrationVo.setStore_y(storeY);
        storeRegistrationVo.setStore_ca(category);
        //유저아이디 나중에는 받아와야한다.
        storeRegistrationVo.setOwner_id(1);

        System.out.println(address);

        storeService.storeInsert(storeRegistrationVo);




        return "등록";
    }

    //등록 승인 되었는지 확인 요청
    @GetMapping("/menuRs")
    public String approvR(@RequestParam("id") int id){

        int rs=storeService.count(id);
        if(rs>0){
            return "true";
        }
        else {
            return "false";
        }



    }




}
