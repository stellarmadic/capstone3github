package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")

public class AccountController {

    private JdbcAccountDao accountDao;

    public AccountController(JdbcAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Account getBalance() {
        int userId = 1001;
        Account account = null;
        account = accountDao.getAccountById(userId);
        return account;
}

    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        System.out.println("Account controller");
        BigDecimal balance = accountDao.getBalance(id);
        return balance;
    }



}
