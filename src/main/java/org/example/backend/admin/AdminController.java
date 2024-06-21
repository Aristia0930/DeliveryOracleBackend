package org.example.backend.admin;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.store.StoreOrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 상점 승인 정보 가져오기
    @PostMapping("/approvals")
    public List<AdminApproveVo> postAllApprovals() {
        return adminService.postAllApprovals();
    }

    // GET 요청을 통해 승인 처리하기
    // GET 요청: axios를 사용하여 GET 요청을 보내고, URL에 쿼리 파라미터를 포함시킬 수 있습니다.
    // const response = await axios.get(`http://localhost:8080/admin/approve?owner_id=${id}`);
    // 이 경우, owner_id가 URL의 쿼리 스트링으로 전달되어 Spring Boot의 @RequestParam으로 받을 수 있습니다.

    @GetMapping("/approve")
    public String setAdminApproval(@RequestParam("owner_id") int owner_id) {
        System.out.println("[AdminController] setAdminApproval() owner_id: " + owner_id);

        String nextPage = "redirect:http://localhost:3000/ManagerApprove";
        adminService.setAdminApproval(owner_id);
        return nextPage;

    }

    //주문 내역 불러오기
    @GetMapping("/orderinfo")
    public List<AdminOrderInformationVo> orderinfo(@RequestParam("store_id") int store_id){
        log.info("결제 내역 조회!" + store_id);
        return adminService.orderinfo(store_id);
    }

    //매출 내역 불러오기
    @GetMapping("/orderSales_info")
    public List<AdminOrderInformationVo> orderSales_info(@RequestParam("store_id") int store_id){
        log.info("현재 매출 내역 조회하기! " + store_id );
        return adminService.orderSales_info(store_id);
    }
}
//    실패한코드 이유? (당연하지 RequestMethod.POST 로 요청했으므로 Body로 받아와야 한다.)
//    @RequestMapping(value = "/approve", method = RequestMethod.POST)
//    public void setAdminApproval(@RequestParam("owner_id") int owner_id){
//        System.out.println("[AdminController] setAdminApproval()");
//
//        adminService.setAdminApproval(owner_id);
//    }
