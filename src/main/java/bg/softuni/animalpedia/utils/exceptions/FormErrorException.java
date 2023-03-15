package bg.softuni.animalpedia.utils.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class FormErrorException extends RuntimeException {
    private final Map<String, String> fieldAndMessage;

    public FormErrorException(Map<String, String> fieldAndMessage) {
        this.fieldAndMessage = fieldAndMessage;
    }
}
