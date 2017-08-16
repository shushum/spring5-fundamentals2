SET MODE POSTGRESQL;

CREATE TABLE country (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(255),
  code_name VARCHAR(255)
);

CREATE TABLE person (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(20),
  last_name  VARCHAR(30),
  country_id INT,
  age        INT,
  height     FLOAT,
  programmer BOOL,
  broke      BOOL,

  FOREIGN KEY (country_id) REFERENCES country (id)
);

-- Dictionary
CREATE TABLE contact_type (
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30)
);
INSERT INTO contact_type (name) VALUES ('TELEPHONE');
INSERT INTO contact_type (name) VALUES ('EMAIL');
INSERT INTO contact_type (name) VALUES ('ADDRESS');

CREATE TABLE contact (
  person_id INT,
  type_id   INT,
  value     VARCHAR(30),

  PRIMARY KEY (person_id, type_id),
  FOREIGN KEY (person_id) REFERENCES person (id),
  FOREIGN KEY (type_id) REFERENCES contact_type (id)
);