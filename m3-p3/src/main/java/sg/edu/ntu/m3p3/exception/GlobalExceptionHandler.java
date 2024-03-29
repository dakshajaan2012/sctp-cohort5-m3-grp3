
package sg.edu.ntu.m3p3.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sg.edu.ntu.m3p3.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Extract error message from MethodArgumentNotValidException
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        // Construct a ResponseEntity with the error message and BAD_REQUEST status
        ErrorResponse errorResponse = new ErrorResponse(errorMessage,
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}