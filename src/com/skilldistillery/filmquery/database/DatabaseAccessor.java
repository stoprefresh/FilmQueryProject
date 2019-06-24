package com.skilldistillery.filmquery.database;

import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;

public interface DatabaseAccessor {
  public void findFilmById(int filmId) throws SQLException;
  public Actor findActorById(int actorId) throws SQLException;
  public List<Actor> findActorsByFilmId(int filmId);
  public void generalSearch(String nextLine) throws SQLException;
}
