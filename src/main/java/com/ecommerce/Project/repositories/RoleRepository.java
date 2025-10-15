package com.ecommerce.Project.repositories;

import com.ecommerce.Project.model.AppRole;
import com.ecommerce.Project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
