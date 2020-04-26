insert into authors (name, country, birth_date) values ('Лев Толстой', 'Россия', '1828-11-09');
insert into authors (name, country, birth_date) values ('Терри Гудкайнд', 'США', '1948-05-01');
insert into authors (name, country, birth_date) values ('Джордж Мартин', 'США', '1948-11-20');
insert into authors (name, country, birth_date) values ('Алексей Пехов', 'Россия', '1978-03-30');

insert into genres (name) values ('Роман');
insert into genres (name) values ('Фэнтези');
insert into genres (name) values ('Повесть');

insert into books (name, author_id, genre_id) values ('Война и мир', 1, 1);
insert into books (name, author_id, genre_id) values ('Анна Каренина', 1, 1);
insert into books (name, author_id, genre_id) values ('Игра престолов', 3, 2);
insert into books (name, author_id, genre_id) values ('Битва королей', 3, 2);
insert into books (name, author_id, genre_id) values ('Первое правило волшебника', 2, 2);
insert into books (name, author_id, genre_id) values ('Искатели ветра', 4, 2);

insert into books_comments (name, author_id, book_id) values ('Плагиат чистой воды!', 3, 5);
insert into books_comments (name, author_id, book_id) values ('Моя прелесть!', 4, 5);
insert into books_comments (name, author_id, book_id) values ('Неплохая книга', 2, 5);