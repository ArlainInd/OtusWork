insert into authors (name, country, birth_date) values ('Лев Толстой', 'Россия', '1828-11-09');
insert into authors (name, country, birth_date) values ('Терри Гудкайнд', 'США', '1948-05-01');

insert into genres (name) values ('Роман');
insert into genres (name) values ('Фэнтези');
insert into genres (name) values ('Повесть');

insert into books (name, author_id, genre_id) values ('Война и мир', 1, 1);
insert into books (name, author_id, genre_id) values ('Анна Каренина', 1, 1);
