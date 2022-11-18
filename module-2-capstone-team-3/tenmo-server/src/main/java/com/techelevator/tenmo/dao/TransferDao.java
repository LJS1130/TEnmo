package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransferById (long transferId, int accountId);

    List<Transfer> getAllTransfers (int accountId);

    boolean createTransfer (Transfer transfer);

    void updateTransfer (Transfer updateTransfer);

}
