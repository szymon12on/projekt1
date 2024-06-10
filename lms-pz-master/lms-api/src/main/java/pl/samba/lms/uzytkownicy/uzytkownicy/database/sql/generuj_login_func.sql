DELIMITER //
DROP FUNCTION IF EXISTS lms.generuj_login_func;
CREATE FUNCTION generuj_login_func(p_imie VARCHAR(40), p_nazwisko VARCHAR(40))
RETURNS VARCHAR(80) DETERMINISTIC
BEGIN
    DECLARE v_login VARCHAR(80);
    DECLARE v_nr INT;

    SET v_login = CONCAT_WS('.',LOWER(SUBSTRING(p_imie, 1, 1)), LOWER(p_nazwisko));

    IF (
        SELECT COUNT(*) 
        FROM uzytkownicy 
        WHERE login = v_login
    ) > 0 THEN
        SET v_nr = (
            SELECT COUNT(id_uzytk) + 1
            FROM uzytkownicy 
            WHERE login LIKE CONCAT(v_login, '%')
        );
        SET v_login = CONCAT(v_login, v_nr);
    END IF;

    RETURN v_login;
END //

DELIMITER ;
