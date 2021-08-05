package com.payment.application.service;

import com.payment.application.dao.BankAccountDao;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class BankAccountService implements IBankAccountService {

    @Autowired
    BankAccountDao bankAccountDao;


    /**
     * This method is responsible for getting a bank account by iban
     *
     * @param iban the iban of the selected bank account
     * @return the bank account associated to this iban
     */
    @Override
    public BankAccount getByIban(String iban) {
        return bankAccountDao.getByIban(iban);
    }

    /**
     * This method is responsible for saving a new bank account if it does not already exist in database
     *
     * @param bankAccount the new bank account to save in database
     * @return the saved bank account in database
     */
    @Override
    public BankAccount save(BankAccount bankAccount) {
        List<String> accountIban = bankAccountDao.getIban();
        if (accountIban.contains(bankAccount.getIban())) {
            log.error("Bank Account already saved into database. Please check iban settings.");
            return null;
        }
        return bankAccountDao.save(bankAccount);
    }

    /**
     * This method is responsible for listing all bank accounts existing in database
     *
     * @return the list of the accounts existing in database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<BankAccount> findAll() {
        return bankAccountDao.findAll();
    }

    /**
     * This method is responsible for listing all bank accounts existing in database for the selected user
     *
     * @return the list of the bank accounts existing in database for this user
     */
    @Override
    public List<BankAccount> findByUser(Account user) {
        return bankAccountDao.findByUser(user);
    }

    /**
     * This method is responsible for decreasing a bank account balance
     *
     * @param bankAccount the bank account to debit
     * @param amount      the amount to be removed
     */
    @Override
    public void decreaseCount(BankAccount bankAccount, Double amount) {
        bankAccount.setBankAccountBalance(bankAccount.getBankAccountBalance() - amount);
        bankAccountDao.save(bankAccount);
    }

    /**
     * This method is responsible for increasing a bank account balance
     *
     * @param bankAccount the account to credit
     * @param amount      the credit to be added
     */
    @Override
    public void increaseCount(BankAccount bankAccount, Double amount) {
        bankAccount.setBankAccountBalance(bankAccount.getBankAccountBalance() + amount);
        bankAccountDao.save(bankAccount);
    }

    /**
     * This method is responsible for getting the user email from Spring Security Context
     *
     * @return the email of the logged user
     */
    @Override
    public String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }
}
