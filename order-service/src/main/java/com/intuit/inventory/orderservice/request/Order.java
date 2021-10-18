/**
 * 
 */
package com.intuit.inventory.orderservice.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rajmanip
 *
 */
@Getter
@Setter
public class Order {
	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private Long addressId;

	@NotNull
	private String orderStatus;

	@DecimalMin("0")
	@NotNull
	private double amount;

	@NotNull
	private String paymentStatus;

	private Date orderDate;
	private Date updatedDate;

	@NotNull
	List<Item> items;

	
}
