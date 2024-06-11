package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
