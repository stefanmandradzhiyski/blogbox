package com.snmi.repository;

import com.snmi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
