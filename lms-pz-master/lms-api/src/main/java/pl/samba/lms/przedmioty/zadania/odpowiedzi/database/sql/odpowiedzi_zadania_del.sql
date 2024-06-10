DELIMITER //
DROP PROCEDURE IF EXISTS lms.odpowiedzi_zadania_del;
CREATE PROCEDURE odpowiedzi_zadania_del(
	IN pk_id_odpowiedzi INT
)
BEGIN
	DELETE FROM odpowiedzi_zadania
	WHERE id_odpowiedzi = pk_id_odpowiedzi;
END //

DELIMITER ;