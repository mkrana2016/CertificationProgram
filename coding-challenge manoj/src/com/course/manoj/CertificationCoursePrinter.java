package com.course.manoj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CertificationCoursePrinter {

	public void printCourseOrder(Map courseMap , Set<String> prerequsitiesList, String inputCourse ) {
		
		System.out.println("The order in which you should be taking the courses for"
        		+ "  \"" + courseMap.get(inputCourse)+"\"");
        
        List<Integer> finalCourseList = new ArrayList<>();
        
        int value = 0;
        for(String s : prerequsitiesList) {
        	finalCourseList.add(Integer.parseInt(s));
        }
        
        //sorting and printing the list of courses
        //Collections.sort(finalCourseList);
        if(finalCourseList.size()==0) {
        	System.out.println("No Prerequisites for this course");
        	return;
        }
        	
        for(Integer course : finalCourseList) {
        	System.out.println( course+ " - " + courseMap.get(String.valueOf(course)));
        }
	}
	
}
