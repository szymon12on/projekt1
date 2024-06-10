CREATE DEFINER=`lms_admin`@`%` PROCEDURE `forum_wpisy_read`(
    IN pk_id_wpis INT,
    IN p_size INT,
    IN p_page INT
)
BEGIN
    DECLARE v_offset INT;
    IF pk_id_wpis IS NOT NULL THEN
        SELECT
            f.id_wpis,
            f.id_przedm,
            f.id_uzytk,
            f.temat,
            f.tresc,
            f.data_wpis
        FROM lms.forum_wpisy f
        WHERE f.id_wpis = pk_id_wpis;
    ELSEIF p_size IS NOT NULL AND p_page IS NOT NULL THEN
        SET v_offset = p_page * p_size;
        
        SELECT
            f.id_wpis,
            f.id_przedm,
            f.id_uzytk,
            f.temat,
            f.tresc,
            f.data_wpis
        FROM lms.forum_wpisy f
        ORDER BY f.id_wpis
        LIMIT p_size
        OFFSET v_offset;
    ELSE
        SELECT
            f.id_wpis,
            f.id_przedm,
            f.id_uzytk,
            f.temat,
            f.tresc,
            f.data_wpis
        FROM lms.forum_wpisy f
        ORDER BY f.id_wpis;
    END IF;
END