package com.payment.application.model;

import lombok.*;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:value.properties")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "amount")
    @NotNull
    private Double amount;
    @Column(name = "amount_ttc")
    private Double amountTTC;
    @Column(name = "rate")
    private Double rate;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account debtorAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account creditorAccount;
}
