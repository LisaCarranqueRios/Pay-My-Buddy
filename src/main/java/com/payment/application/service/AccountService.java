package com.payment.application.service;

import com.payment.application.dao.AccountDao;
import com.payment.application.model.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountDao accountDao;

    /**
     * This method is responsible for getting an account by id
     *
     * @param id the id of the selected account
     * @return the account associated to this id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Account findById(int id) {
        return accountDao.findById(id);
    }

    /**
     * This method is responsible for getting an account by email
     *
     * @param email the email of the selected account
     * @return the account associated to this email
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Account getByEmail(String email) {
        return accountDao.getByEmail(email);
    }

    /**
     * This method is responsible for saving a new account if it does not already exist in database
     *
     * @param account the new account to save in database
     * @return the saved account in database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Account save(Account account) {
        List<String> accountEmails = accountDao.getEmails();
        if (accountEmails.contains(account.getEmail())) {
            log.error("Account already saved into database. Please check email settings.");
            return null;
        }
        return accountDao.save(account);
    }

    /**
     * This method is responsible for updating account information for an existing account
     * if it is found in database
     *
     * @param account the account to update
     * @return the updated account or null if it is not found in database
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Account update(Account account) {
        Account existingAccount = accountDao.getByEmail(account.getEmail());
        if (existingAccount != null && !existingAccount.equals(account)) {
            log.error("incorrect email for account update");
            return null;
        }
        return accountDao.save(account);
    }

    /**
     * This method is responsible for getting an account by id
     *
     * @param id the id of the selected account
     * @return the account associated with this id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Account displayAccount(int id) {
        return accountDao.findById(id);
    }

    /**
     * This method is responsible for deleting an account in database
     *
     * @param account the account to delete
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }

    /**
     * This method is responsible for listing all accounts existing in database
     *
     * @return the list of the accounts existing in database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    /**
     * This method is responsible for adding as contact two accounts
     *
     * @param account1 the first account in contact
     * @param account2 the second account in contact
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addContact(Account account1, Account account2) {
        if (account1.getContacts() == null) {
            account1.setContacts(new HashSet<>());
        }
        if (account2.getContacts() == null) {
            account2.setContacts(new HashSet<>());
        }
        account1.getContacts().add(account2);
        account2.getContacts().add(account1);
        this.update(account1);
        this.update(account2);
    }

    /**
     * This method is responsible for adding credit to a user account
     *
     * @param account the account on which to add credit
     * @param credit  the amount of credit to add to this account
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void credit(Account account, Double credit) {
        account.setCount(account.getCount() + credit);
        accountDao.save(account);
    }

    /**
     * This method is responsible for increasing a creditor account
     *
     * @param account the creditor account to credit
     * @param amount  the amount to be added
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void increaseCount(Account account, Double amount) {
        account.setCount(account.getCount() + amount);
        accountDao.save(account);
    }

    /**
     * This method is responsible for decreasing a debtor account
     *
     * @param account the debtor account to debit
     * @param amount  the amount to be removed
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void decreaseCount(Account account, Double amount) {
        account.setCount(account.getCount() - amount);
        accountDao.save(account);
    }

    /**
     * This method is responsible for getting an account contacts
     *
     * @param debtorAccount the selected account
     * @return the list of contacts of the selected account
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public Set<Account> findByAccount(Account debtorAccount) {
        if (debtorAccount.getContacts().isEmpty()) {
            return accountDao.findByAccount(debtorAccount);
        }
        return accountDao.findByAccount(debtorAccount.getContacts(), debtorAccount);
    }

    /**
     * This method is responsible for removing all accounts from database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void clearDataBase() {
        accountDao.deleteAll();
    }

    /**
     * This method is responsible for getting the user email from Spring Security Context
     *
     * @return the email of the logged user
     */
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
