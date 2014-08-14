package com.uber.sfmovies.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.uber.sfmovies.model.Movie;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Csv Reader to read data from csv and create a list of movies.
 * 
 * @author amangrover
 * 
 */
public class CsvProcessor {
 /**
  * Reads a csv file and return list of movie objects
  * @param file
  * @return
  */
  public List<Movie> readFile(String file) {
    List<Movie> movies = new ArrayList<Movie>();
    try {
      InputStreamReader inputStreamReader =
          new InputStreamReader(getClass().getResourceAsStream(file));
      CSVReader reader = new CSVReader(inputStreamReader);
      String[] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        Movie movie = new Movie();
        movie.setTitle(nextLine[0]);
        if (nextLine[1].equalsIgnoreCase("Twentieth Century Fox Film Corporation")) {
          System.out.println();
        }
        movie.setReleaseYear(Integer.parseInt(nextLine[1]));
        movie.setLocation(nextLine[2]);
        movie.setFunFacts(nextLine[3]);
        movie.setProductionCompany(nextLine[4]);
        movie.setDirector(nextLine[5]);
        movie.setWriter(nextLine[6]);
        List<String> actors = new ArrayList<String>();
        actors.add(nextLine[7]);
        actors.add(nextLine[8]);
        actors.add(nextLine[9]);
        movie.setActors(actors);
        movies.add(movie);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return movies;
  }
}
