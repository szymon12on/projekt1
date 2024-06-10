DELIMITER //
DROP PROCEDURE IF EXISTS lms.wykaz_ocen_konc_proc;
CREATE PROCEDURE wykaz_ocen_konc_proc(
	IN p_id_ucznia INT
)
BEGIN
	SELECT pu.id_przedm, pu.ocena, p.nazwa, pu.id_ucznia, o.kod
	FROM lms.uczen_przedmiot pu 
	JOIN lms.przedmioty p ON pu.id_przedm = p.id_przedm 
	JOIN lms.okresy o ON o.id_okresu = p.id_okresu
	WHERE pu.id_ucznia = p_id_ucznia
	ORDER BY o.data_pocz DESC;
END //
DELIMITER ;