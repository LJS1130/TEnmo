package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {


     BigDecimal getBalance(int userId);

     public void updateBalance(Transfer transfer);

     Account createAccount(Account account);

     Account getAccount(int accountId);





}
