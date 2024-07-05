package org.example.backend.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.user.dto.VisitVo;
import org.example.backend.user.mapper.VisitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@Slf4j
@Service
public class VisitService {

    @Autowired
    private VisitDao visitDao;
    
    //방문자 기록하기위해
    @Transactional
    public void insertVister(String email){
        log.info("방문자 체크 확인을 위한 이메일 : " + email);
        LocalDate today=LocalDate.now();
        VisitVo visitVo = new VisitVo();
        visitVo.setVisitDate(today);
        visitVo.setEmail(email);
        //오늘자로 방문했는지 확인하기
        int rs=visitDao.check(visitVo);
        System.out.println(rs);
        if (rs==0){
            visitDao.insert(visitVo);
        }

        //아니면 넣기




    }

}
