DO $$
DECLARE
    statements CURSOR FOR
        SELECT tablename FROM pg_tables
        WHERE schemaname = 'library_management';
BEGIN
    FOR stmt IN statements LOOP
        EXECUTE 'TRUNCATE TABLE library_management.' || quote_ident(stmt.tablename) || ' CASCADE;';
    END LOOP;
END $$ LANGUAGE plpgsql;
