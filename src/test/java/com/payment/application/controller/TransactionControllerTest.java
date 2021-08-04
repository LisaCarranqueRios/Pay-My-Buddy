package com.payment.application.controller;

import com.payment.application.DTO.TransactionDTO;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.model.Transaction;
import com.payment.application.service.IAccountService;
import com.payment.application.service.IBankAccountService;
import com.payment.application.service.ITransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    ITransactionService transactionService;

    @Mock
    IAccountService accountService;

    @Mock
    IBankAccountService bankAccountService;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @InjectMocks
    TransactionController transac;

    @Test
    public void validate() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0)
                .email("jeannedupont@mail.com").build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2")
                .email("martinedupont@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();

        when(accountService.getUser()).thenReturn("jeannedupont@mail.com");
        when(accountService.getByEmail("jeannedupont@mail.com")).thenReturn(account1);

        Transaction transaction = Transaction.builder().debtorAccount(account1).creditorAccount(account2)
                .id(1).description("description").date(LocalDate.now().toString()).amount(100.0)
                .amountTTC(105.0).rate(0.05).build();

        transac.validate(transaction, bindingResult, model);
        verify(transactionService, times(1)).save(1, "martinedupont@mail.com",
                100.0, "description", null);
    }


    @Test
    public void home() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0)
                .email("jeannedupont@mail.com").build();
        Account account2 = Account.builder().firstName("martine").lastName("dupont").id(2)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2")
                .email("martinedupont@mail.com").count(1000.0)
                .contacts(Set.of(account1)).build();

        when(accountService.getUser()).thenReturn("jeannedupont@mail.com");
        when(accountService.getByEmail("jeannedupont@mail.com")).thenReturn(account1);

        Transaction transaction = Transaction.builder().build();

        transac.home(transaction, bindingResult, model);
        verify(transactionService, times(1)).findByAccount(account1);
    }

    @Test
    public void credit() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0)
                .email("jeannedupont@mail.com").build();
        BankAccount bankAccount = BankAccount.builder().firstName("martine").lastName("dupont").id(2)
                .iban("FR76").bankAccountBalance(1000.0).userAccount(account1).build();

        when(accountService.getUser()).thenReturn("jeannedupont@mail.com");
        when(accountService.getByEmail("jeannedupont@mail.com")).thenReturn(account1);
        when(bankAccountService.getByIban("FR76")).thenReturn(bankAccount);
        TransactionDTO transaction = TransactionDTO.builder().count(Double.valueOf(100)).iban("FR76").build();

        transac.credit(transaction, bindingResult, model);
        verify(transactionService, times(1)).save(account1.getId(), account1.getEmail(),
                Double.valueOf(transaction.getCount()), "Credit from IBAN : " + transaction.getIban(), bankAccount);
    }

    @Test
    public void transfer() {
        Account account1 = Account.builder().firstName("jeanne").lastName("dupont").id(1)
                .password("$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2").count(1000.0)
                .email("jeannedupont@mail.com").build();
        BankAccount bankAccount = BankAccount.builder().firstName("martine").lastName("dupont").id(2)
                .iban("FR76").bankAccountBalance(1000.0).userAccount(account1).build();

        when(accountService.getUser()).thenReturn("jeannedupont@mail.com");
        when(accountService.getByEmail("jeannedupont@mail.com")).thenReturn(account1);
        when(bankAccountService.getByIban("FR76")).thenReturn(bankAccount);
        TransactionDTO transaction = TransactionDTO.builder().count(Double.valueOf(100)).iban("FR76").build();

        transac.transfer(transaction, bindingResult, model);
        verify(transactionService, times(1)).transfer(account1.getId(),
                Double.valueOf(transaction.getCount()), "Transfer from Pay My Buddy account to : " +transaction.getIban(), bankAccount);
    }
}
