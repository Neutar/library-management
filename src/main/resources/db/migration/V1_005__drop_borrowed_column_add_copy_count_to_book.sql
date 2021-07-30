alter table book
add column copy_count numeric not null default 1;

alter table book
DROP COLUMN borrowed;

