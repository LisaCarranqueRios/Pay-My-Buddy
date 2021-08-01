package com.payment.application.dao;

import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface contains method to manage transaction data
 */
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    public Transaction findById(int id);

    @Query("Select t from Transaction t where t.debtorAccount=:account")
    public List<Transaction> findByAccount(Account account);

}