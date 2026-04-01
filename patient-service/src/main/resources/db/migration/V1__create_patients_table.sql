CREATE TABLE patients (
                          id          BIGSERIAL PRIMARY KEY,
                          first_name  VARCHAR(100) NOT NULL,
                          last_name   VARCHAR(100) NOT NULL,
                          email       VARCHAR(150) NOT NULL UNIQUE,
                          phone       VARCHAR(20),
                          date_of_birth DATE NOT NULL,
                          gender      VARCHAR(10) NOT NULL,
                          address     TEXT,
                          created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);