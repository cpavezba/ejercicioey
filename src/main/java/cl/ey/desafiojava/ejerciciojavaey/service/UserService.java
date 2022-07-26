package cl.ey.desafiojava.ejerciciojavaey.service;

import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidParameterException;
import cl.ey.desafiojava.ejerciciojavaey.model.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserDto> addUser(UserDto user) throws InvalidParameterException;
    ResponseEntity<List<UserDto>> getUsers();
}
