package com.course.manoj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CertificationCourseMain {

	public static void main(String[] args) {

    	Map<String,String> courseMap = new HashMap<>();
    	Set<String> prerequsitiesList = new HashSet<>();
    	
    	//get the user input.
    	UserInputReader inputReader = new UserInputReader();
    	String inputCourse = inputReader.getUserInput();
    	
    	CertificationCourseReader courseReader = new CertificationCourseReader();
    	//Reading the csv files into map and set.
    	courseMap = courseReader.getCourseMap();
    	prerequsitiesList = courseReader.getPrerequisites(prerequsitiesList, inputCourse);
        
    	//Printing the prerequsites for the selected course
    	CertificationCoursePrinter coursePrinter = new CertificationCoursePrinter();
    	coursePrinter.printCourseOrder(courseMap, prerequsitiesList, inputCourse);
        
    }

}
