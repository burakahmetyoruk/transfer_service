package com.burak.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal transferAmount;

    @ManyToOne
    @JoinColumn(name = "transferrer_id", referencedColumnName = "id")
    private Account transferrer;

    @ManyToOne
    @JoinColumn(name = "transferred_id", referencedColumnName = "id")
    private Account transferred;
}