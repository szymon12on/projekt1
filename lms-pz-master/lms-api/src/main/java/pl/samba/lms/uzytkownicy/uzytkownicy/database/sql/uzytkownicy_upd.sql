DELIMITER //
DROP PROCEDURE IF EXISTS uzytkownicy_upd;
CREATE PROCEDURE uzytkownicy_upd(
	INOUT pk_id_uzytk INT,
	IN p_imie VARCHAR(40),
	IN p_nazwisko VARCHAR(40),
	IN p_tyt_nauk VARCHAR(30),
	IN p_haslo VARCHAR(40),
	IN p_email VARCHAR(40),
	IN p_telefon INT,
	IN p_data_urodz DATE,
	IN p_status BOOLEAN,
	IN p_id_zdjecia INT,
	IN p_id_roli INT
)
BEGIN
	DECLARE v_login VARCHAR(40);
	
	UPDATE uzytkownicy
	SET
		imie = p_imie,
		nazwisko = p_nazwisko,
		tyt_nauk = p_tyt_nauk,
		haslo = p_haslo,
		email = p_email,
		telefon = p_telefon,
		data_urodz = p_data_urodz,
		STATUS = p_status,
		id_zdjecia = p_id_zdjecia,
		id_roli = p_id_roli
	WHERE id_uzytk = pk_id_uzytk;
		
END //
DELIMITER ;