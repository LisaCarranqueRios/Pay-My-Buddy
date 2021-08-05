package com.payment.application.controller;

import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.service.IAccountService;
import com.payment.application.service.IBankAccountService;
import com.payment.application.service.ITransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountControllerTest {

    @InjectMocks
    BankAccountController bankAccountController;

    @Mock
    IBankAccountService bankAccountService;

    @Mock
    IAccountService accountService;

    @Mock
    ITransactionService transactionService;

    @Spy
    BindingResult bindingResult;

    @Spy
    Model model;

    @Test
    public void contact() {
        Account account = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("martine@mail.com").count(1000.0).build();
        BankAccount bankAccount = BankAccount.builder().firstName("jeanne").lastName("dupont").id(1)
                .iban("FR76").userAccount(account).bankAccountBalance(1000.0).build();

        when(accountService.getUser()).thenReturn("martine@email.com");
        when(accountService.getByEmail(anyString())).thenReturn(account);

        bankAccountController.connect(bankAccount, bindingResult, model);
        verify(bankAccountService, times(1)).save(bankAccount);
    }

}
