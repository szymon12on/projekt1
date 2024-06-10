DELIMITER //

DROP PROCEDURE IF EXISTS lms.uzytkownicy_read;
CREATE PROCEDURE uzytkownicy_read(
	IN pk_id_uzytk INT,
	IN p_size INT,
	IN p_page INT,
	IN p_unique VARCHAR(100)
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_uzytk IS NOT NULL THEN
			SELECT
				u.id_uzytk,
				u.imie,
				u.nazwisko,
				u.tyt_nauk,
				u.login,
				u.haslo,
				u.email,
				u.telefon,
				u.data_urodz,
				u.`status`,
				r.nazwa AS rola,
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.uzytkownicy u
			JOIN lms.role r ON u.id_roli = r.id_roli
			JOIN lms.zdjecia z ON u.id_zdjecia = z.id_zdjecia
			WHERE u.id_uzytk = pk_id_uzytk;
	ELSEIF p_unique IS NOT NULL THEN		
			SELECT
				u.id_uzytk,
				u.imie,
				u.nazwisko,
				u.tyt_nauk,
				u.login,
				u.haslo,
				u.email,
				u.telefon,
				u.data_urodz,
				u.`status`,
				r.nazwa AS rola,
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.uzytkownicy u
			JOIN lms.role r ON u.id_roli = r.id_roli
			JOIN lms.zdjecia z ON u.id_zdjecia = z.id_zdjecia
			WHERE u.login = p_unique;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;
		
		SELECT
				u.id_uzytk,
				u.imie,
				u.nazwisko,
				u.tyt_nauk,
				u.login,
				u.haslo,
				u.email,
				u.telefon,
				u.data_urodz,
				u.`status`,
				r.nazwa AS rola,
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.uzytkownicy u
			JOIN lms.role r ON u.id_roli = r.id_roli
			JOIN lms.zdjecia z ON u.id_zdjecia = z.id_zdjecia
			ORDER BY u.id_uzytk
			LIMIT p_size
			OFFSET v_offset;
	ELSE
			SELECT
				u.id_uzytk,
				u.imie,
				u.nazwisko,
				u.tyt_nauk,
				u.login,
				u.haslo,
				u.email,
				u.telefon,
				u.data_urodz,
				u.`status`,
				r.nazwa AS rola,
				z.id_zdjecia,
				z.zdjecie AS plik,
				z.ext
			FROM lms.uzytkownicy u
			JOIN lms.role r ON u.id_roli = r.id_roli
			JOIN lms.zdjecia z ON u.id_zdjecia = z.id_zdjecia
			ORDER BY u.id_uzytk;
	END IF;
END //

DELIMITER ;