DELIMITER //
DROP PROCEDURE IF EXISTS lms.przedmioty_del;
CREATE PROCEDURE przedmioty_del(
	IN pk_id_przedm INT
)
BEGIN
	DELETE FROM przedmioty
	WHERE id_przedm = pk_id_przedm;
END //

DELIMITER ;