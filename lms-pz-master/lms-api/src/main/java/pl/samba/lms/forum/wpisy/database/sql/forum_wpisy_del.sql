DELIMITER //
DROP PROCEDURE IF EXISTS forum_wpisy_del;
CREATE PROCEDURE forum_wpisy_del(
    IN pk_id_wpis INT
)
BEGIN
    DELETE FROM forum_wpisy
    WHERE id_wpis = pk_id_wpis;
END //
DELIMITER ;