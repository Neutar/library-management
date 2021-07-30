alter table book
add column user_id uuid references library_user(id);

create index book_user_id on book(user_id);

