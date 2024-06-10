DELIMITER //
DROP PROCEDURE IF EXISTS lms.popraw_lp_material_proc;
CREATE PROCEDURE popraw_lp_material_proc(
	p_id_przedm INT,
	p_current_lp INT
)
BEGIN
	DECLARE z_id_material INT;
	DECLARE done INT DEFAULT 0;
		
	DECLARE c_material CURSOR FOR
		SELECT m.id_mater
		FROM lms.materialy m
		WHERE m.lp > p_current_lp
		ORDER BY m.lp ASC;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	OPEN c_material;
	
	popraw_loop : LOOP
		FETCH c_material INTO z_id_material;
		IF done THEN
        LEAVE popraw_loop;
    	END IF;
    	
    	
		UPDATE lms.materialy m
		SET m.lp = m.lp - 1
		WHERE m.id_mater = z_id_material;

	END LOOP;
	
	CLOSE c_material;
END //

DELIMITER ;