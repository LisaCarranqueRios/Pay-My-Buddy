package com.payment.application.IT;

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

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TransactionController.class, AccountService.class, IAccountService.class,
        ITransactionService.class, TransactionService.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {AccountDao.class, TransactionDao.class})
@EntityScan(basePackageClasses = {Account.class, Transaction.class})
@AutoConfigureMockMvc
public class TransactionControllerIT {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;


    @WithMockUser("jeanne@mail.com")
    @Test
    public void givenAuthRequestTransferOnPrivateService_shouldSucceedWith200() throws Exception {
        this.mvc.perform(get("/transaction/transfer"))
                .andExpect(status().isOk())
                .andReturn();
    }


    @WithMockUser("jeanne@mail.com")
    @Test
    public void givenAuthRequestTransactionValidationOnPrivateService_shouldSucceedWith200() throws Exception {

        this.mvc.perform(post("/transaction/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser("jeanne@mail.com")
    @Test
    public void givenAuthRequestTransactionWithWrongURL_shouldFail() throws Exception {

        this.mvc.perform(post("/transaction/validat")
                .with(csrf()))
                .andExpect(status().is(404))
                .andReturn();
    }


}