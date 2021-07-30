create table library_user_book (
	library_user_id uuid not null,
	book_id uuid not null,
	PRIMARY KEY (library_user_id, book_id));

create index library_user_book_library_user_id_ix
on library_user_book(library_user_id);

insert into library_user_book (library_user_id, book_id)
select user_id, id
from book where user_id is not null;

alter table book
drop column user_id;