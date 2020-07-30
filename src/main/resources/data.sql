INSERT INTO user (id, username, password) VALUES (1, 'user1', '$2a$04$Ye7/lJoJin6.m9sOJZ9ujeTgHEVM4VXgI2Ingpsnf9gXyXEXf/IlW');
INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');
INSERT INTO user (id, username, password) VALUES (2, 'user2', '$2a$04$StghL1FYVyZLdi8/DIkAF./2rz61uiYPI3.MaAph5hUq03XKeflyW');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 5);

INSERT INTO cars(vin,register_number,deposit,latitude,longitude,service,reservation) VALUES (11111,'TK1111',500,50.875883,20.63477,false,false);
INSERT INTO cars(vin,register_number,deposit,latitude,longitude,service,reservation) VALUES (22222,'TK2222',500,50.8757833,20.638377,false,false);
INSERT INTO cars(vin,register_number,deposit,latitude,longitude,service,reservation) VALUES (33333,'TK3333',500,50.8758883,20.639377,false,false);

INSERT INTO fuel(price,last_modified) VALUES(5.22,'2020-02-10T10:33:33');

INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
 VALUES (1,'user2','2020-01-01T10:02:02','2020-01-05T11:02:23',false,2300,300,5.22,true,1111);


INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (1,'user3','2020-01-06T10:02:02','2020-01-11T11:02:23',false,0,0,5.22,true,2222);


INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (3,'user4','2020-01-01T10:02:02','2020-01-09T11:02:23',false,0,300,5.55,true,3333);


INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (1,'user5','2020-01-09T10:02:02','2020-01-15T10:02:23',false,0,300,5.22,true,5555);

INSERT INTO user (id, username, password, name, surname, address) VALUES (3, 'Joe23', '$2aSDhasdSl2OIOASDasdAF213SADmMASK123kaSA2n2Sm2s8cT3982K2bJs', 'Joe','Kowalski','Kielce, Zimna 23/2');

INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (1,'user1','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (2,'user2','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (3,'user3','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (4,'user4','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (5,'user5','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (6,'user6','2020-01-01T10:02:02','2020-01-05T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (7,'user7','2020-01-01T10:02:02','2020-02-20T11:02:23',false,0,300,5.22,true,1111);
INSERT INTO rent(car_id,username,start_date,end_date,rent_paid,rent_cost,deposit,fuel_cost,deposit_paid,box_code)
VALUES (8,'uzytkownik','2020-01-01T10:02:02','2020-02-21T11:02:23',false,0,300,5.22,true,1111);
