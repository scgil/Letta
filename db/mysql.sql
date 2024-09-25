DROP DATABASE IF EXISTS letta;
CREATE DATABASE letta;


CREATE TABLE letta.users (
    id int NOT NULL AUTO_INCREMENT,
    login varchar(100) NOT NULL,
    password varchar(64) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE letta.partakers (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(40) NOT NULL,
    surname varchar(80) NOT NULL,
   	userId int NOT NULL,   
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE letta.events (
	id int NOT NULL AUTO_INCREMENT,
	title varchar(50) NOT NULL,
	description varchar(250) NOT NULL,
	category ENUM ('deportes', 'cine', 'teatro', 'tv', 'series', 'libros', 'ocio') NOT NULL,
	eventDate datetime NOT NULL,
	capacity int NOT NULL,
	place varchar(150) NOT NULL,
	img varchar(254),
	idPartaker int NOT NULL,	
	PRIMARY KEY (id),
	FOREIGN KEY (idPartaker) REFERENCES partakers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE letta.attendance (
	idEvent int NOT NULL,
	idPartaker int NOT NULL,
    PRIMARY KEY (idEvent, idPartaker),
    FOREIGN KEY (idEvent) REFERENCES events(id),
	FOREIGN KEY (idPartaker) REFERENCES partakers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE USER IF NOT EXISTS 'letta'@'localhost' IDENTIFIED WITH mysql_native_password BY 'lettapass';
GRANT ALL ON letta.* TO 'letta'@'localhost';