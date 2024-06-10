DELIMITER //
DROP PROCEDURE IF EXISTS powiadomienia_del;
CREATE PROCEDURE powiadomienia_del(
    IN pk_id_powiadom INT
)
BEGIN
    DELETE FROM powiadomienia
    WHERE id_powiadom = pk_id_powiadom;
END //
DELIMITER ;