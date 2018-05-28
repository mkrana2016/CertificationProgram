package com.course.manoj;

import java.util.Scanner;

public class UserInputReader {

		public String getUserInput() {
				
				Scanner input = new Scanner(System.in);
				String inputCourse = "";
				int inputValue = 0;
				try {
						do {
					    	System.out.println("Please enter the course id from 1 to 12");
					    	inputCourse = input.nextLine();
					    	inputValue = Integer.parseInt(inputCourse);
						}while( inputValue < 1 || inputValue > 12);
					
					}catch(NumberFormatException e) {
						System.out.println("Invalid Course Id");
					}finally{
						input.close();
					}
				
		    	return inputCourse;
		    	
		}
}
