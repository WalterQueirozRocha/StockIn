package com.otaviowalter.stockin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otaviowalter.stockin.dto.users.UserAuthenticationDTO;
import com.otaviowalter.stockin.dto.users.UserLoginResponseDTO;
import com.otaviowalter.stockin.infra.security.TokenService;
import com.otaviowalter.stockin.model.Users;

import jakarta.validation.Valid;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

	@Autowired
	TokenService tokenService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserAuthenticationDTO dto) {
		var emailPassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
		var auth = this.authenticationManager.authenticate(emailPassword);
		
		var token = tokenService.generateToken((Users)auth.getPrincipal());
		
		return ResponseEntity.ok(new UserLoginResponseDTO(token));
	}

}
