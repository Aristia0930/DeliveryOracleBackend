package org.example.backend.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.admin.dto.*;
import org.example.backend.service.StoreReportVo;
import org.example.backend.store.dto.ReportsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "관리자 페이지", description = "관리자 페이지 API")
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 상점 승인 정보 가져오기
    @Operation(summary = "업체승인목록조회", description = "등록요청한 업체목록 조회 api")
    @PostMapping("/approvals")
    public List<AdminApproveVo> postAllApprovals() {
        return adminService.postAllApprovals();
    }

    // GET 요청을 통해 승인 처리하기
    // GET 요청: axios를 사용하여 GET 요청을 보내고, URL에 쿼리 파라미터를 포함시킬 수 있습니다.
    // const response = await axios.get(`http://localhost:8080/admin/approve?owner_id=${id}`);
    // 이 경우, owner_id가 URL의 쿼리 스트링으로 전달되어 Spring Boot의 @RequestParam으로 받을 수 있습니다.


    @GetMapping("/approve")
    @Operation(summary = "업체승인하기", description = "등록요청한 업체 승인 api")
    @Parameters({
            @Parameter(name = "store_id", description = "업체 아이디 ", example = "1"),

    })

    public String setAdminApproval(@RequestParam("store_id") int store_id) {
        System.out.println("[AdminController] setAdminApproval() store_id: " + store_id);

        String nextPage = "redirect:http://localhost:3000/ManagerApprove";
        adminService.setAdminApproval(store_id);
        return nextPage;

    }

    //관리자 주문 내역 불러오기
    @GetMapping("/OrderReceipt")
    @Operation(summary = "모든 업체의 주문 내역", description = "주문 완료된 모든 업체의 주문 내역 api")
    public List<AdminOrderInformationVo> orderReceipt(){
        log.info("결제 내역 조회!");
        return adminService.orderReceipt();
    }

    //관리자 매출 내역 그래프로 불러오기
    @GetMapping("/ManagerRevenue")
    @Operation(summary = "매출 그래프", description = "매출 그래프를 보여주기 위한 정보를 요청하는 api =>AdminOrderInformationVo 객체에서 날짜 매출 값을 보여줌")
    @Parameters({
            @Parameter(name = "order_approval_status", description = "승인 상태 ", example = "4"),

    })
    public List<AdminOrderInformationVo> ManagerRevenue(@RequestParam("order_approval_status") int order_approval_status){
        log.info("현재 매출 내역 조회하기! " + ", order_approval_status (주문 승인 상태 값 조회) : " + order_approval_status);
        return adminService.ManagerRevenue(order_approval_status);
    }

    //업체쪽에서 유저 신고한 내용확인하기
    @GetMapping("/Reports")
    @Operation(summary = "업체가 신고한 유저 신고 내역조회", description = "업체가 신고한 유저 신고 내역조회 api")
    public List<ReportsUserVo> userReport(){
        return adminService.userReport();
    }

    //유저 신고 내용 상세 조회
    @GetMapping("ReportsDetail")
    @Operation(summary = "업체가 신고한 유저 신고 내역 상세 조회 ", description = "업체가 신고한 유저 신고 내역 상세 조회 api =>신고자와 신고내용 등 자세한 설명이 나타남")
    @Parameters({
            @Parameter(name = "id", description = "신고당한 유저 아이디", example = "4"),

    })
    public List<ReportsUserDetailVo> userDetail(@RequestParam("id") int authorId){
        return adminService.userDetail(authorId);
    }

    //유저 블락 먹이기
    @PostMapping("block")
    @Operation(summary = "유저 블락 처리 요청 ", description = "유저 블락 처리 요청")
    public ResponseEntity<?> block (@RequestBody Map<String, Integer> id){
        //아이디값을 받아와서 그 아이디 값하고 같은 이메일의 유저 권한을 변경한다.
        int rs=adminService.block(id.get("id"));
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
//
    }

    //유저쪽에서 업체 신고한 내용확인하기
    @GetMapping("/StoreReports")
    @Operation(summary = "유저가 업체 신고 목록 확인", description = "유저가 업체 신고 목록 확인")
    public List<ReportStoreVo> storeReport(){
        return adminService.storeReport();
    }

    //유저쪽에서 업체 신고한 내용 상세조회
    @GetMapping("StoreReportsDetail")
    @Operation(summary = "유저가 업체 신고 내역 상세조회", description = "유저가 업체 신고 내역 상세조회")
    public List<ReportStoreDetailVo> storeDetail(@RequestParam("storeId") int storeId){
        return adminService.storeDetail(storeId);
    }

    //업체 정지시키기 가시성 2 로 지정하기
    @PostMapping("Storeblock")
    @Operation(summary = "업체 정지 요청", description = "업체를 관리자가 정지(블락) 하는 요청")
    public ResponseEntity<?> Storeblockblock (@RequestBody Map<String, Integer> id){
        //아이디값을 받아와서 그 아이디 값하고 같은 이메일의 유저 권한을 변경한다.
        int rs=adminService.Storeblockblock(id.get("id"));
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
//
    }


    //메인화면정보를 받아온다
    @GetMapping("Today")
    @Operation(summary = "방문자 ,매출, 주문건수 죄회 요청", description = "방문자수 매출 주문건수 확인 요청")
    public TodayInfoVo today (){

        return adminService.today();




    }





}

