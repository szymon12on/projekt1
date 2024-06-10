DELIMITER //
DROP PROCEDURE IF EXISTS uzytkownicy_del;
CREATE PROCEDURE uzytkownicy_del(
	IN pk_id_uzytk INT
)
BEGIN
	DECLARE p_id_zdjecia INT;
    
	SELECT u.id_zdjecia INTO p_id_zdjecia
	FROM uzytkownicy u
	WHERE u.id_uzytk = pk_id_uzytk;
	
	DELETE FROM uzytkownicy
	WHERE id_uzytk = pk_id_uzytk;
	
	IF p_id_zdjecia IS NOT NULL AND p_id_zdjecia != 1 THEN
		CALL lms.zdjecia_del(p_id_zdjecia);
	END IF;
END //
DELIMITER ;