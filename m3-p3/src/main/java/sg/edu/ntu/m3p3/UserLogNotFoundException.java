package sg.edu.ntu.m3p3;

import java.util.UUID;

public class UserLogNotFoundException extends RuntimeException {
    public UserLogNotFoundException(UUID id) {
        super("Could not find interaction with id: " + id);
    }
}
