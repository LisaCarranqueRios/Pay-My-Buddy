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
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Required
    @Column(name = "email")
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    @NotNull
    @Required
    @Column(name = "password")
    @NotEmpty
    @Size(min = 2)
    private String password;
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

    @Column(name = "count")
    private Double count;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_contact",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "contact_id")})
    private Set<Account> contacts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
