/**
 * 
 */
package com.intuit.user.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.user.userservice.model.UserInfo;

/**
 * @author rajmanip
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
