DELIMITER //

DROP PROCEDURE IF EXISTS lms.zadania_read;
CREATE PROCEDURE zadania_read(
	IN pk_id_zadania INT,
	IN p_size INT,
	IN p_page INT,
	IN p_kod VARCHAR(100),
	IN p_login VARCHAR(100),
	IN p_id_typu INT
)
BEGIN
	DECLARE v_offset  INT; 
	IF pk_id_zadania IS NOT NULL THEN
		SELECT
			z.id_zadania,
			z.id_przedm,
			z.opis,
			z.data_wstaw,
			z.data_pocz,
			z.data_konc,
			z.tresc,
			z.id_typu
		FROM lms.zadania z
		WHERE z.id_zadania = pk_id_zadania;
	ELSEIF p_size IS NOT NULL OR p_page IS NOT NULL THEN
		SET v_offset = p_page * p_size;

		SELECT
			z.id_zadania,
			z.id_przedm,
			z.opis,
			z.data_wstaw,
			z.data_pocz,
			z.data_konc,
			z.tresc,
			z.id_typu
		FROM lms.zadania z
		WHERE EXISTS(
			SELECT p.id_przedm
			FROM  lms.przedmioty p
			WHERE  z.id_przedm = p.id_przedm
				AND p.kod = p_kod
		)
		ORDER BY z.id_zadania
		LIMIT p_size
		OFFSET v_offset;
	ELSEIF p_id_typu IS NOT NULL THEN
		IF p_login IS NOT NULL THEN
			SELECT
				z.id_zadania,
				z.id_przedm,
				z.opis,
				z.data_wstaw,
				z.data_pocz,
				z.data_konc,
				z.tresc,
				z.id_typu
			FROM lms.zadania z
			WHERE NOW() BETWEEN z.data_pocz AND z.data_konc
				AND z.id_typu = p_id_typu
				AND EXISTS(
					SELECT up.id_ucznia
					FROM lms.uczen_przedmiot up
					WHERE up.id_przedm = z.id_przedm
						AND EXISTS(
							SELECT u.id_uzytk
							FROM lms.uzytkownicy u
							WHERE u.id_uzytk = up.id_ucznia
						)
				)
			ORDER BY z.data_pocz ASC;
		ELSEIF p_kod IS NOT NULL THEN
			SELECT
				z.id_zadania,
				z.id_przedm,
				z.opis,
				z.data_wstaw,
				z.data_pocz,
				z.data_konc,
				z.tresc,
				z.id_typu
			FROM lms.zadania z
			WHERE NOW() BETWEEN z.data_pocz AND z.data_konc
				AND z.id_typu = p_id_typu
				AND EXISTS(
				SELECT p.id_przedm
				FROM  lms.przedmioty p
				WHERE  z.id_przedm = p.id_przedm
					AND p.kod = p_kod
				)
			ORDER BY z.data_pocz ASC;
		ELSE
			SELECT
				z.id_zadania,
				z.id_przedm,
				z.opis,
				z.data_wstaw,
				z.data_pocz,
				z.data_konc,
				z.tresc,
				z.id_typu
			FROM lms.zadania z
			WHERE NOW() BETWEEN z.data_pocz AND z.data_konc
				AND z.id_typu = p_id_typu
			ORDER BY z.data_pocz ASC;
		END IF;
	ELSE
		SELECT
			z.id_zadania,
			z.id_przedm,
			z.opis,
			z.data_wstaw,
			z.data_pocz,
			z.data_konc,
			z.tresc,
			z.id_typu
		FROM lms.zadania z
		WHERE EXISTS(
			SELECT p.id_przedm
			FROM  lms.przedmioty p
			WHERE  z.id_przedm = p.id_przedm
				AND p.kod = p_kod
		)
		ORDER BY z.id_zadania;
	END IF;
END //

DELIMITER ;
