package org.example.backend.admin;

import org.example.backend.store.StoreOrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //결제 내역 조회
    public List<AdminOrderInformationVo> orderinfo(int store_id){
        return adminDao.orderinfo(store_id);
    }

    //현재 매출 내역 조회
    public List<AdminOrderInformationVo> orderSales_info(int store_id, int order_approval_status){
        return adminDao.orderSales_info(store_id, order_approval_status);
    }
}
