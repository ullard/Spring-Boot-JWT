package com.demo.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.jwt.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
	Role findByName(String userRole);
}