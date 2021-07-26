package com.payment.application.controller;

import com.payment.application.DTO.AccountDTO;
import com.payment.application.model.Account;
import com.payment.application.service.IAccountService;
import com.payment.application.service.ITransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    AccountController accountController;

    @Mock
    IAccountService accountService;

    @Mock
    ITransactionService transactionService;

    @Spy
    BindingResult bindingResult;

    @Spy
    Model model;


    @Test
    public void credit() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0).build();
        Account account2 = Account.builder().firstName("jeanne").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0)
                .contacts(Set.of(account1)).build();

        when(accountService.getUser()).thenReturn("email@email.com");
        when(accountService.getByEmail(anyString())).thenReturn(account2);

        accountController.credit(account2, bindingResult, model);
        verify(accountService, times(1)).getByEmail(anyString());
        verify(accountService, times(1)).credit(any(),anyDouble());
    }

    @Test
    public void connect() {
        AccountDTO accountDTO = AccountDTO.builder().email("jeanne@mail.com").build();
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0).build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("martine@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();

        when(accountService.getByEmail(anyString())).thenReturn(account2);
        when(accountService.getUser()).thenReturn("martine@email.com");

        accountController.connect(accountDTO, bindingResult, model);
        verify(accountService, times(2)).getByEmail(anyString());
        verify(accountService, times(1)).addContact(any(), any());
    }

    @Test
    public void connectThrowExceptionWhenEmailInvalid() {
        AccountDTO accountDTO = AccountDTO.builder().email("@mail.com").build();
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0).build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("martine@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();

        when(accountService.getByEmail(anyString())).thenReturn(account2);
        when(accountService.getUser()).thenReturn("martine@email.com");

        accountController.connect(accountDTO, bindingResult, model);
    }

    @Test
    public void contact() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("jeanne@mail.com").count(1000.0).build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("martine@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();
        when(accountService.getUser()).thenReturn("martine@email.com");
        when(accountService.getByEmail(anyString())).thenReturn(account2);

        accountController.contact(model);
        verify(accountService, times(1)).getByEmail(anyString());
    }

    @Test
    public void profile() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("jeanne@mail.com").count(1000.0).build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("martine@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();
        when(accountService.getUser()).thenReturn("martine@email.com");
        when(accountService.getByEmail(anyString())).thenReturn(account2);

        accountController.profile(model);
        verify(accountService, times(1)).getByEmail(anyString());
    }

    @Test
    public void validate() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").email("jeanne@mail.com").count(1000.0).build();
        accountController.validate(account1, bindingResult, model);
        verify(accountService, times(1)).save(any());
    }

}
