package com.payment.application.service;

import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    AccountService accountService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    Environment environment;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Transaction save(int debtorId, String creditorEmail, double amount, String description,
                             BankAccount bankAccount) {
        Account debtorAccount = accountService.findById(debtorId);
        Account creditorAccount = accountService.getByEmail(creditorEmail);
        if (debtorAccount.getContacts() != null && debtorAccount.getContacts().contains(creditorAccount)
                && (debtorAccount.getCount() >= calculateTransactionAmountWithRate(amount))) {
            Transaction transaction = Transaction.builder().debtorAccount(debtorAccount)
                    .creditorAccount(creditorAccount).amount(amount).date(LocalDate.now().toString())
                    .description(description).rate(Double.valueOf(environment.getProperty("rate")))
                    .amountTTC(calculateTransactionAmountWithRate(amount)).build();
            accountService.decreaseCount(debtorAccount, transaction.getAmountTTC());
            accountService.increaseCount(creditorAccount, transaction.getAmount());
            return transactionDao.save(transaction);
        }
            else if (debtorAccount.equals(creditorAccount) &&
                bankAccount.getBankAccountBalance() >= calculateTransactionAmountWithRate(amount)) {
            Transaction transaction = Transaction.builder().debtorAccount(debtorAccount)
                    .creditorAccount(creditorAccount).amount(amount).date(LocalDate.now().toString())
                    .description(description).rate(Double.valueOf(environment.getProperty("rate")))
                    .amountTTC(calculateTransactionAmountWithRate(amount)).build();
            accountService.increaseCount(debtorAccount, transaction.getAmount());
            bankAccountService.decreaseCount(bankAccount, transaction.getAmountTTC());
            return transactionDao.save(transaction);
        } else if (debtorAccount.getCount() <= calculateTransactionAmountWithRate(amount)) {
            log.error("Not enough credit for this transaction. Please credit your count for more transaction.");
        } else if (debtorAccount.getContacts() == null) {
            log.error("Please add contact first.");
        } else {
            log.error("Error during transaction");
        }
        return null;
    }


    /**
     * This method is responsible to transfer money from user Pay My Buddy account to one of the linked bank accounts
     * @param userId
     * @param amount
     * @param description
     * @param bankAccount
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Transaction transfer(int userId,double amount, String description,
                            BankAccount bankAccount) {
        Account payMyBuddyAccount = accountService.findById(userId);
        if (payMyBuddyAccount.getCount() >= calculateTransactionAmountWithRate(amount)) {
            Transaction transaction = Transaction.builder().debtorAccount(payMyBuddyAccount)
                    .creditorAccount(payMyBuddyAccount).amount(amount).date(LocalDate.now().toString())
                    .description(description).rate(Double.valueOf(environment.getProperty("rate")))
                    .amountTTC(calculateTransactionAmountWithRate(amount)).build();
            accountService.decreaseCount(payMyBuddyAccount, transaction.getAmountTTC());
            bankAccountService.increaseCount(bankAccount, transaction.getAmount());
            return transactionDao.save(transaction);
        } else if (payMyBuddyAccount.getCount() <= calculateTransactionAmountWithRate(amount)) {
            log.error("Not enough credit for this transaction. Please credit your Pay My Buddy Account for more transaction.");
        } else {
            log.error("Error during transaction");
        }
        return null;
    }

    /**
     * This method is responsible for getting a transaction by id
     * @param id the id of the selected transaction
     * @return the transaction selected by id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public Transaction displayATransaction(int id) {
        return transactionDao.findById(id);
    }

    /**
     * This method is responsible for deleting a transaction
     * @param transaction the transaction to be deleted
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(Transaction transaction) {
        transactionDao.delete(transaction);
    }

    /**
     * This method is responsible for listing all the transactions existing in database
     * @return the list of the transaction existing in database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    /**
     * This method is responsible for getting the list of transaction associated to a selected account
     * @param account the selected account
     * @return the list of the transaction associated to this account
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<Transaction> findByAccount(Account account) {
        return transactionDao.findByAccount(account);
    }

    /**
     * This method is responsible for removing all transactions from database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void clearDataBase() {
        transactionDao.deleteAll();
    }

    /**
     * This method is responsible for giving the amount TTC for a transaction
     * @param amount the amount of this transaction
     * @return the amount TTC
     */
    private Double calculateTransactionAmountWithRate(Double amount) {
        return amount + Double.valueOf(environment.getProperty("rate")) * amount;
    }

}

