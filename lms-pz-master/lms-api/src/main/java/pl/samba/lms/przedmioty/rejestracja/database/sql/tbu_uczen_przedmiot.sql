CREATE DEFINER=`lms_admin`@`%` TRIGGER `tbu_uczen_przedmiot` BEFORE UPDATE ON `uczen_przedmiot` FOR EACH ROW /*
 * Trigger dodaje nowe powiadomienie gdy wystawiono ocenę uczniowi z przedmotu.
 * autor: bsurma
 */

BEGIN
	DECLARE z_id_powiadom INT;
	DECLARE z_tresc VARCHAR(1000);
	DECLARE z_nazwa VARCHAR(1000);

	IF(OLD.ocena IS NULL AND NEW.ocena IS NOT NULL) THEN
		SELECT p.nazwa
		INTO z_nazwa
		FROM lms.przedmioty p
		WHERE p.id_przedm = NEW.id_przedm;
	
		SET z_tresc = CONCAT(
			'<p>Dostałeś nową ocenę z przedmiotu \'',
			z_nazwa, 
			'\'!</p>'
		);
		
		CALL lms.powiadomienia_ins(z_id_powiadom, NEW.id_ucznia, z_tresc);
	END IF;

END