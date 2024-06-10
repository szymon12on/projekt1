DELIMITER //
DROP PROCEDURE IF EXISTS lms.zdjecia_upd;
CREATE PROCEDURE zdjecia_upd(
	INOUT pk_id_zdjecia INT,
	IN p_zdjecie LONGBLOB,
	IN p_ext VARCHAR(5)
)
BEGIN
	UPDATE zdjecia
	SET 
		zdjecie = p_zdjecie, 
		ext = p_ext
	WHERE id_zdjecia = pk_id_zdjecia;
END //
DELIMITER ;