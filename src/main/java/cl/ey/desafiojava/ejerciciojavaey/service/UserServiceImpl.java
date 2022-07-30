package cl.ey.desafiojava.ejerciciojavaey.service;

import cl.ey.desafiojava.ejerciciojavaey.entity.Phone;
import cl.ey.desafiojava.ejerciciojavaey.entity.User;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidEmailException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidParameterException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidPasswordException;
import cl.ey.desafiojava.ejerciciojavaey.model.PhoneDto;
import cl.ey.desafiojava.ejerciciojavaey.model.UserDto;
import cl.ey.desafiojava.ejerciciojavaey.repository.PhoneRepository;
import cl.ey.desafiojava.ejerciciojavaey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public ResponseEntity<UserDto> addUser(UserDto userDto)
            throws InvalidParameterException, InvalidEmailException, InvalidPasswordException {

        validateParams(userDto);

        Date now = new Date();

        User user = userRepository.saveAndFlush(User.builder()
                .created(now)
                .modified(now)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .isActive(true)
                .lastLogin(now)
                .token(UUID.randomUUID().toString())
                .build());

        Set<Phone> phones = userDto.getPhones().stream().map(phone -> {
            return phoneRepository.save(Phone.builder()
                    .user(User.builder().id(user.getId()).build())
                    .number(phone.getNumber())
                    .citycode(phone.getCitycode())
                    .countrycode(phone.getCountrycode()).build());
        }).collect(Collectors.toSet());

        return new ResponseEntity<UserDto>(createResponse(user, phones), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {

        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = users.stream().map( user -> {
            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .created(user.getCreated())
                    .modified(user.getModified())
                    .lastLogin(user.getLastLogin())
                    .token(user.getToken())
                    .isActive(user.getIsActive())
                    .phones(phoneRepository.findByUserId(user.getId()).stream().map(this::parseDto).collect(Collectors.toSet()))
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    private void validateParams(UserDto request) throws InvalidParameterException, InvalidEmailException, InvalidPasswordException {
        List<String> errors = new ArrayList<>();

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("El correo es requerido");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("La password es requerida");
        }

        if (!errors.isEmpty()) {
            throw new InvalidParameterException(String.join(",", errors));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new InvalidParameterException("El correo ya registrado");
        }

        if (!request.getEmail().matches("^[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+$")) {
            throw new InvalidEmailException("El correo no cumple el formato");
        }

        if (!request.getPassword().matches("^[A-Z]{1}[a-z]+[0-9]{2}$")) {
            throw new InvalidPasswordException("La password no cumple el formato");
        }

    }

    private UserDto createResponse(User user, Set<Phone> phones) {
        return UserDto.builder()
                .id(user.getId())
                .created(user.getCreated())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .lastLogin(user.getLastLogin())
                .modified(user.getModified())
                .name(user.getName())
                .password(user.getPassword())
                .token(user.getToken())
                .phones(phones.stream().map(this::parseDto).collect(Collectors.toSet()))
                .build();
    }

    private PhoneDto parseDto(Phone in) {
        return PhoneDto.builder()
                .id(in.getId())
                .number(in.getNumber())
                .countrycode(in.getCountrycode())
                .citycode(in.getCitycode())
                .build();
    }

}
