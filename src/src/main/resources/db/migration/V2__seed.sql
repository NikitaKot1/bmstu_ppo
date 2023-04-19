-- truncate table consumer cascade;
-- truncate table manufacturer cascade;
-- truncate table mark cascade;
-- truncate table comment cascade;
-- truncate table ingredient cascade;
-- truncate table added_to_like cascade;
-- truncate table ingredient_list cascade;


insert into manufacturer (login, password)
values ('login1', 'password1'),
       ('login2', 'password2'),
       ('login3', 'password3'),
       ('login4', 'password4'),
       ('login5', 'password5');

insert into consumer (login, password)
values ('login1', 'password1'),
       ('login2', 'password2'),
       ('login3', 'password3'),
       ('login4', 'password4'),
       ('login5', 'password5');


insert into mark (name, description, date, ownerid)
VALUES ('name1', 'desc1', '2023-01-01', 1),
       ('name2', 'desc2', '2023-02-02', 2),
       ('name3', 'desc3', '2023-03-03', 3),
       ('name4', 'desc4', '2023-04-04', 4),
       ('name5', 'desc5', '2023-05-05', 5);

insert into comment (date, text, autorid, recipeid)
VALUES ('2023-01-01', 'text1', 1, 1),
       ('2023-02-02', 'text2', 2, 2),
       ('2023-03-03', 'text3', 3, 3),
       ('2023-04-04', 'text4', 4, 4),
       ('2023-05-05', 'text5', 5, 5);


insert into ingredient (name, type)
VALUES ('name1', 'HOP'),
       ('name2', 'HOP'),
       ('name3', 'MALT'),
       ('name4', 'YEAST'),
       ('name5', 'OTHER');

insert into added_to_like (userID, recipeid)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (3, 3),
       (3, 4),
       (3, 5),
       (4, 4),
       (4, 5),
       (5, 5);

insert into ingredient_list (amount, ingredientid)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),

       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),

       (3, 3),
       (3, 4),
       (3, 5),

       (4, 4),
       (4, 5),

       (5, 5);
