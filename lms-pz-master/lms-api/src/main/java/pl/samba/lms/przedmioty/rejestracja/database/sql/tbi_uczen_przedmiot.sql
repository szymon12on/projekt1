CREATE DEFINER=`lms_admin`@`%` TRIGGER `tbi_uczen_przedmiot` BEFORE INSERT ON `uczen_przedmiot` FOR EACH ROW /*
 * Trigger sprawdza, czy uczestnik przedmiotu ma rolę UCZEN. Jeśli tak nie jest
 * rzucany jest wyjątek.
 * autor: bsurma
 */
BEGIN
	IF EXISTS(
		SELECT r.nazwa
		FROM lms.uzytkownicy u
		JOIN lms.role r ON u.id_roli = r.id_roli
		WHERE u.id_uzytk = NEW.id_ucznia AND r.nazwa != 'UCZEN'
	) THEN
      SIGNAL SQLSTATE '62020'
      SET MESSAGE_TEXT = 'Użytkownik musi być uczniem!';
   END IF;
END