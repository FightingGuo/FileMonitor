CREATE TABLE IF NOT EXISTS UNIQUE_FILE
(
   unique_id VARCHAR(255) UNIQUE NOT NULL,
   create_time VARCHAR(30) NOT NULL,
    PRIMARY KEY(unique_id)
);