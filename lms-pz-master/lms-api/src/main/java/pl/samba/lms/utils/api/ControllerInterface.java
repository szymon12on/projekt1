package pl.samba.lms.utils.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfejs kontrolera REST API, obsługującego operacje na zasobach danego typu.
 *
 * @param <T> Typ obiektu DTO (Data Transfer Object) używany w interfejsie.
 * @param <K> Model obiektu, który jest wynikiem operacji w kontrolerze.
 * @author bsurma
 */
public interface ControllerInterface<T, K> {

    /**
     * Metoda do pobierania zasobów z uwzględnieniem parametrów takich jak sortowanie, rozmiar strony itp.
     *
     * @param size      Liczba wyników na stronie.
     * @param page      Numer strony, liczony od 0.
     * @return Odpowiedź HTTP zawierająca kolekcję modelu zasobów.
     */
    ResponseEntity<CollectionModel<K>> get(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) throws Exception;

    /**
     * Metoda do pobierania pojedynczego zasobu na podstawie jego identyfikatora.
     *
     * @param id Identyfikator zasobu.
     * @return Odpowiedź HTTP zawierająca model zasobu o określonym identyfikatorze.
     */
    ResponseEntity<K> get(@PathVariable("id") Integer id) throws Exception;
    /**
     * Metoda do usuwania zasobu na podstawie jego identyfikatora.
     *
     * @param id Identyfikator zasobu do usunięcia.
     */
    void delete(@PathVariable("id") Integer id) throws Exception;

    /**
     * Metoda do dodawania nowego zasobu.
     *
     * @param data Dane zasobu do dodania.
     * @return Odpowiedź HTTP zawierająca dodany zasób.
     */
    ResponseEntity<Object> post(@RequestBody T data) throws Exception;

    /**
     * Metoda do częściowej aktualizacji zasobu na podstawie identyfikatora i dostarczonych danych.
     *
     * @param id   Identyfikator zasobu do aktualizacji.
     * @param data Dane zasobu do częściowej aktualizacji.
     * @return Zaktualizowany zasób.
     */
    ResponseEntity<Object> patch(
            @PathVariable("id") Integer id,
            @RequestBody T data) throws Exception;
}
