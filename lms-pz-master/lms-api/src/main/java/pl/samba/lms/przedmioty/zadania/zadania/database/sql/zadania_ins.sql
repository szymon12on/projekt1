DELIMITER //

DROP PROCEDURE IF EXISTS lms.zadania_ins;
CREATE PROCEDURE zadania_ins(
	OUT pk_id_zadania INT,
	IN p_id_przedm INT,
	IN p_opis VARCHAR(1000),
	IN p_data_pocz DATETIME,
	IN p_data_konc DATETIME,
	IN p_tresc BLOB,
	IN p_id_typu INT
)
BEGIN
	INSERT INTO zadania(id_przedm,opis, data_pocz, data_konc, tresc, id_typu)
	VALUES (p_id_przedm,p_opis, p_data_pocz, p_data_konc, p_tresc, p_id_typu);

	SELECT LAST_INSERT_ID() INTO pk_id_zadania;
END //

DELIMITER ;
