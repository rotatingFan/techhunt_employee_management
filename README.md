# techhunt_employee_management
MVP of employee salary management

this project uses the following dependencies:
- mySQL 8.0.19
- maven
- Angular 9
- Java 8

## Assumptions
1) Max length of each field in DB
2) ID is generated with format eXXXXXXXXX where X is between 0 to 9
3) Uploading of employee will be rejected if ID is not in above format

## Future Improvement
1) Change database to Oracle DB to make use of Sequences
2) Improve UI on file upload
3) Check mime type of file instead of file extension when uploading of employees
4) Improve web responsive
5) Change all message to a properties file and pass in language in header so cater for user story 5
6) Display which line of data fail validation (data already passed to frontend in data field)
7) Add filter by name,id,login

## Setting It Up
### Database
1) Execute table_script.sql in mySQL
2) Edit JDBC settings in /EEMagmt/src/main/resources/application.properties

### Running Employee Management App
1) Run the following commands in cmd "mvnw spring-boot:run"
2) Navigate to following webpage http://localhost:8080/index.html