DELIMITER //
DROP PROCEDURE IF EXISTS lms.zadania_upd;
CREATE PROCEDURE zadania_upd(
	INOUT pk_id_zadania INT,
	IN p_id_przedm INT,
	IN p_opis VARCHAR(1000),
	IN p_data_pocz DATETIME,
	IN p_data_konc DATETIME,
	IN p_tresc BLOB,
	IN p_id_typu INT
)
BEGIN
	UPDATE zadania z
	SET 
		id_przedm = p_id_przedm, 
		opis = p_opis,
		data_pocz = p_data_pocz, 
		data_konc = p_data_konc, 
		tresc = p_tresc,
		id_typu = p_id_typu
	WHERE id_zadania = pk_id_zadania;
END //
DELIMITER ;