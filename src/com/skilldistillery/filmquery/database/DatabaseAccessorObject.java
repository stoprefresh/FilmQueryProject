package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private final String user = "student", pass = "student", url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void generalSearch(String searchWord) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(url, user, pass);

		String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, f.rental_duration,"
				+ " f.rental_rate, f.length, f.replacement_cost, f.rating, f.special_features, l.name FROM film f"
				+ " JOIN language l on l.id = f.language_id WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, "%" + searchWord + "%");
		pstmt.setString(2, "%" + searchWord + "%");

		ResultSet filmResult = pstmt.executeQuery();

		while (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setLangId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeatures(filmResult.getString("special_features"));
			film.setLanguage(filmResult.getString("l.name"));

			if (film != null) {
				System.out.println("\n---------------");
				System.out.println(film);
				System.out.println("Credits: ");
				film.setFilmActors(findActorsByFilmId(film.getId()));
				System.out.println("---------------\n");
			}
			
		}
		filmResult.close();
		pstmt.close();
		conn.close();
	}

	public void findFilmById(int filmId) throws SQLException {

		Film film = null;
		Connection conn = DriverManager.getConnection(url, user, pass);

		String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, f.rental_duration,"
				+ " f.rental_rate, f.length, f.replacement_cost, f.rating, f.special_features, l.name FROM film f"
				+ " JOIN language l on l.id = f.language_id WHERE f.id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, filmId);

		ResultSet filmResult = pstmt.executeQuery();

		if (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("f.id"));
			film.setTitle(filmResult.getString("f.title"));
			film.setDescription(filmResult.getString("f.description"));
			film.setReleaseYear(filmResult.getInt("f.release_year"));
			film.setLangId(filmResult.getInt("f.language_id"));
			film.setRentalDuration(filmResult.getInt("f.rental_duration"));
			film.setRentalRate(filmResult.getDouble("f.rental_rate"));
			film.setLength(filmResult.getInt("f.length"));
			film.setReplacementCost(filmResult.getDouble("f.replacement_cost"));
			film.setRating(filmResult.getString("f.rating"));
			film.setSpecialFeatures(filmResult.getString("f.special_features"));
			film.setLanguage(filmResult.getString("l.name"));
		}

		filmResult.close();
		pstmt.close();
		conn.close();

		if (film != null) {
			System.out.println("\n---------------");
			System.out.println(film);
			System.out.println("Credits: ");
			film.setFilmActors(findActorsByFilmId(filmId));
			System.out.println("---------------\n");
		} else {
			System.out.println("ID selection does not exist.\n\n");
		}
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(url, user, pass);

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, actorId);
		ResultSet actorResult = pstmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor();
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
		}

		actorResult.close();
		pstmt.close();
		conn.close();

		return actor;
	}

	// Pull in Actor method to generate the array of Actors accociated with the movie
	public List<Actor> findActorsByFilmId(int filmId) { 
		List<Actor> filmActors = new ArrayList<>();
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select a.first_name, a.last_name, a.id "
					+ "from film f join film_actor fa on fa.film_id = f.id "
					+ "join actor a on a.id = fa.actor_id where f.id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet actorByFilmResult = pstmt.executeQuery();

			while (actorByFilmResult.next()) {
				if (actorByFilmResult.next()) {
					actor = new Actor();
					actor.setId(actorByFilmResult.getInt("a.id"));
					actor.setFirstName(actorByFilmResult.getString("a.first_name"));
					actor.setLastName(actorByFilmResult.getString("a.last_name"));
					filmActors.add(actor);
					System.out.println(actor);
				}
			}
			actorByFilmResult.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmActors;
	}
}
