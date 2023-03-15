package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.utils.exceptions.FormErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class FormErrorRestAdvice {

    @ExceptionHandler(FormErrorException.class)
    public ResponseEntity<Map<String, String>> onFormError(FormErrorException fee) {
        return ResponseEntity.badRequest().body(fee.getFieldAndMessage());
    }
}
