package com.unycom.example.codingexample.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="CUSTOMER_ORDER")
public class Order {

    private static final BigDecimal priceMultiplicator  = new BigDecimal("100");
    private static final int priceScale = 2;
    private static final int ROUND_MODE = BigDecimal.ROUND_HALF_EVEN;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // Since JPA 2.2 (Hibernate 5.3) we finally got native java.time support.
    @Past
    @NotNull
    private LocalDateTime orderDate = LocalDateTime.now();

    @Past
    private LocalDateTime confirmationDate;

    @Past
    private LocalDateTime completionDate;

    @Column(length=256)
    @NotNull
    @Size(max=256)
    private String product;

    // keep price in the database as long value representing amount in cents. (amountInEUR*100)
    @NotNull
    @Max(value=999999999999L) // 10^12 - 1
    @Min(value=0)
    private Long price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @ManyToOne
    @NotNull
    @JsonIgnoreProperties("orders")
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public BigDecimal getPrice() {
        return new BigDecimal(price).divide(priceMultiplicator).setScale(priceScale, ROUND_MODE);
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.multiply(priceMultiplicator).longValue();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.internalRemoveOrder(this);
        }
        this.customer = customer;
        if (this.customer != null) {
            this.customer.internalAddOrder(this);
        }
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public BigDecimal getFinalPrice() {
        if (price <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountPercent;
        if (price < 100000) {
            discountPercent = new BigDecimal("0.01");
        } else if (price < 500000) {
            discountPercent = new BigDecimal("0.02");
        } else if (price < 2000000) {
            discountPercent = new BigDecimal("0.05");
        } else {
            discountPercent = new BigDecimal("0.1");
        }
        BigDecimal originalPrice = getPrice();
        return originalPrice.subtract(originalPrice.multiply(discountPercent)).setScale(priceScale, ROUND_MODE);
    }
}
