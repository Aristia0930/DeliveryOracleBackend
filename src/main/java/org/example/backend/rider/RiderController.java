package org.example.backend.rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rider")
public class RiderController {
    @Autowired
    private RiderService riderService;

    //배달전체 목록 불러오기
    @GetMapping("/order")
    public List<RiderVo> orderlist(@RequestParam("x")BigDecimal x,@RequestParam("y")BigDecimal y){

        return riderService.orderlist(x,y);



    }

}
