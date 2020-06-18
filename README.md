# techhunt_employee_management
MVP of employee salary management

this project uses the following dependencies:
- mySQL 8.0.19
- maven
- Angular 9
- Java 8

## Assumptions
1) max length of each field in DB
2) ID is generated with format eXXXXXXXXX where X is between 0 to 9
3) Uploading of employee will be rejected if ID is not in above format

## Future Improvement
1) change database to Oracle DB to make use of Sequences
2) improve UI on file upload
3) check mime type of file instead of file extension when uploading of employees

## Setting It Up
### Database
1) Execute table_script.sql in mySQL
2) Edit JDBC settings in /EEMagmt/src/main/resources/application.properties

### Running Employee Management App
run the following commands in cmd "mvnw spring-boot:run"
