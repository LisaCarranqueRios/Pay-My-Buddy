package com.payment.application.controller;

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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@Controller
public class BankAccountController {

    @Autowired
    IBankAccountService bankAccountService;

    @Autowired
    IAccountService accountService;

    @Autowired
    ITransactionService transactionService;


    /**
     * This method is responsible for linking a new bank account to the user Pay My Buddy account
     *
     * @param bankAccount the bank account to link to this application
     * @param result
     * @param model
     * @return The page with the Pay My Buddy user account and bank accounts
     */
    @PostMapping("/bankAccount/add")
    public String connect(BankAccount bankAccount, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        bankAccount.setUserAccount(debtorAccount);
        String addBankSuccessMessage = null;
        String addBankErrorMessage = null;
        if (!result.hasErrors()) {
            BankAccount bankAccountSaved = bankAccountService.save(bankAccount);
            if (bankAccountSaved != null) {
                addBankSuccessMessage = "Bank Account added successfully";
            } else {
                addBankErrorMessage = "Bank Account already saved into database. Please check iban settings.";
            }
        } else {
            addBankErrorMessage = "Please check account information";
        }
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("bankAccounts", bankAccountService.findByUser(debtorAccount));
        model.addAttribute("email", email);
        model.addAttribute("debtorAccount", debtorAccount);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("transactionDTO", new TransactionDTO());
        model.addAttribute("addBankSuccessMessage", addBankSuccessMessage);
        model.addAttribute("addBankErrorMessage", addBankErrorMessage);
        return "account/profile";
    }
}
