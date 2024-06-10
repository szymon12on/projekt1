DELIMITER //
DROP PROCEDURE IF EXISTS lms.zdjecia_del;
CREATE PROCEDURE zdjecia_del(
	IN pk_id_zdjecia INT
)
BEGIN
	DELETE FROM zdjecia
	WHERE id_zdjecia = pk_id_zdjecia;
END //

DELIMITER ;