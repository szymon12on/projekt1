DELIMITER //
DROP PROCEDURE IF EXISTS lms.wykaz_ocen_czast_proc;
CREATE PROCEDURE wykaz_ocen_czast_proc(
	IN p_id_ucznia INT,
	IN p_id_przedm INT
)
BEGIN
	SELECT ocena 
	FROM odpowiedzi_zadania oz 
	WHERE oz.id_ucznia = p_id_ucznia
		AND EXISTS (
			SELECT * 
			FROM lms.zadania z 
			WHERE z.id_przedm = p_id_przedm
			AND z.id_zadania = oz.id_zadania
			);
END //
DELIMITER ;