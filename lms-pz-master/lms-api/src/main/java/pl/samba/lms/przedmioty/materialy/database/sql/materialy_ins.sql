DELIMITER //

DROP PROCEDURE IF EXISTS lms.materialy_ins;
CREATE PROCEDURE materialy_ins(
	OUT pk_id_mater INT,
	IN p_id_przedm INT,
	IN p_temat VARCHAR(200),
	IN p_plik LONGBLOB,
	IN p_nazwa_pliku VARCHAR(80),
	IN p_ext VARCHAR(5),
	IN p_opis VARCHAR(500),
	IN p_id_zadania INT,
	IN p_widocznosc BOOLEAN
)
BEGIN
	DECLARE z_lp INT;
	SET z_lp = next_lp_material_func(p_id_przedm);

	INSERT INTO materialy(
		id_przedm, 
		lp, 
		temat, 
		plik, 
		nazwa_pliku, 
		ext, 
		opis,
		id_zadania, 
		widocznosc
		)
	VALUES (
		p_id_przedm, 
		z_lp, 
		p_temat, 
		p_plik, 
		p_nazwa_pliku, 
		p_ext,  
		p_opis,
		p_id_zadania,
		p_widocznosc
		);

	SELECT LAST_INSERT_ID() INTO pk_id_mater;
END //

DELIMITER ;
