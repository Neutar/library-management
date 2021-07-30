insert into library_management.book (id,name,author,isbn,copy_count)
values ('4ddf3d5e-9ed3-4c70-9438-31ed05404256', 'Harry Potter and the Goblet of Fire', 'J. K. Rowling', '9780439139595', 0);

insert into library_management.library_user_book (library_user_id, book_id)
values ('eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e', '4ddf3d5e-9ed3-4c70-9438-31ed05404256');
