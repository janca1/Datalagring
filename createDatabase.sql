CREATE DATABASE sms;
use sms;

CREATE TABLE address (
 id INT NOT NULL,
 city VARCHAR(50),
 street VARCHAR(100),
 house_number VARCHAR(50),
 zip_code VARCHAR(50)
);

ALTER TABLE address ADD CONSTRAINT PK_address PRIMARY KEY (id);


CREATE TABLE booking (
 id INT NOT NULL,
 date TIMESTAMP(6) NOT NULL,
 time TIME(6) NOT NULL,
 instructor VARCHAR(100) NOT NULL,
 number_of_participants INT NOT NULL
);

ALTER TABLE booking ADD CONSTRAINT PK_booking PRIMARY KEY (id);


CREATE TABLE email (
 id INT NOT NULL,
 email VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE email ADD CONSTRAINT PK_email PRIMARY KEY (id);


CREATE TABLE person (
 id INT NOT NULL,
 first_name VARCHAR(50) NOT NULL,
 age INT,
 person_number VARCHAR(12) NOT NULL UNIQUE,
 last_name VARCHAR(50) NOT NULL,
 address_id INT NOT NULL
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (id);


CREATE TABLE person_email (
 email_id INT NOT NULL REFERENCES email ON DELETE CASCADE,
 person_id INT NOT NULL REFERENCES person ON DELETE CASCADE
);

ALTER TABLE person_email ADD CONSTRAINT PK_person_email PRIMARY KEY (email_id,person_id);


CREATE TABLE phone_number (
 id INT NOT NULL,
 phone_number VARCHAR(20) NOT NULL UNIQUE
);

ALTER TABLE phone_number ADD CONSTRAINT PK_phone_number PRIMARY KEY (id);


CREATE TABLE student (
 id INT NOT NULL,
 number_of_siblings_studying INT,
 number_of_instruments INT NOT NULL,
 person_id INT NOT NULL
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (id);


CREATE TABLE rental (
 id INT NOT NULL,
 rental_status VARCHAR(50) NOT NULL,
 student_id INT NOT NULL,
 instrument_id INT NOT NULL
);

ALTER TABLE rental ADD CONSTRAINT PK_rental PRIMARY KEY (id);


CREATE TABLE application (
 id INT NOT NULL,
 instrument VARCHAR(10) NOT NULL,
 skill_level VARCHAR(50) NOT NULL,
 student_id INT NOT NULL,
 address_id INT NOT NULL
);

ALTER TABLE application ADD CONSTRAINT PK_application PRIMARY KEY (id);


CREATE TABLE instructor (
 id INT NOT NULL,
 person_id INT NOT NULL
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (id);


CREATE TABLE instrument (
 id INT NOT NULL,
 type VARCHAR(50) NOT NULL,
 brand VARCHAR(50),
 rental_fee INT NOT NULL,
 student_id INT
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (id);


CREATE TABLE lesson (
 id INT NOT NULL,
 skill_level VARCHAR(50) NOT NULL,
 minimum_numer_of_students INT,
 maximum_nuber_of_students INT,
 number_of_students INT NOT NULL,
 genre VARCHAR(50) NOT NULL,
 price INT NOT NULL,
 date TIMESTAMP(6) NOT NULL,
 time TIMESTAMP(6) NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (id);


CREATE TABLE payment (
 id INT NOT NULL,
 discount VARCHAR(100),
 price INT NOT NULL,
 date TIMESTAMP(6),
 time TIME(6),
 first_name VARCHAR(100),
 last_name VARCHAR(100),
 student_id INT,
 booking_id INT
);

ALTER TABLE payment ADD CONSTRAINT PK_payment PRIMARY KEY (id);


CREATE TABLE payment_purpose (
 payment_id INT NOT NULL,
 payment_purpose VARCHAR(50) NOT NULL
);

ALTER TABLE payment_purpose ADD CONSTRAINT PK_payment_purpose PRIMARY KEY (payment_id,payment_purpose);


CREATE TABLE person_phone_number (
 person_id INT NOT NULL REFERENCES person ON DELETE CASCADE,
 phone_number_id INT NOT NULL REFERENCES phone_number ON DELETE CASCADE
);

ALTER TABLE person_phone_number ADD CONSTRAINT PK_person_phone_number PRIMARY KEY (person_id,phone_number_id);


CREATE TABLE salary (
 id INT NOT NULL,
 number_of_lessons INT NOT NULL,
 total_amount INT NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE salary ADD CONSTRAINT PK_salary PRIMARY KEY (id);


CREATE TABLE student_lesson (
 student_id INT NOT NULL REFERENCES student ON DELETE CASCADE,
 lesson_id INT NOT NULL REFERENCES lesson ON DELETE CASCADE
);

ALTER TABLE student_lesson ADD CONSTRAINT PK_student_lesson PRIMARY KEY (student_id,lesson_id);


CREATE TABLE instructor_instrument (
 instructor_id INT NOT NULL REFERENCES instructor ON DELETE CASCADE,
 instrument_id INT NOT NULL REFERENCES instrument ON DELETE CASCADE
);

ALTER TABLE instructor_instrument ADD CONSTRAINT PK_instructor_instrument PRIMARY KEY (instructor_id,instrument_id);


ALTER TABLE person ADD CONSTRAINT FK_person_0 FOREIGN KEY (address_id) REFERENCES address (id);


ALTER TABLE person_email ADD CONSTRAINT FK_person_email_0 FOREIGN KEY (email_id) REFERENCES email (id);
ALTER TABLE person_email ADD CONSTRAINT FK_person_email_1 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE application ADD CONSTRAINT FK_application_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE application ADD CONSTRAINT FK_application_1 FOREIGN KEY (address_id) REFERENCES address (id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE instrument ADD CONSTRAINT FK_instrument_0 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE payment ADD CONSTRAINT FK_payment_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE payment ADD CONSTRAINT FK_payment_1 FOREIGN KEY (booking_id) REFERENCES booking (id);


ALTER TABLE payment_purpose ADD CONSTRAINT FK_payment_purpose_0 FOREIGN KEY (payment_id) REFERENCES payment (id);


ALTER TABLE person_phone_number ADD CONSTRAINT FK_person_phone_number_0 FOREIGN KEY (person_id) REFERENCES person (id);
ALTER TABLE person_phone_number ADD CONSTRAINT FK_person_phone_number_1 FOREIGN KEY (phone_number_id) REFERENCES phone_number (id);


ALTER TABLE rental ADD CONSTRAINT FK_rental_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE rental ADD CONSTRAINT FK_rental_1 FOREIGN KEY (instrument_id) REFERENCES instrument (id);


ALTER TABLE salary ADD CONSTRAINT FK_salary_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_1 FOREIGN KEY (lesson_id) REFERENCES lesson (id);


ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);
ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_1 FOREIGN KEY (instrument_id) REFERENCES instrument (id);


