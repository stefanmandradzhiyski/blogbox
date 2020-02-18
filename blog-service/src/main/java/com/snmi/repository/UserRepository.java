package com.snmi.repository;

import com.snmi.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
@Qualifier("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDisplay(Long id, boolean display);
    Optional<User> findByUsernameAndDisplay(String username, boolean display);
    List<User> findAllByDisplay(boolean display);
    boolean existsByUsername(String username);

}
