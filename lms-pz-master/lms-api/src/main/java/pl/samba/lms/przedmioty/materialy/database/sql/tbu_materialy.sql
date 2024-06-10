CREATE DEFINER=`lms_admin`@`%` TRIGGER `tbu_materialy` BEFORE UPDATE ON `materialy` FOR EACH ROW /*
 * Trigger dodaje powiadomienie wszystkim uczesnikom przedmiotu gdy zostanie
 * uwidoczniony materiał.
 * autor: bsurma
 */

BEGIN
	DECLARE z_id_powiadom INT;
	DECLARE z_tresc VARCHAR(1000);
	DECLARE z_nazwa VARCHAR(1000);
	DECLARE done INT DEFAULT 0;
	DECLARE z_id_ucznia INT;

	DECLARE c_uczniowie CURSOR FOR
		SELECT up.id_ucznia
		FROM lms.uczen_przedmiot up
		WHERE up.id_przedm = NEW.id_przedm;
		
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	IF(NEW.widocznosc = 1 AND OLD.widocznosc = 0) THEN
		SELECT p.nazwa
		INTO z_nazwa
		FROM lms.przedmioty p
		WHERE p.id_przedm = NEW.id_przedm;
		
		SET z_tresc = CONCAT(
			'<p>Dostępny nowy materiał z przedmiotu \'',
			z_nazwa, 
			'\'!</p>'
		);
	
		OPEN c_uczniowie;
	
		send_loop: LOOP
		    FETCH c_uczniowie INTO z_id_ucznia;
		    IF done THEN
		        LEAVE send_loop;
		    END IF;
		
		    CALL lms.powiadomienia_ins(z_id_powiadom, z_id_ucznia, z_tresc);
		END LOOP;
		
		CLOSE c_uczniowie;
	END IF;
END