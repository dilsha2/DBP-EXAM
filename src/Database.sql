DROP DATABASE IF EXISTS SuperMarket;

CREATE DATABASE IF NOT EXISTS ijse;

USE ijse;

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student(
    Student_id VARCHAR(45) PRIMARY KEY,
    Student_name VARCHAR(45),
    email TEXT,
    contact VARCHAR(20),
    address TEXT,
    nic VARCHAR(45)
    );

INSERT INTO Student VALUES ('S001','dilsha','dilsha@gmail.com','0765660311','panadura','20031110016');

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher(
    teacher_id VARCHAR(45) PRIMARY KEY,
    name VARCHAR(45),
    nic VARCHAR(45),
    contact VARCHAR(20),
    address TEXT
    );

INSERT INTO Teacher VALUES ('T001','kamal','200131231234','0765660311','panadura');


DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject(
    subject_id VARCHAR(45) PRIMARY KEY,
    subject_name VARCHAR(45),
    credit DOUBLE,
    teacher_id VARCHAR(45),
    CONSTRAINT FOREIGN KEY (teacher_id) REFERENCES Teacher (teacher_id) on Delete Cascade on Update Cascade
    );

INSERT INTO Subject VALUES ('B001','IT','20','T001');

DROP TABLE IF EXISTS Course;
CREATE TABLE IF NOT EXISTS Course(
    course_id VARCHAR(45) PRIMARY KEY,
    course_name VARCHAR(45),
    cost DOUBLE,
    duration VARCHAR(45),
    subject_id VARCHAR(45),
    CONSTRAINT FOREIGN KEY (subject_id) REFERENCES Subject (subject_id) on Delete Cascade on Update Cascade
    );

INSERT INTO Course VALUES ('C001','engineering','25000.00','3 years','B001');

DROP TABLE IF EXISTS Intake;
CREATE TABLE IF NOT EXISTS Intake(
    intake_id VARCHAR(45) PRIMARY KEY,
    start_date DATE,
    intakeCol VARCHAR(45),
    description VARCHAR(45),
    course_id VARCHAR(45),
    CONSTRAINT FOREIGN KEY (course_id) REFERENCES Course (course_id) on Delete Cascade on Update Cascade
    );

INSERT INTO Intake VALUES ('I001','2022-12-7','10','none','C001');

DROP TABLE IF EXISTS Registrations;
CREATE TABLE IF NOT EXISTS Registrations(
    registration_id VARCHAR(45) PRIMARY KEY ,
    reg_date DATE,
    student_id VARCHAR(45),
    intake_id VARCHAR(45),
    CONSTRAINT FOREIGN KEY (student_id) REFERENCES Student (student_id) on Delete Cascade on Update Cascade,
    CONSTRAINT FOREIGN KEY (intake_id) REFERENCES Intake (intake_id) on Delete Cascade on Update Cascade

    );

INSERT INTO Registrations VALUES ('R001','2022-12-7','S001','I001');


DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment(
    payment_id VARCHAR(45),
    date DATE,
    cost DOUBLE,
    registration_id VARCHAR(45),
    CONSTRAINT FOREIGN KEY (registration_id) REFERENCES Registrations (registration_id) on Delete Cascade on Update Cascade

    );

INSERT INTO Payment VALUES ('P001','2022-12-7','2000.00','R001');