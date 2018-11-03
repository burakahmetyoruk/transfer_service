package com.burak.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents a monetary account with name and balance
 * @author byoruk
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unq_account_name", columnNames = "name")})
public class Account implements Serializable {

    public Account(String name) {
        this.name = name;
        //Initial Balance
        this.balance = BigDecimal.TEN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private BigDecimal balance;
}