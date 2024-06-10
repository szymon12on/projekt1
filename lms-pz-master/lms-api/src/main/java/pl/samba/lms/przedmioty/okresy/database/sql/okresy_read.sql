DELIMITER //

DROP PROCEDURE IF EXISTS lms.okresy_read;
CREATE PROCEDURE okresy_read(
	IN pk_id_okresu INT,
		IN p_size INT,
	IN p_page INT
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_okresu IS NOT NULL THEN
			SELECT
				o.id_okresu,
				o.kod,
				o.data_pocz,
				o.data_konc
			FROM lms.okresy o
			WHERE o.id_okresu = pk_id_okresu
			ORDER BY o.id_okresu;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;
		
			SELECT
				o.id_okresu,
				o.kod,
				o.data_pocz,
				o.data_konc
			FROM lms.okresy o
			ORDER BY o.id_okresu
			LIMIT p_size
			OFFSET v_offset;
	ELSE
			SELECT
				o.id_okresu,
				o.kod,
				o.data_pocz,
				o.data_konc
			FROM lms.okresy o
			ORDER BY o.id_okresu;
	END IF;

END //

DELIMITER ;
