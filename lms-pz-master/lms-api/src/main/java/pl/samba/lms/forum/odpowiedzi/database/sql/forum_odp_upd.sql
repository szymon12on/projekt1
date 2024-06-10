DELIMITER //
DROP PROCEDURE IF EXISTS forum_odp_upd;
CREATE PROCEDURE forum_odp_upd(
    IN pk_id_odp INT,
    IN p_id_wpis INT,
    IN p_id_uzytk INT,
    IN p_tresc VARCHAR(700),
    IN p_data_wpis DATETIME
)
BEGIN
    UPDATE forum_odp
    SET
        id_wpis = p_id_wpis,
        id_uzytk = p_id_uzytk,
        tresc = p_tresc,
        data_wpis = p_data_wpis
    WHERE id_odp = pk_id_odp;
END //
DELIMITER ;