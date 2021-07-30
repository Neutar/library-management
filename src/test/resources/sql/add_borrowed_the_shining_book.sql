insert into library_management.book (id,name,author,isbn,copy_count)
values ('23b62398-82b4-4d81-8704-343e8388fdab', 'The Shining', 'Stephen King', '9783785746042', 0);

insert into library_management.library_user_book (library_user_id, book_id)
values ('eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e', '23b62398-82b4-4d81-8704-343e8388fdab');
