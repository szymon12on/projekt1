package pl.samba.lms.utils.api.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.samba.lms.utils.http.HttpErrorResponse;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestApiExHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wystąpił błąd.",
                ex.getMessage(),
                request.getContextPath()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.NOT_FOUND,
                "Wystąpił błąd braku danych.",
                ex.getMessage(),
                request.getContextPath()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.NOT_FOUND,
                "Błędny login lub hasło.",
                ex.getMessage(),
                request.getContextPath()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicateKeyException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wystąpił błąd powtórzenia danych dla klucza unikalnego.",
                ex.getMessage(),
                request.getContextPath()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public ResponseEntity<Object> handleUncategorizedSQLException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wystąpił błąd SQL.",
                ex.getMessage(),
                request.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wystąpił błąd danych podczas wykonywania procedury składowanej SQL.",
                ex.getMessage(),
                request.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<Object> handleBadSqlGrammarException(Exception ex, WebRequest request) {
        HttpErrorResponse response = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wystąpił błąd podczas wykonywania procedury składowanej SQL.",
                ex.getMessage(), request.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
