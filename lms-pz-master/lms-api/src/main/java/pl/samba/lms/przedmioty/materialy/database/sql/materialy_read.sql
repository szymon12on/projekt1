DELIMITER //

DROP PROCEDURE IF EXISTS lms.materialy_read;
CREATE PROCEDURE materialy_read(
	IN pk_id_mater INT,
	IN p_size INT,
	IN p_page INT,
	IN p_kod VARCHAR(100),
	IN p_lp INT
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_mater IS NOT NULL THEN
		SELECT
			m.id_mater,
			m.id_przedm,
			m.data_wstaw,
			m.lp,
			m.temat,
			m.plik,
			m.nazwa_pliku,
			m.ext,
			m.opis,
			m.id_zadania,
			m.widocznosc
		FROM lms.materialy m
		WHERE m.id_mater = pk_id_mater;
	ELSEIF p_kod IS NOT NULL AND p_lp IS NOT NULL THEN
		SELECT
			m.id_mater,
			m.id_przedm,
			m.data_wstaw,
			m.lp,
			m.temat,
			m.plik,
			m.nazwa_pliku,
			m.ext,
			m.opis,
			m.id_zadania,
			m.widocznosc
		FROM lms.materialy m
		JOIN lms.przedmioty p ON m.id_przedm = p.id_przedm
		WHERE p.kod = p_kod
			AND m.lp = p_lp;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;

			SELECT
				m.id_mater,
				m.id_przedm,
				m.data_wstaw,
				m.lp,
				m.temat,
				m.plik,
				m.nazwa_pliku,
				m.ext,
				m.opis,
				m.id_zadania,
				m.widocznosc
			FROM lms.materialy m
			JOIN lms.przedmioty p ON m.id_przedm = p.id_przedm
			WHERE p.kod = p_kod
			ORDER BY m.lp
			LIMIT p_size
			OFFSET v_offset;
	ELSE
			SELECT
				m.id_mater,
				m.id_przedm,
				m.data_wstaw,
				m.lp,
				m.temat,
				m.plik,
				m.nazwa_pliku,
				m.ext,
				m.opis,
				m.id_zadania,
				m.widocznosc
			FROM lms.materialy m
			JOIN lms.przedmioty p ON m.id_przedm = p.id_przedm
			WHERE p.kod = p_kod
			ORDER BY m.lp;
	END IF;
END //

DELIMITER ;
