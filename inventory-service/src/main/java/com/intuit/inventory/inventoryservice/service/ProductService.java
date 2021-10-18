/**
 * 
 */
package com.intuit.inventory.inventoryservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.inventory.inventoryservice.model.ProductInfo;
import com.intuit.inventory.inventoryservice.repository.ProductRepository;

/**
 * @author rajmanip
 *
 */
@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ProductInfo createProduct(ProductInfo productInfo) {
		productInfo = productRepository.save(productInfo);
		return productInfo;
	}

	public ProductInfo getProductDetails(Long productId) {
		Optional<ProductInfo> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return product.get();
		} else {
			throw new NoSuchElementException("Data not found for the requested product");
		}
	}

	public List<ProductInfo> getProductFromCategory(String category) {
		List<ProductInfo> product = productRepository.findByCategoryLike("%" + category + "%");
		if (product.size() > 0) {
			return product;
		} else {
			throw new NoSuchElementException("Data not found for the requested category");
		}
	}

	public List<ProductInfo> getProductFromName(String name) {
		List<ProductInfo> product = productRepository.findByNameLike("%" + name + "%");
		if (product.size() > 0) {
			return product;
		} else {
			throw new NoSuchElementException("Data not found for the requested name");
		}
	}

	public Long getProductQuantity(Long productId) {
		Long qty = productRepository.findAvailableQty(productId);
		if (qty != null) {
			return qty;
		} else {
			throw new NoSuchElementException("Data not found for the requested product");
		}
	}

	public ProductInfo updateProductDetails(ProductInfo productInfo) {
		productInfo = productRepository.save(productInfo);
		return productInfo;
	}

	public ProductInfo updateProductQty(Long productId, Long qty) {
		ProductInfo productInfo = getProductDetails(productId);
		productInfo.setAvailableQuantity(productInfo.getAvailableQuantity() + Integer.valueOf(qty + ""));
		productInfo = productRepository.save(productInfo);
		return productInfo;
	}

	public ProductInfo deleteProduct(Long productId) {
		ProductInfo productInfo = getProductDetails(productId);
		productRepository.deleteById(productId);
		return productInfo;
	}

}
