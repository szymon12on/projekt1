DELIMITER //

DROP PROCEDURE IF EXISTS lms.odpowiedzi_zadania_read;
CREATE PROCEDURE odpowiedzi_zadania_read(
	IN pk_id_odpowiedzi INT,
	IN p_size INT,
	IN p_page INT,
	IN p_kod VARCHAR(100),
	IN p_login VARCHAR(80)
)
BEGIN
	DECLARE v_offset  INT; 
	
	IF pk_id_odpowiedzi IS NOT NULL THEN
		SELECT
			oz.id_odpowiedzi,
			oz.id_zadania,
			oz.id_ucznia,
			oz.tresc,
			oz.koment,
			oz.ocena,
			oz.data_wstaw,
			oz.data_oceny
		FROM lms.odpowiedzi_zadania oz
		WHERE oz.id_odpowiedzi = pk_id_odpowiedzi;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;
		SELECT
			oz.id_odpowiedzi,
			oz.id_zadania,
			oz.id_ucznia,
			oz.tresc,
			oz.koment,
			oz.ocena,
			oz.data_wstaw,
			oz.data_oceny
		FROM lms.odpowiedzi_zadania oz
		ORDER BY oz.id_odpowiedzi
		LIMIT p_size
		OFFSET v_offset;
	ELSEIF p_kod IS NOT NULL AND p_login IS NOT NULL THEN
		SELECT
			oz.id_odpowiedzi,
			oz.id_zadania,
			oz.id_ucznia,
			oz.tresc,
			oz.koment,
			oz.ocena,
			oz.data_wstaw,
			oz.data_oceny
		FROM lms.odpowiedzi_zadania oz
		WHERE EXISTS(
			SELECT z.id_zadania
			FROM lms.zadania z
			JOIN lms.przedmioty p ON z.id_przedm = p.id_przedm
			WHERE z.id_zadania = oz.id_zadania
				AND p.kod = p_kod
		)
		AND EXISTS(
			SELECT *
			FROM lms.uzytkownicy u
			WHERE u.login = p_login 
			AND u.id_uzytk = oz.id_ucznia
		);
	ELSE
		SELECT
			oz.id_odpowiedzi,
			oz.id_zadania,
			oz.id_ucznia,
			oz.tresc,
			oz.koment,
			oz.ocena,
			oz.data_wstaw,
			oz.data_oceny
		FROM lms.odpowiedzi_zadania oz
		ORDER BY oz.id_odpowiedzi;
	END IF;
END //

DELIMITER ;
