package com.payment.application.controller;

import com.payment.application.DTO.AccountDTO;
import com.payment.application.model.Account;
import com.payment.application.model.Transaction;
import com.payment.application.service.IAccountService;
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
                    Double.valueOf(transaction.getAmount()), transaction.getDescription());
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


}
