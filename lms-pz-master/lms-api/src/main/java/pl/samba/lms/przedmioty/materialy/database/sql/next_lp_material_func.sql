DELIMITER //
DROP FUNCTION IF EXISTS lms.next_lp_material_func;
CREATE FUNCTION next_lp_material_func(
	p_id_przedm INT
)
RETURNS INT DETERMINISTIC
BEGIN
	DECLARE z_lp INT;
	
	SELECT MAX(m.lp) + 1
	INTO z_lp
	FROM lms.materialy m
	WHERE m.id_przedm = p_id_przedm
	GROUP BY m.id_przedm;
	
	IF(z_lp IS NULL) THEN
		SET z_lp = 1;
	END IF;
	
	RETURN z_lp;
END //

DELIMITER ;