package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto", uniqueConstraints = {@UniqueConstraint(columnNames = "symbol")})
@Entity
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    @Column(precision = 16, scale = 8)
    private BigDecimal price;
}
