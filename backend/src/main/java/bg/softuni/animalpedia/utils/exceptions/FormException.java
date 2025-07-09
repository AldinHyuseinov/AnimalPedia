package bg.softuni.animalpedia.utils.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class FormException extends RuntimeException {
    private final Map<String, String> fieldAndMessage;

    public FormException(Map<String, String> fieldAndMessage) {
        this.fieldAndMessage = fieldAndMessage;
    }
}
