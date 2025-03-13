package org.example.backend.rider;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface RiderMapper {
    // 배달 전체 목록 불러오기
    List<RiderVo> orderlist(@Param("x") BigDecimal x, @Param("y") BigDecimal y);

    // 콜 받은 거 라이더 DB에 등록
    int call(RiderVo riderVo);

    // 주문 상태 업데이트 (배달 시작)
    int order(@Param("orderId") int orderId);

    // 콜 수락한 목록 조회
    List<RiderVo> orderCall(@Param("id") int id);

    // 배달 완료 처리
    int finish(@Param("deliveryId") int deliveryId);

    // 주문 상태 업데이트 (배달 완료)
    int orderfinish(@Param("orderId") int orderId);

    // 라이더의 배달 완료된 목록 조회
    List<RiderVo> riderReceipt(@Param("riderId") int riderId);

    // 라이더의 수익 조회
    List<RiderVo> riderRevenue(@Param("riderId") int riderId);
}
