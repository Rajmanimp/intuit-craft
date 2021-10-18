/**
 * 
 */
package com.intuit.inventory.orderservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.inventory.orderservice.exception.ValidationException;
import com.intuit.inventory.orderservice.model.ItemInfo;
import com.intuit.inventory.orderservice.model.OrderInfo;
import com.intuit.inventory.orderservice.repository.OrderRepository;
import com.intuit.inventory.orderservice.request.Item;
import com.intuit.inventory.orderservice.util.InventoryInfoUtil;

/**
 * @author rajmanip
 *
 */
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public OrderInfo createOrder(OrderInfo order) {
		List<ItemInfo> items = InventoryInfoUtil.validateQuantity(order.getItems());
		order.getItems().clear();
		if (items.size() == 0) {
			throw new ValidationException("Item Quantities are greater then the available inventory");
		}
		order.setItems(items);
		order = orderRepository.save(order);
		return order;
	}

	public OrderInfo getOrderDetails(Long orderId) {
		Optional<OrderInfo> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			return order.get();
		} else {
			throw new NoSuchElementException("Data not found for the requested Order");
		}
	}

	public List<OrderInfo> getOrderDetailForUser(Long userId) {
		List<OrderInfo> orders = orderRepository.findByUserId(userId);
		if (orders.size() > 0) {
			return orders;
		} else {
			throw new NoSuchElementException("Data not found for the requested User");
		}
	}

	public OrderInfo updateOrderDetails(OrderInfo order) {
		order = orderRepository.save(order);
		return order;
	}

	public OrderInfo updateOrderStatus(Long orderId, String status) {
		OrderInfo order = getOrderDetails(orderId);
		order.setOrderStatus(status);
		order = orderRepository.save(order);
		return order;
	}

	public OrderInfo deleteOrder(Long order) {
		OrderInfo orderInfo = getOrderDetails(order);
		orderRepository.deleteById(order);
		return orderInfo;
	}

}
