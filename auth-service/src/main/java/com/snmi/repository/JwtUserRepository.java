package com.snmi.repository;

import com.snmi.model.JwtUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JwtUserRepository which only purpose is to find an active user by username
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
@Qualifier("JwtUserRepository")
public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {

    Optional<JwtUser> findByUsername(String username);
    Optional<JwtUser> findByUsernameAndDisplay(String username, boolean display);

}
