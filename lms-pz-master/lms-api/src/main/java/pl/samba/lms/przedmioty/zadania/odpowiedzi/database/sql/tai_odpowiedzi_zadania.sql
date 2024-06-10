CREATE DEFINER=`lms_admin`@`%` TRIGGER `tai_odpowiedzi_zadania` BEFORE INSERT ON `odpowiedzi_zadania` FOR EACH ROW /*
 * Trigger dodaje nowe powiadomienie nauczycielowi gdy uczen doda odpowiedz
 * do zadania.
 * autor: bsurma
 */

BEGIN
	DECLARE z_id_powiadom INT;
	DECLARE z_tresc VARCHAR(1000);
	DECLARE z_nazwa VARCHAR(1000);
	DECLARE z_uzytkownik VARCHAR(100);
	DECLARE z_id_nauczyciela INT;
	
	SELECT 
		p.id_prow, 
		p.nazwa
	INTO 
		z_id_nauczyciela, 
		z_nazwa
	FROM lms.zadania z
	JOIN lms.przedmioty p ON z.id_przedm = z.id_przedm
	WHERE z.id_zadania = NEW.id_zadania;
	
	SELECT CONCAT_WS(" ", u.imie, u.nazwisko)
	INTO z_uzytkownik
	FROM lms.uzytkownicy u
	WHERE u.id_uzytk = NEW.id_ucznia;

	SET z_tresc = CONCAT(
		'<p>Użytkownik ',
		z_uzytkownik,
		' dodał nową odpowiedź do zadania z przedmiotu \'',
		z_nazwa, 
		'\'.</p>'
	);
	
	CALL lms.powiadomienia_ins(z_id_powiadom, z_id_nauczyciela, z_tresc);

END