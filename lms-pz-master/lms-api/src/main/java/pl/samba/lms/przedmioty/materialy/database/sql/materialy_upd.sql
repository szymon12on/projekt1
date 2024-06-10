DELIMITER //
DROP PROCEDURE IF EXISTS lms.materialy_upd;
CREATE PROCEDURE materialy_upd(
	INOUT pk_id_mater INT,
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
	UPDATE materialy
	SET 
		id_przedm = p_id_przedm,  
		temat = p_temat, 
		plik = p_plik,
		nazwa_pliku = p_nazwa_pliku,
		ext = p_ext,
		opis = p_opis,
		id_zadania = p_id_zadania,
		widocznosc = p_widocznosc
	WHERE id_mater = pk_id_mater;
END //
DELIMITER ;