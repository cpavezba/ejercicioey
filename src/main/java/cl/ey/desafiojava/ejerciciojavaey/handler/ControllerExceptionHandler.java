package cl.ey.desafiojava.ejerciciojavaey.handler;

import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidEmailException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidParameterException;
import cl.ey.desafiojava.ejerciciojavaey.exception.InvalidPasswordException;
import cl.ey.desafiojava.ejerciciojavaey.model.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidParameterException.class, InvalidPasswordException.class, InvalidEmailException.class})
    protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(MessageResponse.builder().message(ex.getMessage()).build());
    }

}
