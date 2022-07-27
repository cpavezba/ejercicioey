package cl.ey.desafiojava.ejerciciojavaey;

import cl.ey.desafiojava.ejerciciojavaey.entity.Phone;
import cl.ey.desafiojava.ejerciciojavaey.entity.User;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidParameterException;
import cl.ey.desafiojava.ejerciciojavaey.model.PhoneDto;
import cl.ey.desafiojava.ejerciciojavaey.model.UserDto;
import cl.ey.desafiojava.ejerciciojavaey.repository.PhoneRepository;
import cl.ey.desafiojava.ejerciciojavaey.repository.UserRepository;
import cl.ey.desafiojava.ejerciciojavaey.service.UserServiceImpl;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository userRepository;

    @Mock
    PhoneRepository phoneRepository;

    @Before
    public void inicializaMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void creaUsuarioTestOK() throws InvalidParameterException {

        Set<PhoneDto> phones = new HashSet<>();
        phones.add(PhoneDto.builder().number(97179673).citycode(9).countrycode(56).build());
        phones.add(PhoneDto.builder().number(97197534).citycode(9).countrycode(56).build());

        UserDto req = UserDto.builder()
                                    .name("Claudio Pavez")
                                    .email("cpavezba@gmail.com")
                                    .password("Admin88")
                                    .phones(phones)
                                    .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        when(userRepository.saveAndFlush(any(User.class)))
                .thenReturn(User.builder().id(UUID.randomUUID()).isActive(true).build());

        when(phoneRepository.save(any(Phone.class))).thenReturn(Phone.builder().build());

        ResponseEntity<UserDto> resp = service.addUser(req);

        assertNotNull(resp);
        assertNotNull(resp.getBody().getId());
        assertEquals(resp.getBody().getIsActive(), true);

    }


    @Test
    public void creaUsuarioCorreoExiste(){

        Set<PhoneDto> phones = new HashSet<>();
        phones.add(PhoneDto.builder().number(97179673).citycode(9).countrycode(56).build());

        UserDto req = UserDto.builder()
                .name("Claudio Pavez")
                .email("cpavezba@gmail.com")
                .password("Admin88")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.of(User.builder().email("cpavezba@gmail.com").build());
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () ->{
            service.addUser(req);
        });
        assertEquals(ex.getMessage(), "El correo ya registrado");
    }

    @Test
    public void creaUsuarioErrorFormatoCorreo()  {

        Set<PhoneDto> phones = new HashSet<>();
        phones.add(PhoneDto.builder().number(97179673).citycode(9).countrycode(56).build());

        UserDto req = UserDto.builder()
                .name("Claudio Pavez")
                .email("cpavezba@gmail")
                .password("Admin88")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () ->{
            service.addUser(req);
        });
        assertEquals(ex.getMessage(), "El correo no cumple el formato");
    }

    @Test
    public void creaUsuarioErrorFormatoPassword() {

        Set<PhoneDto> phones = new HashSet<>();
        phones.add(PhoneDto.builder().number(97179673).citycode(9).countrycode(56).build());

        UserDto req = UserDto.builder()
                .name("Claudio Pavez")
                .email("cpavezba@gmail.com")
                .password("1234")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () ->{
            service.addUser(req);
        });
        assertEquals(ex.getMessage(), "La password no cumple el formato");
    }

}
