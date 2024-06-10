DELIMITER //

DROP PROCEDURE IF EXISTS lms.zdjecia_ins;
CREATE PROCEDURE zdjecia_ins(
	OUT pk_id_zdjecia INT,
	IN p_zdjecie LONGBLOB,
	IN p_ext VARCHAR(5)
)
BEGIN
	INSERT INTO zdjecia(zdjecie, ext)
	VALUES (p_zdjecie, p_ext);

	SELECT LAST_INSERT_ID() INTO pk_id_zdjecia;
END //

DELIMITER ;
