package com.proyectoNeo01.app.controller;
/**
 * Controlador Usuario
 * @author Tomas Mejias
 */
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.List;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoNeo01.app.entity.User;
import com.proyectoNeo01.app.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * Crea un usuario reciviendo como parametro un objeto de tipo Usuario
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
	}
	
	/**
	 * Obtiene un objeto de tipo Usuario segun un Id valido
	 * @param userId
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable(value = "id") Long userId){
		Optional<User> oUser = userService.findById(userId);
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oUser);
	}
	
	/**
	 * Actualiza un usuario segun un Id valido
	 * @param userDetails
	 * @param userId
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> update (@RequestBody User userDetails,@PathVariable(value = "id") Long userId){
		Optional<User> oUser = userService.findById(userId);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		// BeanUtils.copyProperties(userDetails, oUser);
		
		oUser.get().setName(userDetails.getName());
		oUser.get().setSurname(userDetails.getSurname());
		oUser.get().setEnabled(userDetails.getEnabled());
		oUser.get().setEmail(userDetails.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(oUser.get()));
	}
	
	/**
	 * Elimina un usuario segun un Id valido
	 * @param userId
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable (value = "id") Long userId){
		
		if(!userService.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteUserById(userId);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Obtiene todos los usuarios dentro de la base de datos
	 * @return
	 */
	@GetMapping
	public List<User> readAll(){
		
		List<User> users = StreamSupport
				.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return users;
	}
}
