/**
 * 
 */
package com.intuit.inventory.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.inventory.orderservice.model.ItemInfo;

/**
 * @author rajmanip
 *
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemInfo, Long> {

}
