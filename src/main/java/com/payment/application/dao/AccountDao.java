package com.payment.application.dao;

import com.payment.application.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * This interface contains method to manage account data
 */
@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {

    public Account findById(int id);

    @Query("select a.email from Account a")
    public List<String> getEmails();

    @Query("select a from Account a where a.email=:email")
    public Account getByEmail(@Param("email") String email);

    @Query("Select a from Account a where a not in :contacts and a not in :debtorAccount")
    public Set<Account> findByAccount(Set<Account> contacts, Account debtorAccount);

    @Query("Select a from Account a where a not in :debtorAccount")
    public Set<Account> findByAccount(Account debtorAccount);

}