DELIMITER //

DROP PROCEDURE IF EXISTS lms.przedmioty_read;
CREATE PROCEDURE przedmioty_read(
	IN pk_id_przedm INT,
	IN p_size INT,
	IN p_page INT,
	IN p_unique VARCHAR(100),
	IN p_id_ucznia INT
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_przedm IS NOT NULL THEN
			SELECT
				p.id_przedm,
				p.kod,
				p.nazwa,
				p.id_prow,
				p.limit_miejsc,
				p.opis,
				p.war_zalicz,
				p.id_okresu,
				ps.kod AS 'kod_status',
				p.rejestr_uczn
			FROM lms.przedmioty p
			JOIN przedmiot_status ps ON p.id_status = ps.id_status
			WHERE p.id_przedm = pk_id_przedm;
	ELSEIF p_id_ucznia IS NOT NULL THEN
			SELECT
				p.id_przedm,
				p.kod,
				p.nazwa,
				p.id_prow,
				p.limit_miejsc,
				p.opis,
				p.war_zalicz,
				p.id_okresu,
				ps.kod AS 'kod_status',
				p.rejestr_uczn
			FROM lms.uczen_przedmiot up 
			JOIN lms.przedmioty p ON p.id_przedm = up.id_przedm 
			JOIN lms.przedmiot_status ps ON p.id_status = ps.id_status
			WHERE up.id_ucznia = p_id_ucznia;
	ELSEIF p_unique IS NOT NULL THEN
			SELECT
				p.id_przedm,
				p.kod,
				p.nazwa,
				p.id_prow,
				p.limit_miejsc,
				p.opis,
				p.war_zalicz,
				p.id_okresu,
				ps.kod AS 'kod_status',
				p.rejestr_uczn
			FROM lms.przedmioty p
			JOIN przedmiot_status ps ON p.id_status = ps.id_status
			WHERE p.kod = p_unique;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;
		
		SELECT
				p.id_przedm,
				p.kod,
				p.nazwa,
				p.id_prow,
				p.limit_miejsc,
				p.opis,
				p.war_zalicz,
				p.id_okresu,
				ps.kod AS 'kod_status',
				p.rejestr_uczn
			FROM lms.przedmioty p
			JOIN przedmiot_status ps ON p.id_status = ps.id_status
			ORDER BY p.kod
			LIMIT p_size
			OFFSET v_offset;
	ELSE
			SELECT
				p.id_przedm,
				p.kod,
				p.nazwa,
				p.id_prow,
				p.limit_miejsc,
				p.opis,
				p.war_zalicz,
				p.id_okresu,
				ps.kod AS 'kod_status',
				p.rejestr_uczn
			FROM lms.przedmioty p
			JOIN przedmiot_status ps ON p.id_status = ps.id_status
			ORDER BY p.kod;
	END IF;
END //

DELIMITER ;
