package com.payment.application.model;

import com.payment.application.validation.ValidEmail;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Required
    @Column(name = "iban")
    @NotNull
    @NotEmpty
    private String iban;
    @NotNull
    @Required
    @Column(name = "first_name")
    @NotEmpty
    @Size(min = 2)
    private String firstName;
    @Required
    @Column(name = "last_name")
    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @Column(name = "bank_account_balance")
    private Double bankAccountBalance;

/*    @Column(name = "amount")
    private Double amount;*/

    @ManyToOne(fetch = FetchType.EAGER)
    private Account userAccount;

}
