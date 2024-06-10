CREATE DEFINER=`lms_admin`@`%` TRIGGER `tai_zadania` AFTER INSERT ON `zadania` FOR EACH ROW /*
 * Trigger dodaje powiadomienie wszystkim uczesnikom przedmiotu gdy zostanie
 * dodane zadanie do przedmiotu i zadanie jest już aktywne.
 * autor: bsurma
 */
BEGIN
	DECLARE z_id_powiadom INT;
	DECLARE z_przedmiot VARCHAR(200);
	DECLARE z_tresc VARCHAR(1000);
	DECLARE done INT DEFAULT 0;
	DECLARE id_ucznia INT;
	
	DECLARE c_uczniowie CURSOR FOR
	    SELECT up.id_ucznia
	    FROM lms.uczen_przedmiot up
	    WHERE up.id_przedm = NEW.id_przedm;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	

	IF(DATE(NEW.data_pocz) <= CURDATE()) THEN
		SELECT p.nazwa
		INTO z_przedmiot
		FROM lms.przedmioty p
		WHERE p.id_przedm = NEW.id_przedm;
		
		SET z_tresc := CONCAT(
			'<p>Dostępne nowe zadanie dla przedmiotu \'',
			z_przedmiot,
			'\'!</p><p>Zadanie będzie dostępne do dnia: ',
			DATE(NEW.data_konc),
			'.<p>'
		);
		
		OPEN c_uczniowie;
	
		read_loop: LOOP
		    FETCH c_uczniowie INTO id_ucznia;
		    IF done THEN
		        LEAVE read_loop;
		    END IF;
		
		    CALL lms.powiadomienia_ins(z_id_powiadom, id_ucznia, z_tresc);
		END LOOP;
		
		CLOSE c_uczniowie;
	END IF;
END