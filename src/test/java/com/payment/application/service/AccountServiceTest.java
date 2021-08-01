package com.payment.application.service;

import com.payment.application.dao.AccountDao;
import com.payment.application.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountService accountService;

    @Test
    public void saveTest() {
        Account account = Account.builder().id(1).password("password").build();
        accountService.save(account);
        verify(accountDao, times(1)).save(account);
    }

    @Test
    public void notSavingIfAccountAlreadyExistsTest() {
        Account account = Account.builder().id(1).password("password").email("john@mail.com").build();
        when(accountDao.getEmails()).thenReturn(List.of("john@mail.com"));
        accountService.save(account);
        verify(accountDao, times(0)).save(account);
    }

    @Test
    public void updateTest() {
        Account account = Account.builder().id(1).password("password").email("john@mail.com").build();
        when(accountDao.getByEmail("john@mail.com")).thenReturn(account);
        accountService.update(account);
        verify(accountDao, times(1)).save(account);
    }

    @Test
    public void notUpdatingIfAccountAlreadyExistsTest() {
        Account account = Account.builder().id(1).password("password").email("john@mail.com").build();
        Account formerAccount = Account.builder().id(2).password("password").email("john@mail.com").build();
        when(accountDao.getByEmail("john@mail.com")).thenReturn(formerAccount);
        accountService.update(account);
        verify(accountDao, times(0)).save(account);
    }

    @Test
    public void deleteTest() {
        Account account = Account.builder().id(1).password("password").build();
        accountService.delete(account);
        verify(accountDao, times(1)).delete(account);
    }

    @Test
    public void displayAccountTest() {
        Account account = Account.builder().id(1).password("password").build();
        accountService.displayAccount(account.getId());
        verify(accountDao, times(1)).findById(account.getId());
    }

    @Test
    public void findAllTest() {
        accountService.findAll();
        verify(accountDao, times(1)).findAll();
    }

    @Test
    public void addAccountTest() {
        Account account1 = Account.builder().id(1).contacts(new HashSet<>()).build();
        Account account2 = Account.builder().id(2).contacts(new HashSet<>()).build();
        accountService.addContact(account1, account2);
        assertEquals(1, account1.getContacts().size());
        assertEquals(1, account2.getContacts().size());
        verify(accountDao, times(2)).save(any());
    }

    @Test
    public void addAccountWhenContactListIsNullTest() {
        Account account1 = Account.builder().id(1).build();
        Account account2 = Account.builder().id(2).build();
        accountService.addContact(account1, account2);
        assertEquals(1, account1.getContacts().size());
        assertEquals(1, account2.getContacts().size());
        verify(accountDao, times(2)).save(any());
    }

    @Test
    public void findByAccountWhenContactListIsNotEmpty() {
        Account account = Account.builder().id(2).count(Double.valueOf(100)).build();
        Account debtorAccount = Account.builder().id(1).count(Double.valueOf(100)).contacts(Set.of(account)).build();
        accountService.findByAccount(debtorAccount);
        verify(accountDao).findByAccount(debtorAccount.getContacts(), debtorAccount);
    }

    @Test
    public void findByAccountWhenContactListIsEmpty() {
        Account debtorAccount = Account.builder().id(1).count(Double.valueOf(100)).contacts(new HashSet<>()).build();
        accountService.findByAccount(debtorAccount);
        verify(accountDao).findByAccount(debtorAccount);
    }

    @Test
    public void decreaseCount() {
        Account account = Account.builder().id(1).count(Double.valueOf(100)).contacts(new HashSet<>()).build();
        accountService.decreaseCount(account, 1.0);
        assertEquals(Double.valueOf(99.0), Double.valueOf(account.getCount()));
        verify(accountDao).save(account);
    }

    @Test
    public void increaseCount() {
        Account account = Account.builder().id(1).count(Double.valueOf(100)).contacts(new HashSet<>()).build();
        accountService.increaseCount(account, 1.0);
        assertEquals(Double.valueOf(101.0), Double.valueOf(account.getCount()));
        verify(accountDao).save(account);
    }

    @Test
    public void credit() {
        Account account = Account.builder().id(1).count(Double.valueOf(100)).contacts(new HashSet<>()).build();
        accountService.credit(account, 1.0);
        assertEquals(Double.valueOf(101.0), Double.valueOf(account.getCount()));
        verify(accountDao).save(account);
    }

    @Test
    public void findById() {
        accountService.findById(1);
        verify(accountDao).findById(1);
    }

    @Test
    public void findByEmail() {
        accountService.getByEmail("luc@mail.com");
        verify(accountDao).getByEmail("luc@mail.com");
    }

}
