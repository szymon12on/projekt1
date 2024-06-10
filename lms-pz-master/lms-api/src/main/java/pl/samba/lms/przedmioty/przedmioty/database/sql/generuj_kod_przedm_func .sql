DELIMITER //
DROP FUNCTION IF EXISTS lms.generuj_kod_przedm_func;
CREATE FUNCTION generuj_kod_przedm_func(
	p_id_okresu INT,
	p_nazwa VARCHAR(40)
	)	
RETURNS VARCHAR(15) DETERMINISTIC
BEGIN
   DECLARE v_kod VARCHAR(15);
   DECLARE v_okres VARCHAR(10);
	DECLARE v_id INT;
	
	SELECT 
		COALESCE(MAX(id_przedm) + 1, 1) AS id
	INTO v_id
	FROM przedmioty;
	
	SELECT o.kod
	INTO v_okres 
	FROM lms.okresy o
	WHERE o.id_okresu = p_id_okresu;
	
   SET v_kod = CONCAT_WS(
		'/',
		UPPER(SUBSTRING(REPLACE(v_okres, ' ', ''), 1, 3)),
		UPPER(SUBSTRING(REPLACE(p_nazwa, ' ', ''), 1, 4)),
		v_id);
		
   RETURN v_kod;
END //

DELIMITER ;
