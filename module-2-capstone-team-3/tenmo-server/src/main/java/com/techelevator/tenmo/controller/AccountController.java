package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/tenmo")
@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public AccountController(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/account/{accountId}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int accountId) {
        BigDecimal balance = new BigDecimal(0);
        balance = accountDao.getBalance(accountId);
            return balance;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int accountId) {

        return accountDao.getAccount(accountId);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/{accountId}/transfer", method = RequestMethod.POST)
    public boolean createTransfer(@Valid @RequestBody Transfer transfer) {
        try {
            transferDao.createTransfer(transfer);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage() + "User ID " + transfer.getSendingUserId() + " is not a valid receipient or " + transfer.getTransferAmount() + " is greater than available funds.");
        }
        return true;
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/account/{accountId}/transfer", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable int accountId) {
        return transferDao.getAllTransfers(accountId);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/account/{accountId}/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int accountId, @PathVariable int transferId) {
        return transferDao.getTransferById(transferId, accountId);
    }

//    @PreAuthorize("permitAll")
//    @ResponseStatus(HttpStatus)
//

}
