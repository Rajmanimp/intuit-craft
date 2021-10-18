/**
 * 
 */
package com.intuit.user.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.user.userservice.model.CardInfo;

/**
 * @author rajmanip
 *
 */
public interface CardRepository extends JpaRepository<CardInfo, Long> {

}
