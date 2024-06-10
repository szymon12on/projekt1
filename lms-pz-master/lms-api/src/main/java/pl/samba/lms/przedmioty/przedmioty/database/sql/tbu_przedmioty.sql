CREATE DEFINER=`lms_admin`@`%` TRIGGER `tbu_przedmioty` BEFORE UPDATE ON `przedmioty` FOR EACH ROW /*
 *	Trigger sprawdza, czy prowadzący przedmiot ma rolę NAUCZYCIEL.
 * Jeśli tak nie jest rzucany jest wyjątek.
 * autor: bsurma
 */
BEGIN
	IF EXISTS(
		SELECT r.nazwa
		FROM lms.uzytkownicy u
		JOIN lms.role r ON u.id_roli = r.id_roli
		WHERE u.id_uzytk =  NEW.id_prow AND r.nazwa != 'NAUCZYCIEL'
	) THEN
      SIGNAL SQLSTATE '62010'
      SET MESSAGE_TEXT = 'Prowadzący przedmiot musi być nauczycielem!';
   END IF;
END