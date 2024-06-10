DELIMITER //
DROP PROCEDURE IF EXISTS lms.uczen_przedmiot_upd;
CREATE PROCEDURE uczen_przedmiot_upd(
	INOUT pk_id_encji INT,
	IN p_id_ucznia INT,
	IN p_id_przedm INT,
	IN p_ocena INT
)
BEGIN		
	UPDATE lms.uczen_przedmiot up
	SET ocena = p_ocena,
		data_wystaw_oc = SYSDATE()
	WHERE up.id_encji = pk_id_encji;
	
END //
DELIMITER ;