CREATE TABLE IF NOT EXISTS features
(
    id UUID PRIMARY KEY,
    "timestamp" timestamp NOT NULL,
    begin_viewing_date timestamp NOT NULL,
    end_viewing_date timestamp NOT NULL,
    mission_name varchar(32) NOT NULL,
    quicklook TEXT
);
