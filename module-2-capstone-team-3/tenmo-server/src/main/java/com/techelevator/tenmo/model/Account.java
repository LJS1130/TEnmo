package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private int id;
    private int userId;
    //TODO Research BigDecimal versus Double
    private BigDecimal balance;

    public Account() {
    }

    public Account(int id, int userId) {
        this.id = id;
        this.userId = userId;
        this.balance = new BigDecimal(1000.00);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private void initialBalance() {
        BigDecimal initialBalance = new BigDecimal(1000.00);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }

    public void setAccountId(int id) {
        this.id = id;
    }

}
