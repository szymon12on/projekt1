DELIMITER //

DROP PROCEDURE IF EXISTS lms.uzytkownicy_ins;
CREATE PROCEDURE uzytkownicy_ins(
	OUT pk_id_uzytk INT,
	IN p_imie VARCHAR(40),
	IN p_nazwisko VARCHAR(40),
	IN p_tyt_nauk VARCHAR(30),
	IN p_haslo VARCHAR(60),
	IN p_email VARCHAR(40),
	IN p_telefon INT,
	IN p_data_urodz DATE,
	IN p_id_zdjecia INT,
	IN p_id_roli INT
)
BEGIN
	DECLARE v_login VARCHAR(40);
	
	SET v_login = generuj_login_func(p_imie, p_nazwisko);
	
	INSERT INTO uzytkownicy(
		imie,
		nazwisko,
		tyt_nauk,
		login,
		haslo,
		email,
		telefon,
		data_urodz,
		STATUS,
		id_zdjecia,
		id_roli
	)
	VALUES (
		p_imie,
		p_nazwisko,
		p_tyt_nauk,
		v_login,
		p_haslo,
		p_email,
		p_telefon,
		p_data_urodz,
		TRUE,
		p_id_zdjecia,
		p_id_roli
	);
	
	SELECT id_uzytk INTO pk_id_uzytk FROM uzytkownicy WHERE login = v_login;

END //

DELIMITER ;
