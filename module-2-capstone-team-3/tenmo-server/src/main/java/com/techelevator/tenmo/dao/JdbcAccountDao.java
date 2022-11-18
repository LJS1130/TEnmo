package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BigDecimal getBalance(int id) {
        Account account = new Account();

            String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
            SqlRowSet accountBalance = jdbcTemplate.queryForRowSet(sql, id);
        if (account.getId() != id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid account ID.");
        } else if (accountBalance.next()) {
                account = mapRowToAccount(accountBalance);
            }
            return account.getBalance();
        }



        @Override
        public void updateBalance (Transfer transfer){
            String sqlR = "UPDATE account SET balance = balance + (SELECT transfer_amount FROM transfer WHERE transfer_id = ?) WHERE account.user_id = (SELECT receiving_user_id FROM transfer WHERE transfer_id = ?);";
            jdbcTemplate.update(sqlR, transfer.getTransferId(), transfer.getTransferId());
            String sqlS = "UPDATE account SET balance = balance - (SELECT transfer_amount FROM transfer WHERE transfer_id = ?) WHERE account.user_id = (SELECT sending_user_id FROM transfer WHERE transfer_id = ?);";
            jdbcTemplate.update(sqlS, transfer.getTransferId(), transfer.getTransferId());
        }


    @Override
    public Account getAccount(int accountId) {
        Account account = new Account();
        String sql = "SELECT account_id FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (account.getId() != accountId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid account ID.");
        } else if (results.next()) {
            account = mapRowToAccount(results);
        } return account;
    }

        public Account createAccount (Account account){
            //TODO insert try-catch block
            String sql = "INSERT INTO account (user_id, balance) " +
                    " VALUES (?, ?) RETURNING account_id";
            int newId = jdbcTemplate.queryForObject(sql, int.class, account.getUserId(), account.getBalance());

            return getAccount(newId);
        }


        private Account mapRowToAccount (SqlRowSet rowSet){
            Account account = new Account();
            account.setAccountId(rowSet.getInt("account_id"));
            account.setBalance(rowSet.getBigDecimal("balance"));
            account.setUserId(rowSet.getInt("user_id"));

            return account;
        }


    }
