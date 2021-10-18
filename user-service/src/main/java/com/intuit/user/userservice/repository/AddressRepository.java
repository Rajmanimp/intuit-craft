/**
 * 
 */
package com.intuit.user.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.user.userservice.model.AddressInfo;

/**
 * @author rajmanip
 *
 */
public interface AddressRepository extends JpaRepository<AddressInfo, Long> {

}
