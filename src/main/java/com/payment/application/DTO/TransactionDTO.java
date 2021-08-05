package com.payment.application.DTO;

import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @Required
    @NotNull
    @NotEmpty
    public Double count;

    @Required
    @NotNull
    @NotEmpty
    public String iban;
}
