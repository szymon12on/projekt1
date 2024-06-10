DELIMITER //

DROP PROCEDURE IF EXISTS lms.uczen_przedmiot_ins;
CREATE PROCEDURE uczen_przedmiot_ins(
	OUT pk_id_encji INT,
	IN p_id_przedm INT,
	IN p_id_ucznia INT
)
BEGIN
	INSERT INTO uczen_przedmiot(id_przedm, id_ucznia)
	VALUES (p_id_przedm, p_id_ucznia);

	SELECT LAST_INSERT_ID() INTO pk_id_encji;
END //

DELIMITER ;
