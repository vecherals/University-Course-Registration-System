This project is a simple java application that simulates a basic university course registration system. 
students can search for sections, register gor a class, drop a class and view their GPA
Instructors can see their class roster and post grades.
Admins can generate sample data so the system can actually be tested.
I applied several OOP concepts such ad inheritance, interfaces and polymorphism.

Project Structure
The prohect is organized into  packages:
- com.university.model      -> classes like student, course, section
- com.university.service    -> logic for registration and grading
- com.university.repository -> data storage 
- com.university.ui         -> the main ConsoleApp 
- com.university.tests      -> automated test class

How to run the project?
1. import the project folder into eclipse
2. open "src/com/university/ui/ConsoleApp.java"
3. right-click and select "run as java application"

note: since everything is stored in RAM, the system must be tested in the correct order

Step 1: generate data
go to the main menu
select ADMIN MENU (3)
then select GENERATE SAMPLE DATA(1)
this will create students, instructors and courses

Step 2: register a student
go back to MAIN MENU
select STUDENT MENU (1)
enter student ID: 240201001
choose REGISTER, then enter section ID CS2001-A

Step 3: post a grade
back to MAIN MENU
select INSTRUCTOR MENU (2)
enter instructor ID: I1
choose POST GRADE
section: CS2001-A
student: 240201001
grade: A

Step 4: view transcript
go to STUDENT MENU 
log in with: 240201001
select VIEW TRANSCRIPT to check the GPA calculation

Testing
this project also includes a simple automaed test class
open src/com/university/tests/UniversitySystemTest.java
run it as a java application

it runs 10 different tests and prints "passed" for each succesfull test
