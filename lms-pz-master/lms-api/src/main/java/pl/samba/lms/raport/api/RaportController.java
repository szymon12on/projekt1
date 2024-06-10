package pl.samba.lms.raport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.samba.lms.utils.http.HttpErrorResponse;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/raport")
public class RaportController {

    private final RaportService raportService;

    public RaportController(RaportService raportService) {
        this.raportService = raportService;
    }

    @GetMapping("/generuj")
    public ResponseEntity<byte[]> generujRaport(@RequestParam int idPrzedmiotu) {
        try {
            ByteArrayOutputStream pdfStream = raportService.generateRaport(idPrzedmiotu);
            byte[] pdfBytes = pdfStream.toByteArray();

            return ResponseEntity
                    .ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"raport_" + idPrzedmiotu + ".pdf\"")
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        HttpErrorResponse errorResponse = new HttpErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Nieprawidłowy format idPrzedmiotu",
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<HttpErrorResponse> handleDataAccess(DataAccessException e, HttpServletRequest request) {
        HttpErrorResponse errorResponse = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Błąd dostępu do bazy danych",
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleAll(Exception e, HttpServletRequest request) {
        HttpErrorResponse errorResponse = new HttpErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Wewnętrzny błąd serwera",
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
