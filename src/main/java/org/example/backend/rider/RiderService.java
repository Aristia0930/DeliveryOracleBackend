package org.example.backend.rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiderService {
    @Autowired
    private RiderDao riderDao;


    //배달전체 목록 불러오기
    public List<RiderVo> orderlist(BigDecimal x, BigDecimal y) {
        int EARTH_RADIUS_KM = 6371;
        double myx = x.doubleValue();
        double myy = y.doubleValue();
        List<RiderVo> riderVos = new ArrayList<>();

        riderVos = riderDao.orderlist();
        for (RiderVo i : riderVos) {
            double userx = i.getUser_x().doubleValue();
            double usery = i.getUser_y().doubleValue();
            double storex = i.getStore_x().doubleValue();
            double storey = i.getStore_y().doubleValue();

            // 주문자와 상점 사이의 거리 계산
            double dLat = Math.toRadians(usery - storey);
            double dLon = Math.toRadians(userx - storex);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(storey)) * Math.cos(Math.toRadians(usery)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            i.setDistanceToUser(Math.round((EARTH_RADIUS_KM * c)* 1000.0)/1000.0);


            // 상점과 라이더 사이의 거리 계산
            double dLat2 = Math.toRadians(storey - myy);
            double dLon2 = Math.toRadians(storex - myx);
            double a2 = Math.sin(dLat2 / 2) * Math.sin(dLat2 / 2) +
                    Math.cos(Math.toRadians(myy)) * Math.cos(Math.toRadians(storey)) *
                            Math.sin(dLon2 / 2) * Math.sin(dLon2 / 2);
            double c2 = 2 * Math.atan2(Math.sqrt(a2), Math.sqrt(1 - a2));
            i.setDistanceToStore(Math.round((EARTH_RADIUS_KM * c2)* 1000.0)/1000.0);
        }
        return riderVos;
    }
}
