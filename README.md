# spring-boot-invoice-app-system
# Invoice Management System

Main objective of this project was managing Customers and their Invoices. Developed all type of CRUD operations, learning Spring Framework and Java in the process also how Hibernate works and the use of Thymeleaf and JQuery.

Technologies used: HTML, CSS, [Material Bootstrap](https://mdbootstrap.com/), JS, [Thymeleaf](https://www.thymeleaf.org/), [JQuery](https://jquery.com/), Java, [Springboot](https://spring.io/), [Hibernate](https://hibernate.org/) & SQL.

## Things todo list:
1. Clone this repository: `git clone https://github.com/hendisantika/spring-boot-invoice-app-system.git`
2. Navigate to the folder: `cd spring-boot-invoice-app-system`
3. Set Database credentials in application.properties.
4. Run the application: `mvn clean spring-boot:run`
5. Open your favorite browser: http://localhost:8080

## Features
This app has an auth system based on cookies and sessions. If you don't sign in, you can only see the customer list, but nothing else. If you log in with a ROLE_USER role, you will be able to view the customers' details, and if you log in as ROLE_ADMIN, you will have full CRUD rights, so you will be able to create, update and delete customers and their invoices.
* You can see some screenshots of the app in the /images folder.

## Language
The app's main language is English, but it is partially translated into Indonesia. You can change the language via the dropdown located in the navbar. The exported documents (pdf, xlxs) are translated based on the language configuration of the app.

## How to access

Heroku: https://invoice1demo1.herokuapp.com

* At first glance you have "no-user" role view.
* You can create a User by Signing up (You'll have User-Role privileges)
* Or access as admin to check the app out
    * u: admin
    * p: 12345

* If Signing-up as User does not work try (User-Role privileges):
    * u: naruto
    * p: 12345


