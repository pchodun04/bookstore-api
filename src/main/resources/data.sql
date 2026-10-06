if not exists (select 1 from users) insert into users(name, email, password) values('Jan Kowalski', 'jan@test.pl', 'haslo')

if not exists (select 1 from book) insert into book(title, author, pages, price) values('ksiazka test', 'autor test', 100, 25.00)
