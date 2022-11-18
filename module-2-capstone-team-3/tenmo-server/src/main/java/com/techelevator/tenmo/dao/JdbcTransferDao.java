package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;
    public JdbcTransferDao(DataSource dataSource, AccountDao accountDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountDao = accountDao;
    }


    @Override
    public Transfer getTransferById(long transferId, int accountId) {
        String sql = "SELECT transfer_id, sending_user_id, receiving_user_id, transfer_amount, status FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = new Transfer();
        if (transfer.getTransferId() != transferId || accountDao.getAccount(accountId).getId() != accountId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid transfer ID or account ID");
        } else if (results.next()) {
             transfer = mapRowToTransfer(results);
        } return transfer;
        }

    @Override
    public List<Transfer> getAllTransfers(int accountId) {
        String sql = "SELECT transfer_id, sending_user_id, receiving_user_id, transfer_amount, status FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        List<Transfer> transfers = new ArrayList<>();
        if (accountDao.getAccount(accountId).getId() != accountId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid account ID");
        }
        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public boolean createTransfer(Transfer transfer) {
        try {
            if (transfer.getReceivingUserId() != transfer.getSendingUserId() && (transfer.getTransferAmount().doubleValue() > 0.0)) {
                String sql = "INSERT INTO transfer (sending_user_id, receiving_user_id, transfer_amount, status) VALUES (?, ?, ?, ?) RETURNING transfer_id;";
                int newId = jdbcTemplate.queryForObject(sql, int.class, transfer.getSendingUserId(), transfer.getReceivingUserId(), transfer.getTransferAmount(), transfer.getStatus());
                transfer.setTransferId(newId);
            accountDao.updateBalance(transfer);

            }
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    public void updateTransfer(Transfer updateTransfer) {
        String sql = "UPDATE transfer SET status = ? WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, updateTransfer.getStatus(), updateTransfer.getTransferId());
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setReceivingUserId(rowSet.getInt("receiving_user_id"));
        transfer.setSendingUserId(rowSet.getInt("sending_user_id"));
        transfer.setTransferAmount(rowSet.getBigDecimal("transfer_amount"));
        transfer.setStatus(rowSet.getString("status"));

        return transfer;
    }
}
