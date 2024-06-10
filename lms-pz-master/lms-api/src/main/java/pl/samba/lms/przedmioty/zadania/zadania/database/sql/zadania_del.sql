DELIMITER //
DROP PROCEDURE IF EXISTS lms.zadania_del;
CREATE PROCEDURE zadania_del(
	IN pk_id_zadania INT
)
BEGIN
	DELETE FROM zadania
	WHERE id_zadania = pk_id_zadania;
END //

DELIMITER ;