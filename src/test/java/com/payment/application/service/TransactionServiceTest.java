package com.payment.application.service;

import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionDao transactionDao;

    @Mock
    Environment environment;

    @InjectMocks
    TransactionService transactionService;

    @Mock
    AccountService accountService;

    @Test
    public void saveNoTransactionWhenNoContact() {
        Account debtor = Account.builder().id(1).password("password").email("jeanne.dupont@email.com")
                .firstName("jeanne").lastName("dupont").count(Double.valueOf(90)).build();
        Account creditor = Account.builder().id(2).password("password").email("martine.dupont@email.com")
                .firstName("martine").lastName("dupont").count(Double.valueOf(100)).build();
        when(accountService.findById(1)).thenReturn(debtor);
        when(accountService.getByEmail(any())).thenReturn(creditor);
        lenient().doReturn("0.05").when(environment).getProperty("rate");
        transactionService.save(1, "martine.dupont@email.com", 100, "description");
        verify(transactionDao, times(0)).save(any());
        assertNull(transactionService.save(1, "martine.dupont@email.com", 100, "description"));
    }

    @Test
    public void saveNoTransactionWhenNoSufficientAmount() {
        Account debtor = Account.builder().id(1).password("password").email("jeanne.dupont@email.com")
                .firstName("jeanne").lastName("dupont").count(Double.valueOf(90)).build();
        Account creditor = Account.builder().id(2).password("password").email("martine.dupont@email.com")
                .firstName("martine").lastName("dupont").count(Double.valueOf(100)).build();

        debtor.setContacts(Set.of(creditor));
        creditor.setContacts(Set.of(debtor));
        when(accountService.findById(1)).thenReturn(debtor);
        when(accountService.getByEmail(any())).thenReturn(creditor);
        lenient().doReturn("0.05").when(environment).getProperty("rate");
        transactionService.save(1, "martine.dupont@email.com", 100, "description");
        verify(transactionDao, times(0)).save(any());
        assertNull(transactionService.save(1, "martine.dupont@email.com", 100, "description"));
    }

    @Test
    public void saveTest() {
        Account debtor = Account.builder().id(1).password("password").email("jeanne.dupont@email.com")
                .firstName("jeanne").lastName("dupont").count(Double.valueOf(10000)).build();
        Account creditor = Account.builder().id(2).password("password").email("martine.dupont@email.com")
                .firstName("martine").lastName("dupont").count(Double.valueOf(10000)).build();
        debtor.setContacts(Set.of(creditor));
        creditor.setContacts(Set.of(debtor));
        when(accountService.findById(1)).thenReturn(debtor);
        when(accountService.getByEmail(any())).thenReturn(creditor);
        lenient().doNothing().when(accountService).decreaseCount(debtor, Double.valueOf(100));
        lenient().doNothing().when(accountService).increaseCount(debtor, Double.valueOf(100));
        lenient().doReturn("0.05").when(environment).getProperty("rate");
        transactionService.save(1, "martine.dupont@email.com", 100, "description");
        Transaction transaction = Transaction.builder().debtorAccount(debtor).creditorAccount(creditor).amount(100.0)
                .description("description").build();
        Transaction transactionSaved = Transaction.builder().debtorAccount(debtor).creditorAccount(creditor).amount(100.0)
                .description("description").id(2).build();
        verify(transactionDao, times(1)).save(any());
        when(transactionDao.save(any())).thenReturn(transactionSaved);
        assertNotNull(transactionService.save(1, "martine.dupont@email.com", 100, "description"));
    }

    @Test
    public void deleteTest() {
        Transaction transaction = Transaction.builder().id(1).build();
        transactionService.delete(transaction);
        verify(transactionDao, times(1)).delete(transaction);
    }

    @Test
    public void displayAccountTest() {
        Transaction transaction = Transaction.builder().id(1).build();
        transactionService.displayATransaction(transaction.getId());
        verify(transactionDao, times(1)).findById(transaction.getId());
    }

    @Test
    public void findAll() {
        transactionService.findAll();
        verify(transactionDao, times(1)).findAll();
    }

    @Test
    public void findByAccount() {
        transactionService.findByAccount(any());
        verify(transactionDao, times(1)).findByAccount(any());
    }

    @Test
    public void clearDataBase() {
        transactionService.clearDataBase();
        verify(transactionDao, times(1)).deleteAll();
    }


}
