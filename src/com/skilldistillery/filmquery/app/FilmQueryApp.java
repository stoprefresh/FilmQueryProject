package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() throws SQLException {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//  }

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	// This will be used for input logic and for calling methods from DAO
	private void startUserInterface(Scanner input) throws SQLException {
		boolean stayInSystem = true;
		String selection = "";
		System.out.println("Welcome");
		System.out.println();
		System.out.println();
		System.out.println("Would you like to search the database?");
		System.out.println("Enter 'y' for yes and 'n' for no");
		
		while (stayInSystem == true) {

			menuOptions();
			selection = input.nextLine();
			
			if(selection.contentEquals("0")) {
				System.out.println("\nThank you.");
				System.out.println("Goodbye..");
				stayInSystem = false;
			}
			else if(selection.contentEquals("1")) {
				System.out.println("Please enter a ID to search for a film:\n");
				db.findFilmById(input.nextInt());
				System.out.println("\n\n");
				stayInSystem = true;
			}
			else if (selection.contentEquals("2")) {
				System.out.println("Please enter a keyword you would like to search  with:\n");
				db.generalSearch(input.nextLine());
				System.out.println("\n\n");
				stayInSystem = true;
			}
			else {
				System.out.println("Enter a valid selection\n\n");
			}
		}
	}

	private void menuOptions() {
		System.out.println("Please make selection: ");
		System.out.println("0. Exit");
		System.out.println("1. By Film ID");
		System.out.println("2. By KeyWord");
		System.out.println("_______________________");
	}
}