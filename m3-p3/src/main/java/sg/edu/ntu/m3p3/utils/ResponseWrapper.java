package sg.edu.ntu.m3p3.utils;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ResponseWrapper<T> {
    private String requestId;
    private LocalDateTime timestamp;
    private String status;
    private String message;
    private T data;

    public ResponseWrapper() {
    }

    public ResponseWrapper(String requestId, LocalDateTime timestamp, String status, T data, String message) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.data = data;

    }

}
