DELIMITER //

DROP PROCEDURE IF EXISTS lms.uczen_przedmiot_read;
CREATE PROCEDURE uczen_przedmiot_read(
	IN pk_id_encji INT,
	IN p_id_przedm INT,
	IN p_id_ucznia INT,
	IN p_size INT,
	IN p_page INT
)
BEGIN
	DECLARE v_offset  INT;
	
	IF pk_id_encji IS NOT NULL THEN
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up
		WHERE up.id_encji = pk_id_encji;
	ELSEIF p_id_przedm IS NOT NULL AND p_id_ucznia IS NOT NULL THEN
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up
		WHERE up.id_przedm = p_id_przedm
		AND up.id_ucznia = p_id_ucznia
		ORDER BY up.id_przedm;
	ELSEIF p_id_przedm IS NOT NULL THEN
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up
		WHERE up.id_przedm = p_id_przedm
		ORDER BY up.id_ucznia;
	ELSEIF p_id_ucznia IS NOT NULL THEN
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up
		WHERE up.id_ucznia = p_id_ucznia;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;
		
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up
		ORDER BY up.id_oceny
		LIMIT p_size
		OFFSET v_offset;
	ELSE
		SELECT
			up.id_encji,
			up.id_przedm,
			up.id_ucznia,
			up.ocena,
			up.data_wystaw_oc
		FROM lms.uczen_przedmiot up;
	END IF;
END //

DELIMITER ;
