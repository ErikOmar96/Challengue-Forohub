ALTER TABLE topic
    MODIFY title VARCHAR(40) NOT NULL;
ALTER TABLE topic
    MODIFY message VARCHAR(250) NOT NULL;
ALTER TABLE topic
    MODIFY created_at DATETIME(6) NOT NULL;
ALTER TABLE topic
    MODIFY status enum('DELETED', 'RESOLVED', 'PENDING') NOT NULL;
ALTER TABLE topic
    ADD CONSTRAINT uc_title UNIQUE (title);
