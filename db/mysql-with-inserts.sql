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
    FOREIGN KEY (idPartaker) REFERENCES partakers(id),      
    PRIMARY KEY (id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE USER IF NOT EXISTS 'letta'@'localhost' IDENTIFIED WITH mysql_native_password BY 'lettapass';
GRANT ALL ON letta.* TO 'letta'@'localhost';


INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'nlmarchante17','2cfd8f7c33849e1ca274fcb9befdbe135aa6600a01227c1c4e2a562540e60e98');

INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'ibalvarez17','21743efca9ca0c4eeeba6356b46dc2df97bc3703821826db95be41dbd3fc71d0');

INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'asgonzalez17','d877632bc139c738d886fc8fe132c3c096301c933e8ca0b6b32ef4d803f81626');

INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'eaconde17','d5db8861d27f83b86ac20d898370e55b1e16e38ccdd492ea68d5cb22cf398147');

INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'scgil','0b61e96e39589a00e1bd8536f885be46c814849b6cc9fa9aeb421682cd317417');

INSERT INTO letta.users (
    id,login,password) 
VALUES (
0,'jtcosta17','ec35099fd5837fa3b05f3ba0987eefc271555d027fc4afc44c481e7fee830b7b');

INSERT INTO letta.partakers (
    id,name,surname,userId) 
VALUES (
0,'Noa','López Marchante',1);
   
INSERT INTO letta.partakers (
    id,name,surname,userId) 
VALUES (
0,'Iván','Blanco Álvarez',2);

INSERT INTO letta.partakers (
     id,name,surname,userId)
VALUES (
0,'Alberto Mateo','Sabucedo González',3);

INSERT INTO letta.partakers (
     id,name,surname,userId)
VALUES (
0,'Emma','Álvarez Conde',4);

INSERT INTO letta.partakers (
     id,name,surname,userId)
VALUES (
0,'Sergio','Castro Gil',5);

INSERT INTO letta.partakers (
     id,name,surname,userId)
VALUES (
0,'Juan','Tenorio Costa',6);


INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Peliculon','Vamos a hablar de una pelicula','cine',
    20210604134500,3,'En la calle',NULL,1);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Librazo','Vamos a hablar de un libro','libros',
    20210531113000,3,'En casa','images/ImagenEjemplo.jpg',2);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img , idPartaker) 
VALUES (
    0,'Un librito','Prueba para buscar','libros',
    20210725113000,3,'En casa',NULL,1);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Libro pasado','Prueba para buscar un evento pasado','libros',
    20200820113000,3,'En la calle',NULL,3);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Quedada para ver el mundial de natacion','Quedamos para ver el mundial 2022 de natacion celebrado en Tokio, Japon donde nadan mas de 300 participantes diferentes','deportes',
    20220723150000,8,'Bar Pepito Grillo',NULL,5);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Ver Hamlet','Vemos hamlet en el Palacio Real de Madrid','teatro',
    20210820213000,18,'Palacion Real de Madrid',NULL,3);


INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Discutimos y participamos sobre El cazador','Quedamos para ver el programa de hoy del cazador y ver quien consigue contestar mas preguntas a la primera, al ganador se le invita a una consumicion','tv',
    20210602180000,15,'Bar-Restaurante Maria Antonietta',NULL,4);


INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Good trouble','Quedada para ver la serie con posible discusion una vez terminado el capitulo','series',
    20220820123000,5,'Casa Elena',NULL,3);


INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Damos un paseo por el Minho','Quedamos para dar un paseo por el Minho durante media hora, saliendo desde el antiguo puente romano','ocio',
    20210820183000,15,'Al lado del puente romano',NULL,1);

    INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Good Food','Vemos el programa good food de la television escocesa ','tv',
    20220920113000,7,'En el restaurante Pepe',NULL,3);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Mundial de debate de TCL','Quedamos para ver la final del debate de TCL','tv',
    20220915200000,15,'En la calle',NULL,5);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Campeonato de tenis','Quedada en el bar Graduados para ver la final de Wimbledon','deportes',
    20220711200000,12,'Bar Graduados',NULL,5);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Pachanga de futbol','Vamos a hacer un poco mas activo al barrio','deportes',
    20210911170000,14,'Campo de futbol','images/campoFutbol.jpg',4);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'El viaje de Chihiro','Peliculon que hay que ver si o si','cine',
    20210814180000,20,'Cines Pontevella',NULL,2);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'En la hierba alta','Inquietante, para ver con gente maja','cine',
    20211011190000,4,'Casa de la cultura','images/hierbaAlta.jpg',1);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'La casa de Bernarda Alba','Clasico divertido','teatro',
    20220114200000,13,'Teatro municipal',NULL,6);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'La Fundacion','Antonio Buero Vallejo','teatro',
    20211212180000,13,'Teatro municipal',NULL,5);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Mentes criminales','Vamos a creernos unos detectives juntos','series',
    20210712190000,4,'Casa de la cultura',NULL,6);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'El inocente','Miniserie de netflix. Suspense e intriga, para ver del tiron','series',
    20210712190000,5,'Casa de la cultura','images/inocente.jpg',3);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Senderismo','Paseo por el Cañon del Sil','ocio',
    20220812203000,10,'Mirador Balcones de Madrid','images/balcon.jpg',2);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Cafecito','Un cafecito rapido para amenizar el descanso del trabajo','ocio',
    20211012173000,2,'Cafeteria Facultad',NULL,4);
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Jugar a las cartas','Cinquillo como lema de vida','ocio',
    20211114183000,6,'Cafeteria Graduados',NULL,2);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Maraton de Star Wars','Maraton, en serio. Todas del tiron. 16h de nuestra vida','cine',
    20210812160000,7,'Casa particular',NULL,4);
    
INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Maraton de Star Trek','14h de felicidad, no como con Star Wars','cine',
    20210812160000,7,'Casa particular',NULL,3);

INSERT INTO letta.events (
    id, title, description, category, eventDate, capacity, place, img, idPartaker) 
VALUES (
    0,'Nos vamos de concierto','El objetivo es quedarse afonico con amigos y pillar una insolacion','ocio',
    20210819220000,7,'IFEVI','images/concierto.jpg',6);
    
