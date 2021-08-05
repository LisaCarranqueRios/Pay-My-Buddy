package com.payment.application.service;

import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;

import java.util.List;

public interface IBankAccountService {

    public BankAccount getByIban(String iban);

    public BankAccount save(BankAccount bankAccount);

    public List<BankAccount> findAll();

    public List<BankAccount> findByUser(Account user);

    public void decreaseCount(BankAccount bankAccount, Double amount);

    public void increaseCount(BankAccount bankAccount, Double credit);

    public String getUser();
}
