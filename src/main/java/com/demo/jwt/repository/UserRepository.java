package com.demo.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.jwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	@Query(value = "SELECT * FROM users u WHERE u.username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);
}
