package pl.samba.lms.utils.database;

import java.util.Map;

/**
 * Interfejs definiujący ogólbne repozytorium CRUD (Create, Read, Update, Delete).
 *
 * @param <T> Typ obiektów zarządzanych przez repozytorium.
 * @param <K> Typ unikalnego identyfikatora dla obiektów.
 * @author bsurma
 */
public interface CrudRepositoryInterface<T, K> {

    /**
     * Zapisuje dostarczone dane w repozytorium.
     *
     * @param data Dane do zapisania.
     * @return Unikalny identyfikator skojarzony z zapisanymi danymi.
     */
    public K save(T data);

    /**
     * Pobiera wszystkie obiekty z repozytorium.
     *
     * @return Kolekcja wszystkich obiektów.
     */
    public Iterable<T> getAll();
    /**
     * Pobiera obiekty z repozytorium na podstawie określonych parametrów żądania.
     *
     * @param requestParams ciąg parametrow do filtrowania obiektów.
     * @return Kolekcja wszystkich obiektów.
     */
    public Iterable<T> getAll(String requestParams);
    /**
     * Pobiera obiekt z repozytorium na podstawie jego numeru id.
     *
     * @param id Numer id obiektu do pobrania.
     * @return Obiekt skojarzony z danym identyfikatorem.
     */
    public T getById(K id);
    /**
     * Pobiera obiekt z repozytorium na podstawie jego unikalnego klucza.
     *
     * @param unique Unikalny klucz obiektu do pobrania.
     * @return Obiekt skojarzony z danym identyfikatorem.
     */
    public T getByUnique(String unique);
    /**
     * Aktualizuje dostarczone dane w repozytorium.
     *
     * @param data Dane do zaktualizowania.
     * @return Unikalny identyfikator skojarzony z zaktualizowanymi danymi.
     */
    public K update(T data);
    /**
     * Usuwa obiekt z repozytorium na podstawie jego unikalnego identyfikatora.
     *
     * @param id Unikalny identyfikator obiektu do usunięcia.
     * @return true, jeśli usunięcie było udane, false w przeciwnym razie.
     */
    public boolean delete(K id);

    /**
     * Mapuje zestaw wyników otrzymanych z bazy danych na kolekcję obiektów.
     *
     * @param resultSet Zestaw wyników otrzymanych z bazy danych.
     * @return Iterowalna kolekcja obiektów zmapowanych z zestawu wyników.
     */
    public Iterable<T> resultMapper(Iterable<Map<String, Object>> resultSet);


}