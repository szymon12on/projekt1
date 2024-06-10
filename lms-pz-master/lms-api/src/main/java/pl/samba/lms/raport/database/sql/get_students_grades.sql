CREATE DEFINER=`lms_admin`@`%` PROCEDURE `get_students_grades`(IN idPrzedmiotu INT)
BEGIN
    SELECT 
        p.nazwa AS nazwaPrzedmiotu, 
        p.kod AS kodPrzedmiotu, 
        u.imie, 
        u.nazwisko,
        COALESCE(GROUP_CONCAT(DISTINCT oz.ocena ORDER BY oz.ocena SEPARATOR ', '), '') AS ocenyCzastkowe,
        up.ocena AS ocenaKoncowa
    FROM przedmioty p
    INNER JOIN uczen_przedmiot up ON p.id_przedm = up.id_przedm
    INNER JOIN uzytkownicy u ON up.id_ucznia = u.id_uzytk
    LEFT JOIN odpowiedzi_zadania oz ON up.id_ucznia = oz.id_ucznia
    LEFT JOIN zadania z ON oz.id_zadania = z.id_zadania AND z.id_przedm = p.id_przedm
    WHERE p.id_przedm = idPrzedmiotu
    GROUP BY u.id_uzytk, p.id_przedm
    ORDER BY u.nazwisko, u.imie;
END