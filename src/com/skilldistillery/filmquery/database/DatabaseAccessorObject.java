package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private final String user = "student", pass = "student", url = "jdbc:mysql://localhost:3306/sdvid";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(url, user, pass);

		String sql = "SELECT id, title, description, release_year, language_id, rental_duration,"
				+ " rental_rate, length, replacement_cost, rating, special_features FROM film WHERE id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, filmId);

		ResultSet filmResult = pstmt.executeQuery();

		if (filmResult.next()) {
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
		}
		// ...

		filmResult.close();
		pstmt.close();
		conn.close();

		return film;

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
			actor.setId(actorResult.getInt(""));
			actor.setFirstName(actorResult.getString(""));
			actor.setLastName(actorResult.getString(""));
		}

		actorResult.close();
		pstmt.close();
		conn.close();

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) { // Make changes for this to work
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
//		    while (rs.next()) {
//		      int filmId = rs.getInt(1);
//		      String title = rs.getString(2);
//		      String desc = rs.getString(3);
//		      short releaseYear = rs.getShort(4);
//		      int langId = rs.getInt(5);
//		      int rentDur = rs.getInt(6);
//		      double rate = rs.getDouble(7);
//		      int length = rs.getInt(8);
//		      double repCost = rs.getDouble(9);
//		      String rating = rs.getString(10);
//		      String features = rs.getString(11);
//		      Film film = new Film(filmId, title, desc, releaseYear, langId,
//		                           rentDur, rate, length, repCost, rating, features);
//		      films.add(film);
//		    }
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

}
