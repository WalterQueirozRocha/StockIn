package com.otaviowalter.stockin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public Users getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return (Users) auth.getPrincipal();
	}

}
