
CREATE TABLE IF NOT EXISTS idStore(name text, graphName text, nextId BIGINT NOT NULL);

CREATE OR REPLACE FUNCTION nextId(store text, graph text) RETURNS BIGINT AS $$
    DECLARE
        fnd BOOL;
        nxtID BIGINT;
		id RECORD;
    BEGIN
		fnd:=false;
        FOR id IN
            SELECT * FROM idStore WHERE name = store AND graphName = graph
        LOOP
            UPDATE idStore  SET nextId=nextId+1 WHERE name = store AND graphName = graph;
            nxtID := id.nextId;
            fnd := true;
        END LOOP;
        IF NOT fnd THEN
            INSERT INTO idStore(name, graphName, nextId) VALUES(store, graph, 2);
            nxtID := 1;
        END IF;
        RETURN nxtID;
    END;
$$ LANGUAGE plpgsql;
SELECT nextId('id', 'graph')