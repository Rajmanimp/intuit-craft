/**
 * 
 */
package com.intuit.inventory.orderservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.intuit.inventory.orderservice.model.ItemInfo;

/**
 * @author rajmanip
 *
 */
public class InventoryInfoUtil {

	private static final String URL = "http://localhost:8080/product";

	/**
	 * @param args
	 */
	public static List<ItemInfo> validateQuantity(List<ItemInfo> items) {
		List<ItemInfo> availableItems = new ArrayList<ItemInfo>();
		for (ItemInfo item : items) {
			if (isItemQuantityValid(item)) {
				availableItems.add(item);
				updateQuantity(item);
			}
		}
		return availableItems;
	}

	private static boolean isItemQuantityValid(ItemInfo item) {
		boolean isValid = false;
		String inventoryUrl = URL + "/{id}/qty";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", item.getProductId() + "");
		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.getForObject(inventoryUrl, String.class, params);
			if (item.getQty() <= Long.valueOf(result)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return isValid;
	}

	private static boolean updateQuantity(ItemInfo item) {
		boolean isValid = false;
		item.setQty(item.getQty()*-1);
		String inventoryUrl = URL + "/" + item.getProductId() + "/update-qty?qty=" + item.getQty();

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "merge-patch+json");
		headers.setContentType(mediaType);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		ResponseEntity<Void> response = restTemplate.exchange(inventoryUrl, HttpMethod.PATCH, entity, Void.class);
		System.out.println(response);
		return isValid;
	}

	public static void main(String[] args) {
		ItemInfo item = new ItemInfo();
		item.setProductId(3l);
		item.setQty(5l);
		System.out.println(updateQuantity(item));
	}

}
