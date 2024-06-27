package org.example.backend.admin;

import org.example.backend.admin.dto.AdminApproveVo;
import org.example.backend.admin.dto.AdminOrderInformationVo;
import org.example.backend.admin.dto.ReportsUserDetailVo;
import org.example.backend.admin.dto.ReportsUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public List<AdminApproveVo> postAllApprovals() {
        return adminDao.postAllApprovals();
    }

    public void setAdminApproval(int owner_id) {
        System.out.println("[AdminMemberService] setAdminApproval()");

        int result = adminDao.adminApprovalupdate(owner_id);
    }

    //관리자 주문 내역 조회
    public List<AdminOrderInformationVo> orderReceipt(){
        return adminDao.orderReceipt();
    }

    //매출 내역 조회
    public List<AdminOrderInformationVo> ManagerRevenue(int order_approval_status){
        return adminDao.ManagerRevenue(order_approval_status);
    }

    //유저 신고내역 조회
    public List<ReportsUserVo> userReport(){
        return adminDao.userReport();
    }

    public List<ReportsUserDetailVo> userDetail(int authorId){
        return adminDao.userDetail(authorId);
    }

    //유저 블락하가기
    public int block(int id){
        //받은 아이디로 이메일 검색 검색후 유저권한 변경해야한다.
        //1 이면 블락 성공함
        return adminDao.block(id);

    }

}


