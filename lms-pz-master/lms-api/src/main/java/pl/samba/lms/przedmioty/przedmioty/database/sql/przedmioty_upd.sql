DELIMITER //
DROP PROCEDURE IF EXISTS lms.przedmioty_upd;
CREATE PROCEDURE przedmioty_upd(
	INOUT pk_id_przedm INT,
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
	DECLARE v_id_status INT;

	SELECT ps.id_status
	INTO v_id_status
	FROM przedmiot_status ps
	WHERE ps.kod = p_kod_status;

	UPDATE przedmioty
	SET nazwa = p_nazwa, id_prow = p_id_prow, limit_miejsc = p_limit_miejsc, opis = p_opis, war_zalicz = p_war_zalicz,
		id_okresu = p_id_okresu, id_status = v_id_status, rejestr_uczn = p_rejestr_uczn
	WHERE id_przedm = pk_id_przedm;
END //
DELIMITER ;