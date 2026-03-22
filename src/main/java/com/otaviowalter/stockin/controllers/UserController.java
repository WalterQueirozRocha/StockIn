package com.otaviowalter.stockin.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.dto.users.UserRegisterDTO;
import com.otaviowalter.stockin.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "User management")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/{id}")
	@Operation(summary = "Find user by ID")
	public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
		UserDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	@Operation(summary = "Find all users")
	public ResponseEntity<Page<UserDTO>> findAllUsers(Pageable pageable) {
		Page<UserDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Register a new user if the email address has not already been used.")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRegisterDTO registerUser) {

		UserDTO registeredUser = service.register(registerUser);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(registeredUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(registeredUser);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMINISTRATOR')")
	@Operation(summary = "Find all users", description = "Update your user or update other users if you are an admin.")
	public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete user by ID", description = "Delete a user by ID if you are an admin")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
