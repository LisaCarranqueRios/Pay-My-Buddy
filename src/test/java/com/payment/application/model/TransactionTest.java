package com.payment.application.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

    @Test
    public void getTransaction() {
        Account creditorAccount = Account.builder().id(1).build();
        Account debtorAccount = Account.builder().id(2).build();
        Transaction transaction = Transaction.builder().id(1)
                .creditorAccount(creditorAccount)
                .debtorAccount(debtorAccount).amount(Double.valueOf(100))
                .date("03/10/2021").description("spring event").rate(0.05).build();
        assertEquals(Double.valueOf(100), transaction.getAmount());
        assertEquals(1, transaction.getId());
        assertEquals(1, transaction.getCreditorAccount().getId());
        assertEquals(2, transaction.getDebtorAccount().getId());
        assertEquals("spring event", transaction.getDescription());
        assertEquals("03/10/2021", transaction.getDate());
        assertEquals(Double.valueOf(0.05), transaction.getRate());
    }

    @Test
    public void setTransaction() {
        Account creditorAccount = Account.builder().id(1).build();
        Account debtorAccount = Account.builder().id(2).build();
        Transaction transaction = Transaction.builder().id(1)
                .creditorAccount(creditorAccount)
                .debtorAccount(debtorAccount).amount(Double.valueOf(100))
                .date("03/10/2021").description("spring event").rate(0.05).build();
        transaction.setId(10);
        transaction.setAmount(Double.valueOf(200));
        transaction.setAmountTTC(Double.valueOf(205));
        transaction.setDate("04/10/2021");
        transaction.setDescription("js event");
        transaction.setRate(Double.valueOf(0.5));
        Account creditorAccount2 = Account.builder().id(10).build();
        Account debtorAccount2 = Account.builder().id(20).build();
        transaction.setCreditorAccount(creditorAccount2);
        transaction.setDebtorAccount(debtorAccount2);
        assertEquals(Double.valueOf(200), transaction.getAmount());
        assertEquals(10, transaction.getId());
        assertEquals(10, transaction.getCreditorAccount().getId());
        assertEquals(20, transaction.getDebtorAccount().getId());
        assertEquals("js event", transaction.getDescription());
        assertEquals("04/10/2021", transaction.getDate());
        assertEquals(Double.valueOf(0.5), transaction.getRate());
    }

}
