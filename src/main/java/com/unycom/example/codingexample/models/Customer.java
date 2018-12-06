package com.unycom.example.codingexample.models;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="CUSTOMER")
public class Customer {

    @Id
    @Column(length=20)
    @Size(max=20, min=1)
    private String code;

    @Column(length=256)
    @Size(max=256)
    private String name;

    @Column(length=256)
    @Size(max=256)
    private String location;

    @NotNull
    @Past
    private LocalDate registrationDate = LocalDate.now();

    @NotNull
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CustomerType type;

    @OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
    // In real system it would be better to introduce "DTO" models, and implement mapping JPA<->DTO.
    // But it in this particular case @JsonIgnoreProperties looks like the simplest and correct approach.
    @JsonIgnoreProperties("customer")
    private List<Order> orders;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    // Bi-directional association pattern
    // getOrders always returns unmodifiable/immutable list
    public List<Order> getOrders() {
        if (orders == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(orders);
    }

    public void addOrder(Order o) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        o.setCustomer(this);
    }

    public void removeOrder(Order o) {
        if (o != null) {
            o.setCustomer(null);
        }
    }

    protected void internalAddOrder(Order o) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(o);
    }

    protected void internalRemoveOrder(Order o) {
        if (orders == null) {
            return;
        }
        orders.remove(o);
    }
}
