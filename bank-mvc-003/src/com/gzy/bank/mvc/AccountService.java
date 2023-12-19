package com.gzy.bank.mvc;

import com.gzy.bank.bean.Account;
import com.gzy.bank.exceptions.AppException;
import com.gzy.bank.exceptions.MoneyNotEnoughException;

/**
 * 专注业务
 */
public class AccountService {


    private AccountDao accountDao = new AccountDao();

    public void transfer(String fromActno,String toActno,double money) throws MoneyNotEnoughException, AppException {
        //查询余额
        Account fromAccount = accountDao.selectByActno(fromActno);
        if(fromAccount.getBalance()<money){
            throw new MoneyNotEnoughException("对不起，余额不足");
        }

        //余额充足
        Account toAccount = accountDao.selectByActno(toActno);
        fromAccount.setBalance(fromAccount.getBalance()-money);
        toAccount.setBalance(toAccount.getBalance()+money);

        int count = accountDao.update(fromAccount);
        count += accountDao.update(toAccount);

        if(count!=2){
            throw new AppException("账户转账异常");
        }
    }
}
