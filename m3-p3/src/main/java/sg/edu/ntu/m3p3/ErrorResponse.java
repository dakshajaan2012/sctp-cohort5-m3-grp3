
package sg.edu.ntu.m3p3;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    public ErrorResponse(String message) {
        
    }
    private String message;
    private LocalDateTime timestamp;

}