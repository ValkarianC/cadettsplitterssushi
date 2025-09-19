package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sushi-customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    @Column(length = 50, name = "customer_first_name", nullable = false)
    private String firstName;
    @Column(length = 50, name = "customer_last_name", nullable = false)
    private String lastName;
    @Column(length = 20, name = "customer_phone_number")
    private String phoneNumber;
    @Column(length = 50, name = "customer_email")
    private String email;

    public Customer() {
    }

    public Customer(long id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
