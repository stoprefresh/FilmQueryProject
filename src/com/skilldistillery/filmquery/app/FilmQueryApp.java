package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) throws SQLException {
    FilmQueryApp app = new FilmQueryApp();
    app.test();
    app.launch();
  }

  private void test() throws SQLException {
    Film film = db.findFilmById(1);
    System.out.println(film);
  }

  
  private void launch() throws SQLException {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  // This will be used for input logic and for calling methods from DAO
  private void startUserInterface(Scanner input) throws SQLException {
	boolean stayInSystem = true;
	String searchWord = "", selection = "";  
    
    System.out.println("Welcome");
    System.out.println("");
    System.out.println("");
    
    
    while(stayInSystem == true) {
    	System.out.println("Please make selection: ");
    	System.out.println("0. Exit");
    	System.out.println("1. By Film ID");
    	System.out.println("2. By KeyWord");
    	System.out.println("_______________________");
    	
    	try {
    		searchWord = input.nextLine();
    	}
    	catch(InputMismatchException e) {
    		System.out.println("That is not valid");
    	}
    	 
    	switch(searchWord) {
    	
    	case "1":
    		System.out.println("Please enter a ID to search for a film:\n");
    		try {
    			Film film = db.findFilmById(input.nextInt());
    			System.out.println(film);
    		}
    		catch(InputMismatchException e) {
    			System.out.println("Invalid Input");
    		}
    		break;
    	case "2":
    		System.out.println("Please enter a keyword you would like to search  with:\n");
    		selection = input.nextLine();
    		
    		break;
    	case "0":
    		System.out.println("Thank you.");
    		System.out.println("Goodbye..");
    		stayInSystem = false;
    		break;
    	default:
    		
    		
    		
    		
    	}
    	
    	
    	
    }
    
	  
	  
	  
	  
	  
  }

}
