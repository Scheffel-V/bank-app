# bank-app
## A fictional Bank Application.

### Backend
The backend REST API was developed using **Java**, with **Spring Framework**, **Spring Boot** and **Hibernate**. The database uses **H2**. The authentication system uses **JWT**.
The API documentation can be found here:
[Bank Application API](https://documenter.getpostman.com/view/5250307/S1TYWGuU)

### Frontend
The frontend was developed using **Angular 7**.

### Comments
There is three Users already created in the database, with Accounts and Transactions. You can log in with "testUsername" and "testPassword", or create a new User. POST requests return in the message header the "Location" attribute, which is the address of the entity just created. GET, PUT, and DELETE requests, when they can not find the object with the ID, return a "404 Not Found". All requests, except for "POST" in "authenticate" and "users" are protected by an authentication system that uses JWT.

### Todo
It is still possible to improve the system, such as taking care of circular references in objects, so that their respective JSONs are not so large. The updates of the objects through the PUT method are not complete, and do not update all the attributes of the object, only the essential ones, and this could be done. In addition, the frontend logic can be improved to access the backend less often. It is also possible to make the frontend more beautiful, readable and functional.

### Run
#### Backend
To run the backend, you will need to install Java and Maven. Then,run the following commands inside the root directory of the repository:
```
cd backend
mvn spring-boot:run
```
The backend server will be running on: 
```
http://127.0.0.1:8080/
```

#### Frontend
To run the frontend, you will need to install NodeJS (> 8.9) and Angular7. Then,run the following commands inside the root directory of the repository:
```
cd frontend
cd bank-app
npm install
ng serve
```
The frontend server will be running on: 
```
http://127.0.0.1:4200/
```
