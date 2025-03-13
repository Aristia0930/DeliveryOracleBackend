package org.example.backend.service.storeReport;

import org.apache.ibatis.annotations.Mapper;
import org.example.backend.service.StoreReportVo;

@Mapper
public interface ReportMapper {

    // 신고했는지 아닌지 확인하는 절차
    Integer reportCheck(StoreReportVo storeReportVo);

    // 신고 등록
    int report(StoreReportVo storeReportVo);
}