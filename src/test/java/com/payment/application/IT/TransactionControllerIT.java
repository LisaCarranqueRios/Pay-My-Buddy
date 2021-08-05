package com.payment.application.IT;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TransactionController.class, AccountService.class, IAccountService.class,
        ITransactionService.class, TransactionService.class, IBankAccountService.class, BankAccountService.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {AccountDao.class, TransactionDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
@AutoConfigureMockMvc
public class TransactionControllerIT {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ITransactionService transactionService;

    @MockBean
    private IAccountService accountService;


    @WithMockUser("jeanne@mail.com")
    @Test
    public void givenAuthRequestTransferOnPrivateService_shouldSucceedWith200() throws Exception {
        Account account = Account.builder().lastName("Dupont").firstName("Jeanne")
                .id(200).email("jeanne@mail.com").build();
        Account debtorAccount = Account.builder().lastName("Dupont").firstName("Jeanne")
                .id(200).contacts(Set.of(account)).email("jeanne@mail.com").count(Double.valueOf(100)).build();
        when(accountService.getUser()).thenReturn("jeanne@mail.com");
        when(accountService.getByEmail("jeanne@mail.com")).thenReturn(debtorAccount);
        this.mvc.perform(get("/transaction/transfer"))
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
        this.mvc.perform(post("/transaction/creditAccount").param("count", "10")
                .param("email", "martine@mail.com")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }


}