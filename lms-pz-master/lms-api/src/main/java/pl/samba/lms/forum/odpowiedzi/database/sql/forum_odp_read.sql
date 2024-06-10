CREATE DEFINER=`lms_admin`@`%` PROCEDURE `forum_odp_read`(
    IN pk_id_odp INT,
    IN p_size INT,
    IN p_page INT
)
BEGIN
    DECLARE v_offset INT;
    IF pk_id_odp IS NOT NULL THEN
        SELECT
            f.id_odp,
            f.id_wpis,
            f.id_uzytk,
            f.tresc,
            f.data_wpis
        FROM lms.forum_odp f
        WHERE f.id_odp = pk_id_odp;
    ELSEIF p_size IS NOT NULL AND p_page IS NOT NULL THEN
        SET v_offset = p_page * p_size;
        
        SELECT
            f.id_odp,
            f.id_wpis,
            f.id_uzytk,
            f.tresc,
            f.data_wpis
        FROM lms.forum_odp f
        ORDER BY f.id_odp
        LIMIT p_size
        OFFSET v_offset;
    ELSE
        SELECT
            f.id_odp,
            f.id_wpis,
            f.id_uzytk,
            f.tresc,
            f.data_wpis
        FROM lms.forum_odp f
        ORDER BY f.id_odp;
    END IF;
END