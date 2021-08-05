package com.payment.application.IT;

import com.payment.application.controller.AccountController;
import com.payment.application.controller.TransactionController;
import com.payment.application.dao.AccountDao;
import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import com.payment.application.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AccountController.class, IAccountService.class, AccountService.class,
        TransactionController.class, ITransactionService.class, TransactionService.class, IBankAccountService.class,
        BankAccountService.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {AccountDao.class, TransactionDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
public class AccountControllerRedirectToLoginIT {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void login() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/login", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void homeShouldRedirectForUnidentifiedUser() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/home", String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void accountProfileShouldRedirectForUnidentifiedUser() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/account/profile", String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void accountContactShouldRedirectForUnidentifiedUser() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/account/contact", String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void accountConnectShouldRedirectForUnidentifiedUser() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/account/connect", String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void accountCreditShouldRedirectForUnidentifiedUser() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("jeanne@mail.com",
                "password")
                .getForEntity("/account/credit", String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }


}
