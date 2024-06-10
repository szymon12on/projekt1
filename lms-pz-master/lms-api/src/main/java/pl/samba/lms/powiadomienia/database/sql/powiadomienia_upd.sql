DELIMITER //
DROP PROCEDURE IF EXISTS powiadomienia_upd;
CREATE PROCEDURE powiadomienia_upd(
    INOUT pk_id_powiadom INT,
    IN p_id_flagi INT
)
BEGIN
    UPDATE powiadomienia p
    SET p.id_flagi = p_id_flagi
    WHERE id_powiadom = pk_id_powiadom;
END //
DELIMITER ;