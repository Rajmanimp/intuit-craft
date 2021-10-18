package com.intuit.inventory.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.inventory.orderservice.model.OrderInfo;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

	public List<OrderInfo> findByUserId(Long userId);

}
