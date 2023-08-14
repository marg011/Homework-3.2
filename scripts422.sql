CREATE TABLE persons(
                        id BIGINT,
                        name VARCHAR(255) PRIMARY KEY,
                        age INTEGER,
                        drivers_license BOOLEAN,
                        car_id BIGINT REFERENCES cars (id)
);

CREATE TABLE cars(
                     id BIGINT PRIMARY KEY,
                     brand VARCHAR(255),
                     model VARCHAR(255),
                     price NUMERIC
);