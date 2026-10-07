package org.example.ebankservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ebankservice.model.Customer;

import java.util.Date;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id @GeneratedValue
    private String id;
    private Date createAt;
    private Double balance;
    private String type;
    private Long customerId;
    @Transient
    private Customer customer;

}
