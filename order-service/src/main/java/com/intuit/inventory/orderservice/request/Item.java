/**
 * 
 */
package com.intuit.inventory.orderservice.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rajmanip
 *
 */

@Getter
@Setter
public class Item {
	private Long id;

	@NotNull
	private Long productId;
	@NotNull
	private double price;

	@NotNull
	private Long qty;

}
