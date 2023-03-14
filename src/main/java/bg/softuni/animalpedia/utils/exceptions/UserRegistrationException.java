package bg.softuni.animalpedia.utils.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserRegistrationException extends RuntimeException {
    private final Map<String, String> fieldAndMessage;

    public UserRegistrationException(Map<String, String> fieldAndMessage) {
        this.fieldAndMessage = fieldAndMessage;
    }
}
