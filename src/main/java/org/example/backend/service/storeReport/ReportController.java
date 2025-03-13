package org.example.backend.service.storeReport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.service.StoreReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userReport")
@Tag(name = "사용자 신고 api", description = "신고 API")
//업체신고 컨트롤러
public class ReportController {
    //신고받고 난후
    //관리자 쪽에서 업체 내리기  => 상태 2로 번경
    //관리자 쪽에서 업체 다시 1로 수정하는 페이지도 만들기

    @Autowired
    private ReportService reportService;


    //업체 신고(신고버튼 눌럿을때 신고했는지 확인하고신고한했다면 신고 하게 하기)
    @PostMapping("/store")
    @Operation(summary = "사용자가 업체 신고 요청", description = "사용자가 업체 신고 요청 api")
    public ResponseEntity<?> report(@RequestBody StoreReportVo storeReportVo){

        int rs= reportService.report(storeReportVo);
        System.out.println("결과다");
        System.out.println(rs);
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else if(rs==10){
            return new ResponseEntity<>("checkFail", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/check")
    @Operation(summary = "신고 기록 확인 요청", description = "신고 기록 확인 요청 api")
    public int check(@RequestBody StoreReportVo storeReportVo){
        return reportService.check(storeReportVo);
    }
}
