package com.codingshuttle.project.uber.uberApp.entities;

import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_wallet_transaction_wallet", columnList = "wallet_id"),
        @Index(name = "idx_wallet_transaction_ride", columnList = "ride_id")
})
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @ManyToOne
    private Ride ride;
//    if transaction related to ride then ride id will get stored otherwise null get stored

    private String transactionId;
//    if it is banking related transaction then may be UTR no get saved


    @ManyToOne
    private Wallet wallet;
//    as one wallet can have many transaction that is many WalletTransaction can be associated to one wallet
//also each transaction belongs to a wallet either driver or rider wallet

    @CreationTimestamp
    private LocalDateTime timeStamp;


}
