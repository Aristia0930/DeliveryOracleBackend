package org.example.backend.service.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    //계좌잔액확인
    public int amount(int id){
        return accountDao.amount(id);
    }

    //계좌 금액추가 하고 현재 금액 반환하기
    @Transactional
    public int deposit(int id,int price){
        //유저 계좌 번호 확인
        int accountNum=accountDao.accountId(id);
        log.info(":::: 내계좌 넘버 ::::"+accountNum);
        //현재 잔액
        if(price<=0){
            return -1;
        }else{
        accountDao.deposit(accountNum,price);
        return accountDao.amount(id);}


    }

    //결제하기
    @Transactional
    public int pay(int id,int price){
        //유저 계좌 번호 확인
        int accountNum=accountDao.accountId(id);
        log.info(":::: 내계좌 넘버 ::::"+accountNum);
        //현재 잔액
        int amount=accountDao.amount(id);
        if (amount + price<0){
            throw new IllegalStateException("Insufficient balance");


        }else {
            return accountDao.withdraw(accountNum, price);
        }


    }


}
