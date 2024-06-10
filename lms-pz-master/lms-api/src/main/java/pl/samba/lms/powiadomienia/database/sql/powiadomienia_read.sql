DELIMITER //

DROP PROCEDURE IF EXISTS lms.powiadomienia_read;
CREATE PROCEDURE powiadomienia_read(
	IN pk_id_powiadom INT,
	IN p_size INT,
	IN p_page INT,
	IN p_login VARCHAR(100)
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_powiadom IS NOT NULL THEN
		SELECT
			p.id_powiadom,
			p.id_odbiorcy,
			p.data_wstaw,
			p.tresc,
			p.id_flagi
		FROM lms.powiadomienia p
		WHERE p.id_powiadom = pk_id_powiadom;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;

			SELECT
				p.id_powiadom,
				p.id_odbiorcy,
				p.data_wstaw,
				p.tresc,
				p.id_flagi
			FROM lms.powiadomienia p
			JOIN lms.uzytkownicy u ON u.id_uzytk = p.id_odbiorcy
			WHERE u.login = p_login
			ORDER BY p.id_powiadom
			LIMIT p_size
			OFFSET v_offset;
	ELSE
			SELECT
				p.id_powiadom,
				p.id_odbiorcy,
				p.data_wstaw,
				p.tresc,
				p.id_flagi
			FROM lms.powiadomienia p
			JOIN lms.uzytkownicy u ON u.id_uzytk = p.id_odbiorcy
			WHERE u.login = p_login
			ORDER BY p.id_powiadom;
	END IF;
END //

DELIMITER ;
