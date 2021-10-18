/**
 * 
 */
package com.intuit.inventory.inventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intuit.inventory.inventoryservice.model.ProductInfo;

/**
 * @author rajmanip
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductInfo, Long> {

	@Query(value = "SELECT available_quantity FROM product_info p WHERE p.id = ?1", nativeQuery = true)
	public Long findAvailableQty(Long id);

	public List<ProductInfo> findByCategoryLike(String category);

	public List<ProductInfo> findByNameLike(String name);

	public List<ProductInfo> findByTagsLike(String tags);

}
