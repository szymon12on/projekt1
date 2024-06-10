DELIMITER //

DROP PROCEDURE IF EXISTS lms.okresy_ins;
CREATE PROCEDURE okresy_ins(
	OUT pk_id_okresu INT,
	IN p_kod VARCHAR(10),
	IN p_data_pocz DATETIME,
	IN p_data_konc DATETIME
)
BEGIN
	INSERT INTO okresy(kod, data_pocz, data_konc)
	VALUES (p_kod, p_data_pocz, p_data_konc);

	SELECT LAST_INSERT_ID() INTO pk_id_okresu;
END //

DELIMITER ;
