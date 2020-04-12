insert into authors (authorid, name, country, birthdate) values (2000, 'Джордж Мартин', 'США', '1948-11-20');

insert into books (bookid, name, authorid) values (2, 'Игра престолов', 2000);
insert into books_genres (book_id, genre_id) values (2, 100);
insert into books_genres (book_id, genre_id) values (2, 101);

insert into books (bookid, name, authorid) values (3, 'Битва королей', 2000);
insert into books_genres (book_id, genre_id) values (3, 100);
insert into books_genres (book_id, genre_id) values (3, 101);

insert into authors (authorid, name, country, birthdate) values (2001, 'Алексей Пехов', 'Россия', '1978-03-30');
insert into books (bookid, name, authorid) values (4, 'Искатели ветра', 2001);
insert into books_genres (book_id, genre_id) values (4, 100);
insert into books_genres (book_id, genre_id) values (4, 101);

insert into books (bookid, name, authorid) values (5, 'Детство', 1000);
insert into genres (genreid, name) values (102, 'Повесть');
insert into books_genres (book_id, genre_id) values (5, 102);

insert into genres (genreid, name) values (103, 'Жанр для удаления');

insert into authors (authorid, name, country, birthdate) values (3001, 'Ноунейм', 'Россия', '1900-01-01');
