package com.payment.application.service;

import com.payment.application.dao.BankAccountDao;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    BankAccountDao bankAccountDao;

    @InjectMocks
    BankAccountService bankAccountService;

    @Test
    public void increaseCount() {
        BankAccount bankAccount = BankAccount.builder().id(1).bankAccountBalance(Double.valueOf(100)).userAccount(new Account()).build();
        bankAccountService.increaseCount(bankAccount, 1.0);
        assertEquals(Double.valueOf(101.0), Double.valueOf(bankAccount.getBankAccountBalance()));
        verify(bankAccountDao).save(bankAccount);
    }

    @Test
    public void decreaseCount() {
        BankAccount bankAccount = BankAccount.builder().id(1).bankAccountBalance(Double.valueOf(100)).userAccount(new Account()).build();
        bankAccountService.decreaseCount(bankAccount, 1.0);
        assertEquals(Double.valueOf(99.0), Double.valueOf(bankAccount.getBankAccountBalance()));
        verify(bankAccountDao).save(bankAccount);
    }

    @Test
    public void findByUser() {
        Account user = Account.builder().id(1).build();
        bankAccountService.findByUser(user);
        verify(bankAccountDao).findByUser(user);
    }

    @Test
    public void findAll() {
        bankAccountService.findAll();
        verify(bankAccountDao).findAll();
    }

    @Test
    public void save() {
        BankAccount account = BankAccount.builder().id(1).iban("FR75").build();
        bankAccountService.save(account);
        verify(bankAccountDao, times(1)).save(account);
    }

    @Test
    public void findByIban() {
        BankAccount bankAccount = BankAccount.builder().id(1).iban("FR76").build();
        bankAccountService.getByIban(bankAccount.getIban());
        verify(bankAccountDao).getByIban(bankAccount.getIban());
    }

}
