if not exists (select 1 from users)
   begin
    insert into users(name, email, password)
    values('Jan Kowalski', 'jan@test.pl', 'haslo')
   end

if not exists (select 1 from book)
   begin
    insert into book(title, author, pages, price)
    values('ksiazka test', 'autor test', 100, 25.00)
   end