package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    //상점등록 서비스
    public  int storeInsert(StoreRegistrationVo storeRegistrationVo){
        return storeDao.storeInsert(storeRegistrationVo);
    }

    //승인 확인 서비스
    public int count(int id){
        return storeDao.approveR(id);
    }

    //메뉴 등록 서비스
    public int menuRs(StoreInformationVo storeInformationVo){
        return storeDao.menuRs(storeInformationVo);
    }

    //메뉴 목록 조회 서비스
    public List<StoreInformationVo> menuList(int id){
        return storeDao.menuList(id);
    }


}
