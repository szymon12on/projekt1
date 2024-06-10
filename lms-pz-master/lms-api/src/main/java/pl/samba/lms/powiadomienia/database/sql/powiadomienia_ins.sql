DELIMITER //
DROP PROCEDURE IF EXISTS powiadomienia_ins;
CREATE PROCEDURE powiadomienia_ins(
    OUT pk_id_powiadom INT,
    IN p_id_odbiorcy INT,
    IN tresc VARCHAR(1000)
)
BEGIN
   INSERT INTO lms.powiadomienia (id_odbiorcy, tresc) 
   VALUES (p_id_odbiorcy, tresc);
   
   SELECT LAST_INSERT_ID() INTO pk_id_powiadom;
END //
DELIMITER ;