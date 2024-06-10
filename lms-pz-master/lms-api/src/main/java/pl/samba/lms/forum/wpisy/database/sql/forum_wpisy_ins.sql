DELIMITER //
DROP PROCEDURE IF EXISTS lms.forum_wpisy_ins;
CREATE PROCEDURE forum_wpisy_ins(
    OUT pk_id_wpis INT,
    IN p_id_przedm INT,
    IN p_id_uzytk INT,
    IN p_temat VARCHAR(200),
    IN p_tresc VARCHAR(700),
    IN p_data_wpis DATETIME
)
BEGIN
    INSERT INTO forum_wpisy(id_przedm, id_uzytk, temat, tresc, data_wpis)
    VALUES (p_id_przedm, p_id_uzytk, p_temat, p_tresc, p_data_wpis);

    SET pk_id_wpis = LAST_INSERT_ID();
END //
DELIMITER ;