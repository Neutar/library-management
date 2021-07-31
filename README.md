# library-management
Library Management

### Infrastructure
This web project is built with Spring Boot, Postgresql and Docker. 
Postgresql is used as docker container so there is no need for database installment. 
Adminer is used for the database management tool.

### Application Structure
Application followed Service, Controller and Repository layers with business logic in the Domain objects.
Unit tests are created for each layer and Integration tests are created to test scenarios. 
Integration tests uses Test containers to make sure infrastructure with Postgresql works as expected.

### Running the Application

First you should change your directory in the terminal as the project directory "library-management" and type below script.
```
./library-management.sh
```
This script will run the maven clean and package with running all tests. And execute docker-compose up that will build image for the project, database, Then create containers and run them.

In order to open adminer you can use [adminer console link](http://localhost:9090/?pgsql=postgres_db&username=postgres&db=postgres&ns=library_management) with password as `123456`.
Password can be changed with passing `POSTGRES_PASS` environment variable. To make this effective, existing postgres container must be removed.

### Creating Initial Data
Initial data can be created with json files. 
Sample files can be found as `docker/initialBooks.json` and `docker/initialBooks.json`. 
To use create more you can either edit those files or create a file on your system and set `INITIAL_BOOKS_DATA_PATH` and `INITIAL_USERS_DATA_PATH` accordingly when running `library-management.sh` script.

### Endpoints

#### Initialize Test Data For Users And Books

```
curl --request POST \
--url http://localhost:8080/api/initialize
```

#### List all users
```
curl --request GET \
--url http://localhost:8080/api/user
```

#### List all books
```
curl --request GET \
--url http://localhost:8080/api/book
```

#### User can borrow a copy of a book
```
curl --request PUT \
--url http://localhost:8080/api/user/9ba08c7c-5dd5-421f-a74f-2b6b7255fb03/book/87929a3e-e38b-493f-b2f7-6e9a47227a98
```

* if the user try to exceed the borrow limit, the system returns a bad request with a message "BORROWING_LIMIT_EXCEEDED"
* if the user try to borrow a same, the system returns bad request with a message "ALREADY_BORROWED_SAME_BOOK"
* if user tru to borrow a copy of a book which is not found in the library, the system returns not found wtih a message "BOOK_NOT_FOUND"
* if the user is not exists, the system returns not found with a message "USER_NOT_FOUND"

#### User can return many books
```
curl --request POST \
--url http://localhost:8080/api/user/9ba08c7c-5dd5-421f-a74f-2b6b7255fb03/book/return \
--header 'Content-Type: application/json' \
--data '["cd663b2b-7dad-4e60-bddb-a51a8efa2c4d"]'
```

* if user try to return which is not in the user's borrowed list, the system returns bad request with a message "BOOK_NOT_BORROWED"
* if user tru to borrow a copy of a book which is not found in the library, the system returns not found wtih a message "BOOK_NOT_FOUND"
* if the user is not exists, the system returns not found with a message "USER_NOT_FOUND" 