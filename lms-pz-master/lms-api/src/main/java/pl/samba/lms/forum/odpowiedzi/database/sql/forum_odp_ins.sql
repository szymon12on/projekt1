DELIMITER //
DROP PROCEDURE IF EXISTS lms.forum_odp_ins;
CREATE PROCEDURE forum_odp_ins(
    OUT pk_id_odp INT,
    IN p_id_wpis INT,
    IN p_id_uzytk INT,
    IN p_tresc VARCHAR(700),
    IN p_data_wpis DATETIME
)
BEGIN
    INSERT INTO forum_odp(id_wpis, id_uzytk, tresc, data_wpis)
    VALUES (p_id_wpis, p_id_uzytk, p_tresc, p_data_wpis);

    SET pk_id_odp = LAST_INSERT_ID();
END //
DELIMITER ;