insert into authors (authorid, name, country, birthdate) values (1000, 'Лев Толстой', 'Россия', '1828-11-09');
insert into genres (genreid, name) values (100, 'Роман');
insert into books (bookid, name, authorid) values (1, 'Война и мир', 1000);
insert into books_genres (book_id, genre_id) values (1, 100);

insert into books (bookid, name, authorid) values (51, 'Анна Каренина', 1000);
insert into books_genres (book_id, genre_id) values (51, 100);

insert into authors (authorid, name, country, birthdate) values (1001, 'Терри Гудкайнд', 'США', '1948-05-01');
insert into genres (genreid, name) values (101, 'Фэнтези');
insert into books (bookid, name, authorid) values (52, 'Первое правило волшебника', 1001);
insert into books_genres (book_id, genre_id) values (52, 100);
insert into books_genres (book_id, genre_id) values (52, 101);