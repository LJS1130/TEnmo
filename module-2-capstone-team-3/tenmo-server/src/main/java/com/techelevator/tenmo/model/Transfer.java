package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transfer {

    private BigDecimal transferAmount;
    private long transferId;
    private long sendingUserId;
    private long receivingUserId;
    private String status;
    //private LocalDateTime transferDateTime;


    public Transfer () {}

    public Transfer(BigDecimal transferAmount, long transferId, long sendingUserId, long receivingUserId, String status) {
        this.transferAmount = transferAmount;
        this.transferId = transferId;
        this.sendingUserId = sendingUserId;
        this.receivingUserId = receivingUserId;
        this.status = status;
        //this.transferDateTime = LocalDateTime.now();
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getSendingUserId() {
        return sendingUserId;
    }

    public void setSendingUserId(long sendingUserId) {
        this.sendingUserId = sendingUserId;
    }

    public long getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(long receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public LocalDateTime getTransferDateTime() {
//        return transferDateTime;
//    }

//    public void setTransferDateTime(LocalDateTime transferDateTime) {
//        this.transferDateTime = transferDateTime;
//    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferAmount=" + transferAmount +
                ", transferId=" + transferId +
                ", sendingUserId=" + sendingUserId +
                ", receivingUserId=" + receivingUserId +
                ", status='" + status + '\'' +
                '}';
    }
}
