DELIMITER //
DROP PROCEDURE IF EXISTS forum_wpisy_upd;
CREATE PROCEDURE forum_wpisy_upd(
    IN pk_id_wpis INT,
    IN p_id_przedm INT,
    IN p_id_uzytk INT,
    IN p_temat VARCHAR(200),
    IN p_tresc VARCHAR(700),
    IN p_data_wpis DATETIME
)
BEGIN
    UPDATE forum_wpisy
    SET
        id_przedm = p_id_przedm,
        id_uzytk = p_id_uzytk,
        temat = p_temat,
        tresc = p_tresc,
        data_wpis = p_data_wpis
    WHERE id_wpis = pk_id_wpis;
END //
DELIMITER ;