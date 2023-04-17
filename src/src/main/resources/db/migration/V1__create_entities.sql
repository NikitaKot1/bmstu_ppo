CREATE TABLE if not exists manufacturer
(
    id       int generated always as identity primary key,
    login    text not null,
    password text not null
);

CREATE TABLE if not exists consumer
(
    id       int generated always as identity primary key,
    login    text not null,
    password text not null
);

create table if not exists mark
(
    id          int generated always as identity primary key,
    name        text      not null,
    description text      not null,
    date        timestamp not null,
    ownerid     int,
    constraint fk_owner foreign key (ownerid) references manufacturer (id)
);

create table if not exists comment
(
    id       int generated always as identity primary key,
    date     timestamp not null,
    text     text      not null,
    autorid  int,
    recipeid int,
    constraint fk_autor foreign key (autorid) references consumer (id),
    constraint fk_recipe foreign key (recipeid) references mark (id)
);

CREATE TYPE ing_type AS ENUM ('YEAST', 'MALT', 'HOP', 'OTHER');

create table if not exists ingredient
(
    id   int generated always as identity primary key,
    name text     not null,
    type ing_type not null
);

create table if not exists added_to_like
(
    id       int generated always as identity primary key,
    userid   int,
    recipeid int,
    constraint fk_autor foreign key (userid) references consumer (id),
    constraint fk_recipe foreign key (recipeid) references mark (id)
);


create table if not exists ingredient_list
(
    id           int generated always as identity primary key,
    amount       int not null check ( amount > 0 ),
    ingredientid int,
    constraint fk_ingredient foreign key (ingredientid) references ingredient (id)
)