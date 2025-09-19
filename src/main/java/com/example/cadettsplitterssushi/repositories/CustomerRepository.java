package com.example.cadettsplitterssushi.repositories;

import com.example.cadettsplitterssushi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
