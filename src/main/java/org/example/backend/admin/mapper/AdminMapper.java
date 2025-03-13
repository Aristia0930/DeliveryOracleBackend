package org.example.backend.admin.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.backend.admin.dto.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminApproveVo> getAllApprovals();

    int updateAdminApproval(@Param("storeId") int storeId);

    List<AdminOrderInformationVo> getOrderReceipts();

    List<AdminOrderInformationVo> getManagerRevenue(@Param("orderApprovalStatus") int orderApprovalStatus);

    List<ReportsUserVo> getUserReports();

    List<ReportsUserDetailVo> getUserDetails(@Param("authorId") int authorId);

    int blockUser(@Param("userId") int userId);

    List<ReportStoreVo> getStoreReports();

    List<ReportStoreDetailVo> getStoreDetails(@Param("storeId") int storeId);

    int blockStore(@Param("storeId") int storeId);

    TodayInfoVo getTodayInfo();
}
