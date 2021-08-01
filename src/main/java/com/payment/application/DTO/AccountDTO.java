package com.payment.application.DTO;

import com.payment.application.validation.ValidEmail;
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
/**
 * This class is used to map account email information
 */
public class AccountDTO {

    @Required
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

}
