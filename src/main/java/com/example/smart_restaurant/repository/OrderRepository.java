package com.example.smart_restaurant.repository;

import com.example.smart_restaurant.model.Order;
import com.example.smart_restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerOrderByOrderTimeDesc(User customer);
}