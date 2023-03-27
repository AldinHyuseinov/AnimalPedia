package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.utils.exceptions.FormException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestAdvice {

    @ExceptionHandler(FormException.class)
    public ResponseEntity<Map<String, String>> onFormError(FormException fe) {
        return ResponseEntity.badRequest().body(fe.getFieldAndMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> onNotFound(NoSuchElementException nse) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nse.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> onFailedOperations(UnsupportedOperationException ooe) {
        return ResponseEntity.badRequest().body(ooe.getMessage());
    }
}
