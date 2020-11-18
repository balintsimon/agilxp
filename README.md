# AgileXpert Homework

## About this project
This project is a possible solution to the problem posted on AgileXpert.hu's career website. 
It describes a client-server architecture OS that drives some sort of device, while keeping all data
and computation in the cloud. The problem, however, is a bit vague on details, so each applicant must
fill in the details themselves.

## Tasks
There are five tasks altogether (just a rough description):
1. Create an OOP model in Java as a possible solution to the problem. Use List for one-to-many and many-to-many 
connections and "custom text identifiers" for each object.
1. Add JPA annotation to the entities
1. Create a console application that adds an admin on the back-end (should use MAVEN) - "1st stage"
1. Modify the application so that it adds users to the group and customizes their menus, runs applications, etc. - 
"2nd stage"
1. Add a table-based display on the console that shows the results of the foregoing. - "3rd stage"

## Solution
The solution consists of two modules:
1. "server": stores all the data, runs applications, etc.
1. "client": initiates changes on the server + houses the logic for formatting and displaying the table

The three stages on the frontend described in the tasks are ran automatically by the Command Line runner when the 
client is started.

In this solution the two parts communicate through API (partially complies to REST specifications). This may not 
have been necessary, but the problem hinted at that server stores data in the cloud, so a web service seemed necessary.

The problem does not describe how each functionality should be divided between the two modules. However, as it hints 
that data and logic should reside on the server, thus applications are ran on the server. This may not be a viable
solution in real life applications.

NB the following:
- The problem did not specifically state that an admin is needed. However, it can be derived from the example that
in case of a family, the father can add all the members to the user group.
- Use of security (Spring Boot security, JWT, password protection, etc.) was not required.
- I made the assumption that one user can only be a member of one group and that the creator of a group is the admin.
- "Arculat" was not explained in any way, as such I translated it to "Theme" and used it accordingly.

## Technology
Technologies used in creating the services:
 - Spring Boot with Java11 (the problem specified Java, but did not say to use SE specifically)
 - Lombok
 - JPA
 - H2 database (NB: database uses default port, user name and password)
 
 ## How to Start the Project
 Please start server first. When it is up and running, you can run the client to initiate the tests.
 Both modules use Maven.
