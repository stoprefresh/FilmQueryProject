package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

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
		Film film;
		System.out.println("Welcome");
		System.out.println();
		System.out.println();

		while (stayInSystem == true) {
			
			System.out.println("Please make selection: ");
			System.out.println("0. Exit");
			System.out.println("1. By Film ID");
			System.out.println("2. By KeyWord");
			System.out.println("_______________________");
			
			
			selection = input.nextLine();
			
			
			switch (selection) {

			case "0":
				System.out.println("\nThank you.");
				System.out.println("Goodbye..");
				stayInSystem = false;
				break;
				
			case "1":
				System.out.println("Please enter a ID to search for a film:\n");
				try {
					film = db.findFilmById(input.nextInt());
					if (film == null) {
						System.out.println("ID selection does not exist.\n\n");
						break;
					} else {
						System.out.println(film + "\n\n");
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid Input\n\n");
					break;
				}
				
			case "2":
				System.out.println("Please enter a keyword you would like to search  with:\n");
				db.generalSearch(input.nextLine());
				System.out.println("\n\n");
				break;
				
			default:
				System.out.println("Enter a valid selection\n\n");
				break;
			}
			selection = input.nextLine();
		}
	}
}
