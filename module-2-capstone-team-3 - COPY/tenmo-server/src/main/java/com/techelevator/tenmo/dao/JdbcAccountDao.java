package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao  {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   @Override
    public BigDecimal getBalance(int userId) {
        BigDecimal balance = BigDecimal.valueOf(0.00);
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                balance = mapRowToAccount(results).getBalance();
            }
        }
        catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to server or database", e);
            }
                return balance;
    }

    @Override
    public Account getAccountById(int userId){
        Account account = null;
        String sql = "SELECT account_id,user_id,balance FROM account WHERE user_id = ?; ";
        try{
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
           if (results.next()) {
               account = mapRowToAccount(results);
               System.out.println("Located record in JDBCAccountDao: "+ account);
           }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;

    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
