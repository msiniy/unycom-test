package com.unycom.example.codingexample.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="CUSTOMER_ORDER")
public class Order {

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

    // Integer is not sufficient to keep 10 digits, BigDecimal and BigInteger
    // is an overkill. Long looks like an appropriate trade-off.
    @NotNull
    @Max(value=9999999999L)
    @Min(value=0)
    private Long price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @ManyToOne
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public long getFinalPrice() {
        if (price <= 0) {
            return 0L;
        }
        BigDecimal originalPrice = new BigDecimal(price);
        BigDecimal discountPercent;
        if (price < 1000) {
            discountPercent = new BigDecimal("0.01");
        } else if (price < 5000) {
            discountPercent = new BigDecimal("0.02");
        } else if (price < 20000) {
            discountPercent = new BigDecimal("0.05");
        } else {
            discountPercent = new BigDecimal("0.1");
        }
        return originalPrice.subtract(originalPrice.multiply(discountPercent))
                .setScale(0, RoundingMode.HALF_EVEN).longValue();
    }
}
