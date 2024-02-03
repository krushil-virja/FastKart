package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
