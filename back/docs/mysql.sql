create table user(
  id int primary key auto_increment,
  username varchar(50) not null,
  password char(32) not null,
  salt char(32) not null
);

create table project(
  id int primary key auto_increment,
  uid int default 0,
  name varchar(100) not null,
  ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  utime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int default 0 comment '逻辑删除'
);

create table spec(
  id int primary key auto_increment,
  pid int not null,
  name varchar(100) not null,
  ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  utime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  width int not null,
  height int not null,
  is_deleted int default 0 comment '逻辑删除',
  jsonData json default null
);

create table project2spec(
  id int primary key auto_increment,
  pid int not null references project(id),
  sid int not null references spec(id)
);

INSERT INTO project (uid, name) VALUES (1, 'Project A');
INSERT INTO project (uid, name) VALUES (2, 'Project B');
INSERT INTO project (uid, name) VALUES (3, 'Project C');
INSERT INTO project (uid, name) VALUES (4, 'Project D');
INSERT INTO project (uid, name) VALUES (5, 'Project E');
INSERT INTO spec (pid, name, width, height) VALUES (1, 'Spec A1', 1024, 768);
INSERT INTO spec (pid, name, width, height) VALUES (1, 'Spec A2', 1280, 720);
INSERT INTO spec (pid, name, width, height) VALUES (2, 'Spec B1', 1920, 1080);
INSERT INTO spec (pid, name, width, height) VALUES (3, 'Spec C1', 800, 600);
INSERT INTO spec (pid, name, width, height) VALUES (4, 'Spec D1', 2560, 1440);
