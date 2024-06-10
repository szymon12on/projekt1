DELIMITER //
DROP PROCEDURE IF EXISTS lms.okresy_del;
CREATE PROCEDURE okresy_del(
	IN pk_id_okresu INT
)
BEGIN
	DELETE FROM okresy
	WHERE id_okresu = pk_id_okresu;
END //
DELIMITER ;