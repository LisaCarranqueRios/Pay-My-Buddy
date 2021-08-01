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

import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {Application.class, DataBaseConfig.class, AccountService.class, TransactionService.class})
@EnableJpaRepositories(basePackageClasses = {TransactionDao.class, AccountDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
@ContextConfiguration(classes = {DataBaseConfig.class}, loader = AnnotationConfigContextLoader.class)
public class AccountServiceIT {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @AfterEach
    public void clearDataBase() {
        transactionService.clearDataBase();
        accountService.clearDataBase();
    }

    @Test
    public void save() {
        Account account1 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@email.com").firstName("Jeanne").lastName("Dupont").build();
        Account account = accountService.save(account1);
        assertNotNull(account);
        assertEquals(Double.valueOf(100), account.getCount());
    }

    @Test
    public void saveNoAccountWhenAlreadySave() {
        Account account1 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@mail.com").firstName("Jeanne").lastName("Dupont").build();
        Account accountSaved = accountService.save(account1);
        assertNotNull(accountSaved);
        Account account2 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@mail.com").firstName("Jeanne").lastName("Dupont").build();
        Account accountSaved2 = accountService.save(account1);
        assertNull(accountSaved2);
    }

    @Test
    public void displayAccount() {
        Account account1 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@email.com").firstName("Jeanne").lastName("Dupont").build();
        Account account = accountService.save(account1);
        Account accountById = accountService.displayAccount(account.getId());
        assertNotNull(accountById);
    }

    @Test
    public void delete() {
        Account account1 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@email.com").firstName("Jeanne").lastName("Dupont").build();
        Account account = accountService.save(account1);
        accountService.delete(account);
        assertNull(accountService.displayAccount(account.getId()));
    }

    @Test
    public void findAll() {
        Account account1 = Account.builder().count(Double.valueOf(100)).password("password")
                .email("jeanne.dupont@email.com").firstName("Jeanne").lastName("Dupont").build();
        Account accountSaved1 = accountService.save(account1);
        Account account2 = Account.builder().count(Double.valueOf(100))
                .password("password").email("martine.dupont@email.com").firstName("Martine").lastName("Dupont").build();
        Account accountSaved2 = accountService.save(account2);
        List<Account> accountList = accountService.findAll();
        assertNotNull(accountList);
    }

    @Test
    public void addContact() {
        Account account1 = Account.builder().password("password").lastName("Dupont").firstName("Jeanne").email("jeanne.dupont@email.com")
                .password("password").count(Double.valueOf(100)).contacts(new HashSet<>()).count(Double.valueOf(100)).build();
        Account accountSaved1 = accountService.save(account1);
        Account account2 = Account.builder().password("password").lastName("Dupont").firstName("Martine")
                .email("martine.dupont@email.com").count(Double.valueOf(100)).contacts(new HashSet<>()).count(Double.valueOf(100)).build();
        Account accountSaved2 = accountService.save(account2);
        assertEquals(0, accountSaved1.getContacts().size());
        assertEquals(0, accountSaved2.getContacts().size());
        accountService.addContact(account1, account2);
        assertEquals(1, accountSaved1.getContacts().size());
        assertEquals(1, accountSaved2.getContacts().size());
    }

    @Test
    public void credit() {
        Account account1 = Account.builder().firstName("Jeanne").lastName("Dupont").password("password").count(Double.valueOf(100))
                .email("jeanne.dupont@email.com").contacts(new HashSet<>()).count(Double.valueOf(100)).build();
        Account accountSaved1 = accountService.save(account1);
        accountService.credit(accountSaved1, Double.valueOf(10));
        assertEquals(Double.valueOf(110), accountSaved1.getCount());
    }

    @Test
    public void increaseCount() {
        Account account1 = Account.builder()
                .firstName("Jeanne").lastName("Dupont").password("password").count(Double.valueOf(100))
                .email("jeanne.dupont@email.com").contacts(new HashSet<>()).count(Double.valueOf(100)).build();
        Account accountSaved1 = accountService.save(account1);
        accountService.increaseCount(accountSaved1, Double.valueOf(10));
        assertEquals(Double.valueOf(110), accountSaved1.getCount());
    }

    @Test
    public void decreaseCount() {
        Account account1 = Account.builder()
                .firstName("Jeanne").lastName("Dupont").password("password").count(Double.valueOf(100))
                .email("jeanne.dupont@email.com").contacts(new HashSet<>()).count(Double.valueOf(100)).build();
        Account accountSaved1 = accountService.save(account1);
        accountService.decreaseCount(accountSaved1, Double.valueOf(10));
        assertEquals(Double.valueOf(90), accountSaved1.getCount());
    }

}
