package com.proyectoNeo01.app.service;
/**
 * Capa de Servicio Usuarios
 * @author Tomas Mejias
 */
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectoNeo01.app.entity.User;
import com.proyectoNeo01.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Devuelve un iterable de todos los usuarios dentro de la base de datos
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<User> findAll() {
		
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) {
		
		userRepository.deleteById(id);
	}

}
