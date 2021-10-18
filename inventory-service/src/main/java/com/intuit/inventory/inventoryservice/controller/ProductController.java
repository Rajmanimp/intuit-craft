/**
 * 
 */
package com.intuit.inventory.inventoryservice.controller;

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

import com.intuit.inventory.inventoryservice.model.ProductInfo;
import com.intuit.inventory.inventoryservice.request.Product;
import com.intuit.inventory.inventoryservice.service.ProductService;

import io.swagger.annotations.ApiOperation;

/**
 * @author rajmanip
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	private ModelMapper modelMapper = new ModelMapper();

	private static final Logger LOGGER = LogManager.getLogger(ProductController.class.getName());

	@ApiOperation(value = "Retrieve product details", response = Product.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		ProductInfo productInfo = productService.getProductDetails(id);
		Product response = convertToProduct(productInfo);
		LOGGER.info("Product with id: {} is searched.", response.getId());
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "create Product", response = Product.class)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Product createProduct(@RequestBody Product product) {
		ProductInfo productInfo = productService.createProduct(convertToProductInfo(product));
		Product response = convertToProduct(productInfo);
		return response;
	}

	@ApiOperation(value = "delete Product", response = Product.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
		ProductInfo productInfo = productService.deleteProduct(id);
		Product response = convertToProduct(productInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update Product", response = Product.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Product> deleteProduct(@RequestBody Product user) {
		ProductInfo userInfo = productService.updateProductDetails(convertToProductInfo(user));
		Product response = convertToProduct(userInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "update Product quantities", response = Product.class)
	@RequestMapping(value = "/{id}/update-qty", method = RequestMethod.PATCH)
	public ResponseEntity<Product> updateQuantity(@PathVariable Long id, Long qty) {
		ProductInfo productInfo = productService.updateProductQty(id, qty);
		Product response = convertToProduct(productInfo);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Retrieve pending product qty", response = Long.class)
	@RequestMapping(value = "/{id}/qty", method = RequestMethod.GET)
	public ResponseEntity<Long> getProductQuantity(@PathVariable Long id) {
		Long qty = productService.getProductQuantity(id);
		LOGGER.info("Product with id: {} is searched.", id);
		return ResponseEntity.ok(qty);
	}

	@ApiOperation(value = "get products by category", response = Long.class)
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByCategory(String category) {
		List<ProductInfo> products = productService.getProductFromCategory(category);
		return ResponseEntity.ok(convertToProductList(products));
	}

	@ApiOperation(value = "get products by name", response = Long.class)
	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByName(String name) {
		List<ProductInfo> products = productService.getProductFromName(name);
		return ResponseEntity.ok(convertToProductList(products));
	}

	private Product convertToProduct(ProductInfo info) {
		Product user = modelMapper.map(info, Product.class);
		return user;
	}

	private ProductInfo convertToProductInfo(Product info) {
		ProductInfo user = modelMapper.map(info, ProductInfo.class);
		return user;
	}

	private List<Product> convertToProductList(List<ProductInfo> productInfos) {
		List<Product> products = productInfos.stream().map(productInfo -> modelMapper.map(productInfo, Product.class))
				.collect(Collectors.toList());
		return products;
	}

}
