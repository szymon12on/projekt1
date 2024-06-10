DELIMITER //
DROP PROCEDURE IF EXISTS lms.materialy_del;
CREATE PROCEDURE materialy_del(
	IN pk_id_mater INT
)
BEGIN
	DECLARE z_lp INT;
	DECLARE z_id_przedm INT;
	
	SELECT m.lp, m.id_przedm
	INTO z_lp, z_id_przedm
	FROM lms.materialy m
	WHERE m.id_mater = pk_id_mater;

	DELETE FROM materialy
	WHERE id_mater = pk_id_mater;
	
	/*
	 * Porządkowanie lp
	 */

	CALL popraw_lp_material_proc(z_id_przedm, z_lp);

END //

DELIMITER ;