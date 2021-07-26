package com.payment.application.service;

import com.payment.application.model.Account;

import java.util.List;
import java.util.Set;

/**
 * This interface is responsible for account method declaration
 */
public interface IAccountService {

    public Account findById(int id);

    public Account getByEmail(String email);

    public Account save(Account account);

    Account update(Account account);

    public Account displayAccount(int id);

    public void delete(Account account);

    public List<Account> findAll();

    public void addContact(Account account1, Account account2);

    public void credit(Account account, Double count);

    public void decreaseCount(Account account, Double amount);

    public void increaseCount(Account account, Double credit);

    void clearDataBase();

    public Set<Account> findByAccount(Account debtorAccount);

    public String getUser();
}
