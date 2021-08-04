package com.payment.application.IT;

import com.payment.application.app.Application;
import com.payment.application.config.DataBaseConfig;
import com.payment.application.dao.AccountDao;
import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import com.payment.application.service.AccountService;
import com.payment.application.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Application.class, DataBaseConfig.class, AccountService.class, TransactionService.class})
@EnableJpaRepositories(basePackageClasses = {TransactionDao.class, AccountDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
@ContextConfiguration(classes = {DataBaseConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TransactionServiceIT {

    @AfterEach
    public void clearDataBase() {
        transactionService.clearDataBase();
        accountService.clearDataBase();
    }

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Test
    public void save() {
        Account creditorAccount = Account.builder().firstName("Martine").lastName("Dupont").email("martine.dupont@email.com")
                .password("password").count(Double.valueOf(1000)).build();
        Account debtorAccount = Account.builder().lastName("Dupont").firstName("Jeanne").password("password")
                .email("jeanne.dupont@email.com").count(Double.valueOf(1000)).build();
        creditorAccount = accountService.save(creditorAccount);
        debtorAccount = accountService.save(debtorAccount);
        accountService.addContact(creditorAccount, debtorAccount);
        Transaction transactionSaved1 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        Transaction transactionSaved2 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        List<Transaction> transactions = transactionService.findAll();
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals(Double.valueOf(100), transactions.get(0).getAmount());
        assertEquals("js event", transactions.get(0).getDescription());
        assertEquals(creditorAccount.getId(), transactions.get(0).getCreditorAccount().getId());
        assertEquals(debtorAccount.getId(), transactions.get(0).getDebtorAccount().getId());
    }

    @Test
    public void displayTransaction() {
        Account creditorAccount = Account.builder().firstName("Martine").lastName("Dupont").email("martine.dupont@email.com")
                .password("password").count(Double.valueOf(1000)).build();
        Account debtorAccount = Account.builder().lastName("Dupont").firstName("Jeanne").password("password")
                .email("jeanne.dupont@email.com").count(Double.valueOf(1000)).build();
        creditorAccount = accountService.save(creditorAccount);
        debtorAccount = accountService.save(debtorAccount);
        accountService.addContact(creditorAccount, debtorAccount);
        Transaction transactionSaved1 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        Transaction transactionSaved2 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        List<Transaction> transactions = transactionService.findAll();
        assertNotNull(transactionService.displayATransaction(transactionSaved1.getId()));
        assertNotNull(transactionService.displayATransaction(transactionSaved2.getId()));
    }

    @Test
    public void deleteTransaction() {
        Account creditorAccount = Account.builder().firstName("Martine").lastName("Dupont").email("martine.dupont@email.com")
                .password("password").count(Double.valueOf(1000)).build();
        Account debtorAccount = Account.builder().lastName("Dupont").firstName("Jeanne").password("password")
                .email("jeanne.dupont@email.com").count(Double.valueOf(1000)).build();
        creditorAccount = accountService.save(creditorAccount);
        debtorAccount = accountService.save(debtorAccount);
        accountService.addContact(creditorAccount, debtorAccount);
        Transaction transactionSaved1 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        Transaction transactionSaved2 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        List<Transaction> transactions = transactionService.findAll();
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        transactionService.delete(transactionSaved1);
        assertNull(transactionService.displayATransaction(transactionSaved1.getId()));
        assertNotNull(transactionService.displayATransaction(transactionSaved2.getId()));
    }

    @Test
    public void findAll() {
        Account creditorAccount = Account.builder().firstName("Martine").lastName("Dupont").email("martine.dupont@email.com")
                .password("password").count(Double.valueOf(1000)).build();
        Account debtorAccount = Account.builder().lastName("Dupont").firstName("Jeanne").password("password")
                .email("jeanne.dupont@email.com").count(Double.valueOf(1000)).build();
        creditorAccount = accountService.save(creditorAccount);
        debtorAccount = accountService.save(debtorAccount);
        accountService.addContact(creditorAccount, debtorAccount);
        Transaction transactionSaved1 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        Transaction transactionSaved2 = transactionService.save(debtorAccount.getId(), "martine.dupont@email.com",
                100, "js event", null);
        List<Transaction> transactions = transactionService.findAll();
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
    }


}
