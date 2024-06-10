DELIMITER //

DROP PROCEDURE IF EXISTS lms.przedmioty_ins;
CREATE PROCEDURE lms.przedmioty_ins(
	OUT pk_id_przedm INT,
	IN p_nazwa VARCHAR(1000),
	IN p_id_prow INT,
	IN p_limit_miejsc INT,
	IN p_opis VARCHAR(2000),
	IN p_war_zalicz VARCHAR(2000),
	IN p_id_okresu INT,
	IN p_kod_status VARCHAR(3),
	IN p_rejestr_uczn BOOLEAN
)
BEGIN
	DECLARE v_kod VARCHAR(15);
	DECLARE v_id_status INT;
	
	SET v_kod = generuj_kod_przedm_func(p_id_okresu,p_nazwa);
   
	SELECT ps.id_status
	INTO v_id_status
	FROM przedmiot_status ps
	WHERE ps.kod = p_kod_status;
	
	IF(v_id_status IS NULL) THEN 
		SIGNAL SQLSTATE '65000'
      SET MESSAGE_TEXT = 'Nieprawidłowy status przedmiotu!';
   END IF;
	
	INSERT INTO przedmioty(kod, nazwa, id_prow, limit_miejsc, opis, war_zalicz, id_okresu, id_status, rejestr_uczn)
	VALUES (v_kod, p_nazwa, p_id_prow, p_limit_miejsc, p_opis, p_war_zalicz, p_id_okresu, v_id_status, p_rejestr_uczn);

	SELECT LAST_INSERT_ID() INTO pk_id_przedm;
END //

DELIMITER ;
