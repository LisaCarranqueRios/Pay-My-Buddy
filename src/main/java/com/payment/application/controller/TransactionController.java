package com.payment.application.controller;

import com.payment.application.DTO.AccountDTO;
import com.payment.application.DTO.TransactionDTO;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.model.Transaction;
import com.payment.application.service.IAccountService;
import com.payment.application.service.IBankAccountService;
import com.payment.application.service.ITransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Log4j2
@Controller
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBankAccountService bankAccountService;

    /**
     * This method is responsible for saving a new transaction
     * @param transaction the new transaction to be saved
     * @param result
     * @param model
     * @return the refreshed page with the list of all transactions
     */
    @PostMapping("/transaction/validate")
    public String validate(Transaction transaction, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        Transaction transactionSaved = null;
        if (!result.hasErrors()) {
            transactionSaved = transactionService.save(debtorAccount.getId(), transaction.getCreditorAccount().getEmail(),
                    Double.valueOf(transaction.getAmount()), transaction.getDescription(), null);
        }
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        Set<Account> accountToAdd = accountService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountToAdd);
        model.addAttribute("email", email);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("accountDTO", new AccountDTO());
        if (transactionSaved == null) {
            model.addAttribute("errorMessage", "Please credit your account from profile section.");
        } else {
            model.addAttribute("successMessage", "Transaction successful.");
        }
        return "transaction/list";
    }

    /**
     * This method is responsible for displaying the page of all existing transaction
     * @param transaction
     * @param result
     * @param model
     * @return the page with the list of all transactions
     */
    @GetMapping("/transaction/transfer")
    public String home(Transaction transaction, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        Set<Account> accountToAdd = accountService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountToAdd);
        model.addAttribute("email", email);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("accountDTO", new AccountDTO());
        return "transaction/list";
    }

    /**
     * This method is responsible for transferring money from the user bank account to the user
     * Pay My Buddy account
     * @param transactionDTO
     * @param result
     * @param model
     * @return The page with the Pay My Buddy user account and bank accounts
     */
    @PostMapping("/transaction/creditAccount")
    public String credit(TransactionDTO transactionDTO, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        BankAccount bankAccount = bankAccountService.getByIban(transactionDTO.getIban());
        Transaction transaction = null;
        String creditSuccessMessage = null;
        String creditErrorMessage = null;
        if (!result.hasErrors()) {
            transaction = transactionService.save(debtorAccount.getId(), debtorAccount.getEmail(),
                    Double.valueOf(transactionDTO.getCount()), "Credit from IBAN : " + transactionDTO.getIban()
            , bankAccount);
            if (transaction != null) {
                creditSuccessMessage = "Money transferred from your bank account to your Pay My Buddy account.";
            } else {
                creditErrorMessage = "Not enough credit for this transaction. Please credit your bank account for more transaction.";
            }
        }
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("bankAccounts", bankAccountService.findByUser(debtorAccount));
        model.addAttribute("email", email);
        model.addAttribute("debtorAccount", accountService.getByEmail(email));
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("bankAccount", new BankAccount());
        model.addAttribute("transactionDTO", new TransactionDTO());

        model.addAttribute("creditSuccessMessage", creditSuccessMessage);
        model.addAttribute("creditErrorMessage", creditErrorMessage);
        return "account/profile";
    }


    /**
     * This method is responsible for transferring money from the Pay My Buddy user account to the
     * selected user bank account
     * @param transactionDTO
     * @param result
     * @param model
     * @return The page with the Pay My Buddy user account and bank accounts
     */
    @PostMapping("/transaction/accountToBankAccount")
    public String transfer(TransactionDTO transactionDTO, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account payMyBuddyAccount = accountService.getByEmail(email);
        BankAccount bankAccount = bankAccountService.getByIban(transactionDTO.getIban());
        Transaction transaction = null;
        String transferSuccessMessage = null;
        String transferErrorMessage = null;
        if (!result.hasErrors()) {
            transaction = transactionService.transfer(payMyBuddyAccount.getId(),
                    Double.valueOf(transactionDTO.getCount()), "Transfer from Pay My Buddy account to : " +transactionDTO.getIban()
            , bankAccount);
            if (transaction != null) {
                transferSuccessMessage = "Money transferred from your Pay My Buddy account to your bank account.";
            } else {
                transferErrorMessage = "Not enough credit for this transaction. Please credit your Pay My Buddy Account for more transaction.";
            }
        }
        List<Transaction> accountTransactions = transactionService.findByAccount(payMyBuddyAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("bankAccounts", bankAccountService.findByUser(payMyBuddyAccount));
        model.addAttribute("email", email);
        model.addAttribute("debtorAccount", payMyBuddyAccount);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", payMyBuddyAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("bankAccount", new BankAccount());
        model.addAttribute("transactionDTO", new TransactionDTO());
        model.addAttribute("transferSuccessMessage", transferSuccessMessage);
        model.addAttribute("transferErrorMessage", transferErrorMessage);
        return "account/profile";
    }



}
