DELIMITER //
DROP PROCEDURE IF EXISTS lms.okresy_upd;
CREATE PROCEDURE okresy_upd(
	INOUT pk_id_okresu INT,
	IN p_kod VARCHAR(10),
	IN p_data_pocz DATETIME,
	IN p_data_konc DATETIME
)
BEGIN
	UPDATE okresy
	SET kod = p_kod, data_pocz = p_data_pocz, data_konc = p_data_konc
	WHERE id_okresu = pk_id_okresu;

END //
DELIMITER ;