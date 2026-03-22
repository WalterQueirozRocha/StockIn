package com.otaviowalter.stockin.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.users.UpdatePasswordDTO;
import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.dto.users.UserRegisterDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UsersRepository userRepository;

	@Transactional(readOnly = true)
	public UserDTO findById(UUID id) {
		Users entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		return new UserDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(Pageable pageable) {
		try {
			Page<Users> pages = userRepository.findAll(pageable);
			return pages.map((user) -> new UserDTO(user));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Users Not Found");
		}
	}

	@Transactional
	public UserDTO register(UserRegisterDTO newUser) {

		if (this.userRepository.findByEmail(newUser.getName()) != null) {
			throw new RuntimeException("User already registered");
		}

		Users user = new Users();
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setRole(newUser.getRole());

		String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
		user.setPassword(encryptedPassword);

		user.setAdminition(new Date());

		Users savedUser = userRepository.save(user);
		return new UserDTO(savedUser);
	}

	@Transactional
	public UserDTO update(UUID id, UserDTO dto) {
		try {
			Users entity = userRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setEmail(dto.getEmail());
			entity.setRole(dto.getRole());
			entity = userRepository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("User Not Found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		try {
			userRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("User Not Found");
		}
	}

	@Transactional
	public void updatePassword(String email, UpdatePasswordDTO dto) {
		Users entity = (Users) userRepository.findByEmail(email);
		if (!new BCryptPasswordEncoder().matches(dto.getOldPassword(), entity.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getNewPassword());
		entity.setPassword(encryptedPassword);
		entity = userRepository.save(entity);
	}

}
