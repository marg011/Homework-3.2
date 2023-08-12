-- liquibase formatted sql

-- changeset margo:1
CREATE  INDEX student_name_index ON student (name);

-- changeset margo:2
CREATE INDEX faculty_name_color ON faculty (name, color);