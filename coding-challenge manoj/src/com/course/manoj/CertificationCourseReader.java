package com.course.manoj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CertificationCourseReader {
	
	String line = "";
    String cvsSplitBy = ",";
    
    //Reading the courses csv into Map
    public Map<String, String> getCourseMap() {
    	
    	Map<String, String> courseMap = new HashMap<>();
    	String coursesFile = "C:/Users/Manoj/eclipse-workspace/coding-challenge manoj/bin/resources/courses.csv";
        
        try  {
        	BufferedReader br = new BufferedReader(new FileReader(coursesFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] course = line.split(cvsSplitBy);
                courseMap.put(course[0], course[1]);
            }

        }catch(FileNotFoundException e) {
        	e.printStackTrace();
        }
        	catch (IOException e) {
            e.printStackTrace();
        }
        
        return courseMap;
    }
    
  //Reading the Prerequisites csv into Set as there should not be any duplicates
    public Set<String> getPrerequisites(Set<String> prerequsitiesList, String inputCourse) {
    	
    	String prerequisitesFile = "C:/Users/Manoj/eclipse-workspace/coding-challenge manoj/bin/resources/prerequisites.csv";
    	
        try (BufferedReader br = new BufferedReader(new FileReader(prerequisitesFile))) {

            while ((line = br.readLine()) != null) {

                String[] prerequisite = line.split(cvsSplitBy);
                
                if(prerequisite[0].equals(inputCourse)){
                	prerequsitiesList.add(prerequisite[1]);
                	//calling itself to add the Prerequisites of Prerequisite
                	if(prerequsitiesList.size()<=9) {
                		getPrerequisites(prerequsitiesList, prerequisite[1]);
                	}
                }
            }

        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return prerequsitiesList;
    }
}
