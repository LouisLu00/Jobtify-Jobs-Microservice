CREATE TABLE IF NOT EXISTS job (
                     job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     public_view BOOLEAN NOT NULL,
                     company VARCHAR(255) NOT NULL,
                     title VARCHAR(255) NOT NULL,
                     description TEXT,
                     salary DOUBLE NOT NULL,
                     location VARCHAR(255),
                     industry VARCHAR(255)
);

