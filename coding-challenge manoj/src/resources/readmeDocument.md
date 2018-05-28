Coding Challenge Solution Description
===============================
Requirements :
An online education provider provide "Software development certification" which requires 12 modules to be completed.
In order to enroll for any module, you must have completed it's all pre-requisite modules.
Modules may or may not have pre-requisites.

Write a program that, given a list of courses and their pre-requisites, produces a possible order in which you may complete as many of the provided course units as possible, adhering to the pre-requisite requirements.
 
Resources provided:
  1. A comma-seperated file containing course titles and unique ids, "courses.csv".
  2. A comma-seperated file containing course pre-requisites, by id, "prerequisites.csv".
  3. The problem description, "readme.md".
 
 Design Description :
 1. "CertificationCourseMain.java" containing the main method.
 2. "UserInputReader.java" to get the input from the user.
 3. "CertificationCourseReader.java" to read the csv files.
 4. "CertificationCoursePrinter.java" to print the modules order.
 
 Solution Description :
 1. The program will take an input parameter as the couseID
 2. If the couseID is not valid ask the user for the correct courseID
 3. If the couseID is valid then read the "courses.csv" into a HashMap.
 4. We will be storing "courses.csv" into a HashMap as we need the 	collection to be storing "ID and Name of the course" as key-value 	pair.
 5. We will be reading  "prerequisites.csv" into HashSet<> as we don't 	want any duplicates here.
  
 
 Challenges :
 1. Final prerequisites order( example : 10 after 8)
 2. Had to limit the prerequisites size (because of 4,5,9 modules)