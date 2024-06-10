DELIMITER //
DROP PROCEDURE IF EXISTS lms.odpowiedzi_zadania_upd;
CREATE PROCEDURE odpowiedzi_zadania_upd(
	INOUT pk_id_odpowiedzi INT,
	IN p_tresc BLOB,
	IN p_koment VARCHAR(500),
	IN p_ocena INT,
	IN p_data_oceny DATETIME
)
BEGIN
	UPDATE odpowiedzi_zadania
	SET  tresc = p_tresc, koment = p_koment, ocena = p_ocena, data_oceny = p_data_oceny
	WHERE id_odpowiedzi = pk_id_odpowiedzi;
END //
DELIMITER ;