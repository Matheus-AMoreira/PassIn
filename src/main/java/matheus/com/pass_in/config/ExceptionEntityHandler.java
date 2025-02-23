package matheus.com.pass_in.config;

import matheus.com.pass_in.domain.attendee.exceptions.AttendeeAlreadyExistException;
import matheus.com.pass_in.domain.chenkin.exceptions.CheckInAlreadyExistsException;
import matheus.com.pass_in.domain.event.exceptions.AttendeeNotFoundException;
import matheus.com.pass_in.domain.event.exceptions.EventFullException;
import matheus.com.pass_in.domain.event.exceptions.EventNotFoundException;
import matheus.com.pass_in.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return  ResponseEntity.ok().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFullException(EventFullException exception){
        return  ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return  ResponseEntity.ok().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExistException(AttendeeAlreadyExistException exception){
        return  ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExistsException(CheckInAlreadyExistsException exception){
        return  ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
