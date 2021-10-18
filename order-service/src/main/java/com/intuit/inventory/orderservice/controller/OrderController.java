/**
 * 
 */
package com.intuit.inventory.orderservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.inventory.orderservice.model.OrderInfo;
import com.intuit.inventory.orderservice.request.Order;
import com.intuit.inventory.orderservice.service.OrderService;

import io.swagger.annotations.ApiOperation;

/**
 * @author rajmanip
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	private ModelMapper modelMapper = new ModelMapper();

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class.getName());

	@ApiOperation(value = "Retrieve Order details", response = Order.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrders(@PathVariable Long id) {
		LOGGER.debug("fetch the order details for id:{}", id);
		OrderInfo order = orderService.getOrderDetails(id);
		Order response = convertToOrder(order);
		LOGGER.debug("order details for id:{}", response);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "create Order", response = Order.class)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Order createUser(@RequestBody Order order) {
		LOGGER.info("Order recieved with details:{}", order);
		OrderInfo orderInfo = orderService.createOrder(convertToOrderInfo(order));
		Order response = convertToOrder(orderInfo);
		LOGGER.info("Order saved with details:{}", response);
		return response;
	}

	@ApiOperation(value = "delete Order", response = Order.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> deleteUser(@PathVariable Long id) {
		LOGGER.info("delete request recieved for order id:{}", id);
		OrderInfo orderInfo = orderService.deleteOrder(id);
		Order response = convertToOrder(orderInfo);
		LOGGER.info("order with id:{} deleted", id);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update Order", response = Order.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Order> deleteUser(@RequestBody Order order) {
		LOGGER.info("update request recieved for order :{}", order);
		OrderInfo orderInfo = orderService.updateOrderDetails(convertToOrderInfo(order));
		Order response = convertToOrder(orderInfo);
		LOGGER.info("order updated :{}", response);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update Order status", response = Order.class)
	@RequestMapping(value = "/{id}/update-status", method = RequestMethod.PATCH)
	public ResponseEntity<Order> updateStatus(@PathVariable Long id, String status) {
		LOGGER.info("status update request recieved for order :{} to {}", id, status);
		OrderInfo orderInfo = orderService.updateOrderStatus(id, status);
		Order response = convertToOrder(orderInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Retrieve Order details for user", response = Order.class)
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<Order>> getUser(Long userId) {
		LOGGER.debug("fetch the order details for user:{}", userId);
		List<OrderInfo> orders = orderService.getOrderDetailForUser(userId);
		List<Order> response = convertToOrderList(orders);
		return ResponseEntity.ok(response);
	}

	private Order convertToOrder(OrderInfo info) {
		Order order = modelMapper.map(info, Order.class);
		return order;
	}

	private OrderInfo convertToOrderInfo(Order info) {
		OrderInfo order = modelMapper.map(info, OrderInfo.class);
		return order;
	}

	private List<Order> convertToOrderList(List<OrderInfo> orderInfos) {
		List<Order> orders = orderInfos.stream().map(orderInfo -> modelMapper.map(orderInfo, Order.class))
				.collect(Collectors.toList());
		return orders;
	}
}
