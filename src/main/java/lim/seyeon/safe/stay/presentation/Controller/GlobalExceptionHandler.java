package lim.seyeon.safe.stay.presentation.Controller;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(ex.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }
}
