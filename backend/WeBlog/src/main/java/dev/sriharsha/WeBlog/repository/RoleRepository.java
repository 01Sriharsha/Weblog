package dev.sriharsha.WeBlog.repository;

import dev.sriharsha.WeBlog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}