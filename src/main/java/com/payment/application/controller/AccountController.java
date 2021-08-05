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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
public class AccountController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBankAccountService bankAccountService;

    /**
     * This method is responsible for getting a list of all accounts existing in database
     *
     * @param model
     * @return the list of all accounts in database
     */
    @RequestMapping("/account/list")
    public String home(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "account/list";
    }

    /**
     * This method is responsible for getting a logging page for an existing user
     *
     * @param request
     * @param model
     * @return a page with a sign in form in order to logging
     */
    @GetMapping("/account/register")
    public String addAccount(WebRequest request, Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "account/register";
    }

    /**
     * This method is responsible for validating and saving a new user
     *
     * @param account the user account information which has to follow Account model rules
     * @param result
     * @param model
     * @return a page with a sign in form in order to logging
     */
    @PostMapping("/account/validate")
    public String validate(@ModelAttribute("account") @Valid Account account, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            account.setPassword(encoder.encode(account.getPassword()));
            account.setCount(Double.valueOf(0));
            Account accountSaved = accountService.save(account);
            if (accountSaved != null)  {
                model.addAttribute("accounts", accountService.findAll());
                return "redirect:/home";
            } else {
                model.addAttribute("errorMessage", "User already saved in database. " +
                        "Please check your settings");
                return "account/register";
            }

        }
        return "account/register";
    }

    /**
     * This method is responsible for getting user profile information for a logged user
     *
     * @param model
     * @return a page with user profile information :
     * - firstname
     * - lastname
     * - account credit
     * and a form to credit his account
     */
    @GetMapping("/account/profile")
    public String profile(Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("bankAccounts", bankAccountService.findByUser(debtorAccount));
        model.addAttribute("email", email);
        model.addAttribute("debtorAccount", debtorAccount);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("transactionDTO", new TransactionDTO());
        model.addAttribute("bankAccount", new BankAccount());
        return "account/profile";
    }

    /**
     * This method is responsible for getting the list of contacts for a logged user
     *
     * @param model
     * @return the list of contacts with their firstname and lastname
     */
    @GetMapping("/account/contact")
    public String contact(Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("email", email);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        return "account/contact";
    }

    /**
     * This method is responsible for adding a contact to the list of contacts for a logged user
     *
     * @param accountDTO
     * @param result
     * @param model
     * @return the page with the list of transaction for the logged user, and a success information for this procedure
     * orthe page with the list of transaction with an alert message if the procedure failed
     */
    @PostMapping("/account/connect")
    public String connect(@Valid AccountDTO accountDTO, BindingResult result, Model model) {
        String email = accountService.getUser();
        Account debtorAccount = accountService.getByEmail(email);
        String successMessage = null;
        String errorMessage = null;
        if (!result.hasErrors()) {
            Account contact = accountService.getByEmail(accountDTO.getEmail());
            if (contact != null) {
                accountService.addContact(contact, debtorAccount);
                successMessage = "Connection added successfully";
            } else {
                errorMessage = "Cannot find user in database. Please check email account";
            }
        } else {
            errorMessage = "Invalid email format";
        }
        List<Transaction> accountTransactions = transactionService.findByAccount(debtorAccount);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("email", email);
        model.addAttribute("transactions", accountTransactions);
        model.addAttribute("contacts", debtorAccount.getContacts());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("account", new Account());
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);
        return "transaction/list";
    }


}