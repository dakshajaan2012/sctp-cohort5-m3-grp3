package sg.edu.ntu.m3p3.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ResponseUtil {

    public <T> ResponseEntity<ResponseWrapper<T>> createSuccessResponse(T data) {
        ResponseWrapper<T> response = new ResponseWrapper<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "success",
                data,
                null);
        return ResponseEntity.ok(response);
    }

    public <T> ResponseEntity<ResponseWrapper<T>> createErrorResponse(String errorMessage, Class<T>... responseType) {
        // responseType optional
        ResponseWrapper<T> response = new ResponseWrapper<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "error",
                null,
                errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}