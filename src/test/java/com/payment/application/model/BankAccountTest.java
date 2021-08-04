package com.payment.application.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BankAccountTest {

    @Test
    public void getBankAccount() {
        Account account1 = Account.builder().id(1).build();
        BankAccount account = BankAccount.builder().iban("FR76").bankAccountBalance(Double.valueOf(100)).firstName("Jeanne").lastName("Dupont")
                .id(1).userAccount((account1)).build();
        assertEquals("FR76", account.getIban());
        assertEquals(Double.valueOf(100), account.getBankAccountBalance());
        assertEquals("Jeanne", account.getFirstName());
        assertEquals("Dupont", account.getLastName());
        assertEquals(1, account.getId());
    }

    @Test
    public void setBankAccount() {
        Account account1 = Account.builder().id(1).email("jean@mail.com").build();
        Account account2 = Account.builder().id(2).build();
        BankAccount account = BankAccount.builder().iban("FR76").bankAccountBalance(Double.valueOf(100)).firstName("Jeanne").lastName("Dupont")
                .id(1).userAccount(account1).build();
        account.setBankAccountBalance(Double.valueOf(200));
        account.setIban("FR75");
        account.setId(2);
        account.setFirstName("Jean");
        account.setLastName("Martin");
        assertEquals("FR75", account.getIban());
        assertEquals("jean@mail.com", account.getUserAccount().getEmail());
        assertEquals(Double.valueOf(200), account.getBankAccountBalance());
        assertEquals("Jean", account.getFirstName());
        assertEquals("Martin", account.getLastName());
        assertEquals(2, account.getId());
    }

}
