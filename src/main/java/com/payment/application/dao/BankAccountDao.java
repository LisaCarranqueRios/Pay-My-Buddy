package com.payment.application.dao;

import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountDao extends JpaRepository<BankAccount, Integer> {

    @Query("select a from BankAccount a where a.userAccount=:user")
    public List<BankAccount> findByUser(Account user);

    @Query("select a.iban from BankAccount a")
    public List<String> getIban();

    @Query("select a from BankAccount a where a.iban=:iban")
    public BankAccount getByIban(@Param("iban") String iban);

}
