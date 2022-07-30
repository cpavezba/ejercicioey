package cl.ey.desafiojava.ejerciciojavaey.service;

import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidEmailException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidParameterException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidPasswordException;
import cl.ey.desafiojava.ejerciciojavaey.model.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserDto> addUser(UserDto user) throws InvalidParameterException, InvalidEmailException, InvalidPasswordException;
    ResponseEntity<List<UserDto>> getUsers();
}
