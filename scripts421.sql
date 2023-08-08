ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age >= 16);
ALTER  TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE student ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE student ADD CONSTRAINT age_default DEFAULT 20 for age;

ALTER TABLE faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color);