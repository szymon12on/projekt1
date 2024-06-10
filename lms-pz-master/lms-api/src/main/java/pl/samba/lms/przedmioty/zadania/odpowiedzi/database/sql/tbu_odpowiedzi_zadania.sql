CREATE DEFINER=`lms_admin`@`%` TRIGGER `tbu_odpowiedzi_zadania` BEFORE UPDATE ON `odpowiedzi_zadania` FOR EACH ROW
/*
 * Trigger dodaje nowe powiadomienie gdy wystawiono ocenę uczniowi za zadanie zprzedmotu.
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
		WHERE EXISTS (
			SELECT *
			FROM lms.odpowiedzi_zadania oz
			JOIN lms.zadania z ON z.id_zadania = oz.id_zadania
			WHERE z.id_przedm = p.id_przedm
				AND oz.id_odpowiedzi = NEW.id_odpowiedzi
		);
		
		SET z_tresc = CONCAT(
			'<p>Dostałeś nową ocenę za zadanie z przedmiotu \'',
			z_nazwa, 
			'\'!</p>'
		);
		
		CALL lms.powiadomienia_ins(z_id_powiadom, NEW.id_ucznia, z_tresc);
	END IF;

END