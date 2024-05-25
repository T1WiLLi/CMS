DROP SCHEMA IF EXISTS cms CASCADE;
CREATE SCHEMA cms;
SET search_path TO cms;

CREATE TABLE type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    date_of_birth DATE
);

CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    seniority VARCHAR(50),
    type_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (type_id) REFERENCES type(id)
);

CREATE TABLE teacher (
    employe_id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT,
    department_id INT,
    seniority VARCHAR(50),
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE program (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE course (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sigle VARCHAR(10),
    department_id INT,
    teacher_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(employe_id)
);

CREATE TABLE academicYear (
    year_id VARCHAR(10) PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE session (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    year_id VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (year_id) REFERENCES academicYear(year_id)
);

CREATE TABLE student (
    user_id INT NOT NULL,
    admission_number INT PRIMARY KEY,
    program_id INT NOT NULL,
    session_number INT NOT NULL,
    Field VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (program_id) REFERENCES program(id)
);

CREATE TABLE Enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    admission_number INT NOT NULL,
    session_id INT NOT NULL,
    course_id INT NOT NULL,
    end_date DATE,
    grade VARCHAR(2),
    FOREIGN KEY (admission_number) REFERENCES student(admission_number),
    FOREIGN KEY (session_id) REFERENCES session(session_id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE evaluation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    ponderation INT,
    denominator INT,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE student_evaluation (
    admission_number INT NOT NULL,
    evaluation_id INT NOT NULL,
    PRIMARY KEY (admission_number, evaluation_id),
    FOREIGN KEY (admission_number) REFERENCES student(admission_number),
    FOREIGN KEY (evaluation_id) REFERENCES evaluation(id)
);

CREATE TABLE student_course (
    admission_number INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (admission_number, course_id),
    FOREIGN KEY (admission_number) REFERENCES student(admission_number),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    student_admission_number VARCHAR(50) NOT NULL,
    course_id INT NOT NULL,
    hour_start VARCHAR(5),
    hour_end VARCHAR(5),
    FOREIGN KEY (student_admission_number) REFERENCES student(admission_number),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE mail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    receiver_id INT NOT NULL,
    sender_id INT NOT NULL,
    subject VARCHAR(100),
    content TEXT,
    date DATE,
    read BOOLEAN,
    FOREIGN KEY (receiver_id) REFERENCES user(id),
    FOREIGN KEY (sender_id) REFERENCES user(id)
);

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address VARCHAR(200),
    city VARCHAR(100),
    province VARCHAR(50),
    postal_code VARCHAR(10),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

