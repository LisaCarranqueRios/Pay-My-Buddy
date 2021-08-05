package com.payment.application.service;

import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.model.Transaction;

import java.util.List;

/**
 * This interface is responsible for transaction method declaration
 */
public interface ITransactionService {

    public Transaction save(int debtorId, String creditorEmail, double amount, String description, BankAccount bankAccount);

    public Transaction transfer(int userId, double amount, String description, BankAccount bankAccount);

    public Transaction displayATransaction(int id);

    public void delete(Transaction transaction);

    public List<Transaction> findAll();

    public List<Transaction> findByAccount(Account account);

    void clearDataBase();
}
