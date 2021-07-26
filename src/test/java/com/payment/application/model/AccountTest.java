package com.payment.application.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

    @Test
    public void getAccount() {
        Account account1 = Account.builder().id(1).build();
        Account account2 = Account.builder().id(2).build();
        Account account = Account.builder().email("jeannedupont@mail.com").count(Double.valueOf(100)).firstName("Jeanne").lastName("Dupont")
                .password("password").id(1).contacts(Set.of(account1, account2)).build();
        assertEquals("password", account.getPassword());
        assertEquals("jeannedupont@mail.com", account.getEmail());
        assertEquals(Double.valueOf(100), account.getCount());
        assertEquals("Jeanne", account.getFirstName());
        assertEquals("Dupont", account.getLastName());
        assertEquals(1, account.getId());
    }

    @Test
    public void setAccount() {
        Account account1 = Account.builder().id(1).build();
        Account account2 = Account.builder().id(2).build();
        Account account = Account.builder().email("jeannedupont@mail.com").count(Double.valueOf(100)).firstName("Jeanne").lastName("Dupont")
                .password("password").id(1).contacts(Set.of(account1, account2)).build();
        account.setCount(Double.valueOf(200));
        account.setEmail("jeanne@mail.com");
        account.setPassword("password2");
        account.setId(2);
        account.setFirstName("Jean");
        account.setLastName("Martin");
        assertEquals("password2", account.getPassword());
        assertEquals("jeanne@mail.com", account.getEmail());
        assertEquals(Double.valueOf(200), account.getCount());
        assertEquals("Jean", account.getFirstName());
        assertEquals("Martin", account.getLastName());
        assertEquals(2, account.getId());
    }

}
