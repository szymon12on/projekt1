DELIMITER //
DROP PROCEDURE IF EXISTS forum_odp_del;
CREATE PROCEDURE forum_odp_del(
    IN pk_id_odp INT
)
BEGIN
    DELETE FROM forum_odp
    WHERE id_odp = pk_id_odp;
END //
DELIMITER ;