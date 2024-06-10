DELIMITER //
DROP PROCEDURE IF EXISTS lms.uczen_przedmiot_del;
CREATE PROCEDURE uczen_przedmiot_del(
	IN p_id_ucznia INT,
	IN p_id_przedm INT
)
BEGIN
	DELETE FROM uczen_przedmiot up
	WHERE up.id_ucznia = p_id_ucznia 
	AND up.id_przedm = p_id_przedm;
END //
DELIMITER ;