package com.payment.application.IT;

import com.payment.application.controller.AccountController;
import com.payment.application.controller.TransactionController;
import com.payment.application.dao.AccountDao;
import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import com.payment.application.service.AccountService;
import com.payment.application.service.IAccountService;
import com.payment.application.service.ITransactionService;
import com.payment.application.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AccountController.class, AccountService.class, IAccountService.class, TransactionController.class,
        ITransactionService.class, TransactionService.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {AccountDao.class, TransactionDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
@AutoConfigureMockMvc
public class AccountControllerIT {

    @Autowired
    private MockMvc mvc;

   @MockBean
    private TransactionService transactionService;

   @MockBean
    private AccountService accountService;

    /* @MockBean
    private AccountService accountService;*/

    @WithMockUser("jeanne@mail.com")
    @Test
    public void register_shouldSucceedWith200() throws Exception {
        this.mvc.perform(get("/account/register"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser("jeanne@mail.com")
    @Test
    public void profile_shouldSucceedWith200() throws Exception {
        Account debtorAccount = Account.builder().email("jeanne@mail.com").id(200).count(10000.0)
                .firstName("jeanne").lastName("dupont").build();
        when(accountService.getUser()).thenReturn("jeanne@mail.com");
        when(accountService.getByEmail("jeanne@mail.com")).thenReturn(debtorAccount);
        this.mvc.perform(get("/account/profile"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser("jeanne@mail.com")
    @Test
    public void contact_shouldSucceedWith200() throws Exception {
        Account account = Account.builder().email("jeanne@mail.com").id(200).count(10000.0)
                .firstName("jeanne").lastName("dupont").build();
        Account debtorAccount = Account.builder().email("jeanne@mail.com").id(200).count(10000.0)
                .firstName("jeanne").lastName("dupont").contacts(Set.of(account)).build();
        when(accountService.getUser()).thenReturn("jeanne@mail.com");
        when(accountService.getByEmail("jeanne@mail.com")).thenReturn(debtorAccount);
        this.mvc.perform(get("/account/contact"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser("jeanne@mail.com")
    @Test
    public void credit_shouldSucceedWith200() throws Exception {
        Account debtorAccount = Account.builder().email("jeanne@mail.com").id(200).count(10000.0)
                .firstName("jeanne").lastName("dupont").build();
        when(accountService.getUser()).thenReturn("jeanne@mail.com");
        when(accountService.getByEmail("jeanne@mail.com")).thenReturn(debtorAccount);
        this.mvc.perform(post("/account/credit").param("count", "10")
                .param("email", "martine@mail.com")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser("jeanne@mail.com")
    @Test
    public void connect_shouldSucceedWith200() throws Exception {
        Account account2 = Account.builder().email("jeanne@mail.com").id(200).count(10000.0)
                .firstName("jeanne").lastName("dupont").build();
        Account account1 = Account.builder().email("martine@mail.com").id(200).count(10000.0)
                .firstName("martine").lastName("dupont").build();
        when(accountService.getUser()).thenReturn("jeanne@mail.com");
        when(accountService.getByEmail("martine@mail.com")).thenReturn(account1);
        when(accountService.getByEmail("jeanne@mail.com")).thenReturn(account2);
        this.mvc.perform(post("/account/connect")
                .param("email", "martine@mail.com")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }


}