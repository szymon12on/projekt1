DELIMITER //

DROP PROCEDURE IF EXISTS lms.zdjecia_read;
CREATE PROCEDURE zdjecia_read(
	IN pk_id_zdjecia INT
)
BEGIN
	IF pk_id_zdjecia IS NOT NULL THEN
			SELECT
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.zdjecia z
			WHERE u.id_zdjecia = pk_id_zdjecia;
	ELSE
			SELECT
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.zdjecia z
			WHERE u.id_zdjecia = pk_id_zdjecia
			ORDER BY u.id_zdjecia;
	END IF;
END //

DELIMITER ;