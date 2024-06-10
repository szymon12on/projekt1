DELIMITER //

DROP PROCEDURE IF EXISTS lms.odpowiedzi_zadania_ins;
CREATE PROCEDURE odpowiedzi_zadania_ins(
	OUT pk_id_odpowiedzi INT,
	IN p_id_zadania INT,
	IN p_id_ucznia INT,
	IN p_tresc BLOB,
	IN p_koment VARCHAR(500),
	IN p_ocena INT,
	IN p_data_oceny DATETIME
)
BEGIN
	INSERT INTO odpowiedzi_zadania(id_zadania, id_ucznia, tresc, koment, ocena, data_oceny)
	VALUES (p_id_zadania, p_id_ucznia, p_tresc, p_koment, p_ocena, p_data_oceny);

	SELECT LAST_INSERT_ID() INTO pk_id_odpowiedzi;
END //

DELIMITER ;
