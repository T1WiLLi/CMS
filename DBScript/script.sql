DROP SCHEMA IF EXISTS cms CASCADE;
CREATE SCHEMA cms;
SET search_path TO cms;

CREATE TABLE type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE program (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id)
);


CREATE TABLE user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    date_of_birth DATE
);

CREATE TABLE employee (
    employee_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    seniority VARCHAR(50),
    type_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (type_id) REFERENCES type(id)
);

CREATE TABLE teacher (
    employee_id SERIAL PRIMARY KEY,
    teacher_id INT,
    department_id INT,
    seniority VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sigle VARCHAR(10),
    department_id INT,
    teacher_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(employee_id)
);

CREATE TABLE academic_year (
    year_id VARCHAR(10) PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE session (
    session_id SERIAL PRIMARY KEY,
    year_id VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (year_id) REFERENCES academic_year(year_id)
);

CREATE TABLE student (
    user_id INT NOT NULL,
    admission_number INT PRIMARY KEY,
    program_id INT NOT NULL,
    session_id INT NOT NULL,
    Field VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (program_id) REFERENCES program(id),
    FOREIGN KEY (session_id) REFERENCES session(session_id)
);

CREATE TABLE enrollment (
    enrollment_id SERIAL PRIMARY KEY,
    admission_number INT NOT NULL,
    session_id INT NOT NULL,
    course_id INT NOT NULL,
    end_date DATE,
    grade VARCHAR(2),
    FOREIGN KEY (admission_number) REFERENCES student(admission_number) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES session(session_id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE evaluation (
    id SERIAL PRIMARY KEY,
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
    FOREIGN KEY (admission_number) REFERENCES student(admission_number) ON DELETE CASCADE,
    FOREIGN KEY (evaluation_id) REFERENCES evaluation(id)
);

CREATE TABLE student_course (
    admission_number INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (admission_number, course_id),
    FOREIGN KEY (admission_number) REFERENCES student(admission_number) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    student_admission_number INT NOT NULL,
    course_id INT NOT NULL,
    hour_start VARCHAR(5),
    hour_end VARCHAR(5),
    FOREIGN KEY (student_admission_number) REFERENCES student(admission_number) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE mail (
    id SERIAL PRIMARY KEY,
    receiver_id INT NOT NULL,
    sender_id INT NOT NULL,
    subject VARCHAR(100),
    content TEXT,
    date DATE,
    read BOOLEAN,
    FOREIGN KEY (receiver_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    address VARCHAR(200),
    city VARCHAR(100),
    province VARCHAR(50),
    postal_code VARCHAR(10),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION delete_old_read_emails()
RETURNS TRIGGER AS $$
BEGIN 
    IF OLD.read = true THEN
        DELETE FROM mail WHERE id = OLD.id AND date < current_date - interval '30 days';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_delete_old_read_emails
AFTER UPDATE OF read ON mail
FOR EACH ROW
EXECUTE FUNCTION delete_old_read_emails();

INSERT INTO type (name) VALUES ('Teacher');
INSERT INTO type (name) VALUES ('Intern');
INSERT INTO type (name) VALUES ('Admin');
INSERT INTO type (name) VALUES ('Janitor');
INSERT INTO type (name) VALUES ('HR');
INSERT INTO type (name) VALUES ('Manager');

-- Inserting departments
INSERT INTO department (name) VALUES ('Technology');
INSERT INTO department (name) VALUES ('Health');
INSERT INTO department (name) VALUES ('Mathematics');
INSERT INTO department (name) VALUES ('French');
INSERT INTO department (name) VALUES ('English');
INSERT INTO department (name) VALUES ('Physical Education');
INSERT INTO department (name) VALUES ('Chemistry');
INSERT INTO department (name) VALUES ('Engineering');
INSERT INTO department (name) VALUES ('Pre-university');

-- Inserting programs
INSERT INTO program (name, department_id) VALUES ('Techniques de pharmacie', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name, department_id) VALUES ('Développement d’applications Web et Mobiles', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Gestion, communications administratives et médias sociaux', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Environnement, hygiène et sécurité au travail', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name) VALUES ('Gestion d’un établissement de restauration');
INSERT INTO program (name, department_id) VALUES ('Soins infirmiers', (SELECT id FROM department WHERE name = 'Health'));
INSERT INTO program (name) VALUES ('Techniques d’éducation spécialisée');
INSERT INTO program (name, department_id) VALUES ('Techniques de Comptabilité et de gestion', (SELECT id FROM department WHERE name = 'Technology'));
INSERT INTO program (name, department_id) VALUES ('Techniques de génie mécanique', (SELECT id FROM department WHERE name = 'Engineering'));
INSERT INTO program (name) VALUES ('Techniques juridiques');
INSERT INTO program (name, department_id) VALUES ('Technologie du génie électrique : automatisation et contrôle', (SELECT id FROM department WHERE name = 'Engineering'));
INSERT INTO program (name, department_id) VALUES ('Sciences de la nature', (SELECT id FROM department WHERE name = 'Pre-university'));
INSERT INTO program (name, department_id) VALUES ('Sciences humaines', (SELECT id FROM department WHERE name = 'Pre-university'));
INSERT INTO program (name, department_id) VALUES ('Arts, lettres et communication : profil arts, médias et société', (SELECT id FROM department WHERE name = 'Pre-university'));
