CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(45) NOT NULL,
    password TEXT NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS otps (
    username VARCHAR(45) NOT NULL,
    code VARCHAR(45) NOT NULL,
    PRIMARY KEY (username),
    CONSTRAINT otps_user FOREIGN KEY (username) REFERENCES users (username)
);