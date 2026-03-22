package com.otaviowalter.stockin.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.otaviowalter.stockin.model.Users;

public interface UsersRepository extends JpaRepository<Users, UUID> {
	UserDetails findByEmail(String email);
}
