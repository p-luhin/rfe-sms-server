# RFE Sms Server
RFE Sms Server - is a simple application that provides api to send sms notifications. The main difference between this and other existing applications is that this one were created especially for Belarusian State University projects, such as https://cv.bsu.by and other internal ones. Under the hood this application using http://websms.by service.
This application contains users management system, ability to send sms directly from server or from external applications, collecting statistics of sent sms, duplicating sms messages to email.

# Getting Started
To start application you need to get Git, JRE that provides Java 8, Apache Maven at least 3.2 version, Apache Tomcat at least 8.0 version and MySQL at least 5.6 version.
First, you need to clone this repository.
```
git clone git@github.com:p-luhin/rfe-sms-server.git
```
Then you have to step into the cloned directory and specify branch you want to deploy. The latest stable version will always be in master branch that is active by default, but you can specify any branch you want.
Then you have to build application in web-application archive (war) file. To do this you need from project directory specify the following command
```
mvn clean install -Pprod
```
This will compile and pack all project files in war file.
The project contains two profiles: ```prod``` and ```local```. The main difference is that production profile sends requests directly to websms server, and local one returns random or predefined data.
After installing, you have to copy file war file named ```sms-server-X.war```, where X - application version from ```target``` folder to ```webapp``` folder of tomcat.
Next you have to create database schema and user for it.
```
CREATE DATABASE sms_server;
CREATE USER 'sms-server-user'@'localhost' IDENTIFIED BY 'some-password';
GRANT ALL ON sms_server.* TO 'sms-server-user'@'localhost';
FLUSH PRIVILEGES;
```
This set of mysql commands will create database schema, user and grant all access to created user.
Then you need to specify the following environment variables for the application:
* ```-Ddatabase.url``` - url of database schema. In this example ```jdbc:mysql://localhost:3306/sms_server```
* ```-Ddatabase.username``` - username of database user. In this example ```sms-server-user```
* ```-Ddatabase.password``` - password of database user. In this example ```some-password```
* ```-Dsms.test``` - in older versions test flag, send real or mock request to websms system. Deprecated. Use build profiles insted.
* ```-Dserver.url``` - server domain or IP address with port where application is going to be deployed. Needed to send in email to newly registered users. 
