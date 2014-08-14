package com.uber.sfmovies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uber.sfmovies.csv.CsvProcessor;
import com.uber.sfmovies.model.AutoCompleteKeyValue;
import com.uber.sfmovies.model.Movie;
import com.uber.sfmovies.model.Node;

/**
 * Service class responsible for building trie, Act as a middle layer between controller and our in
 * memory dictionary trie.
 * 
 * @author amangrover
 * 
 */
public class AutoCompleteService {
  // Trie node asscoiated with the class
  private Node node = new Node();
  private static AutoCompleteService autoCompleteService = new AutoCompleteService();
  private Map<String, Set<Movie>> nameVal = new HashMap<String, Set<Movie>>();
  private static final String SF_FILE_LOCATION = "/Film_Locations_in_San_Francisco.csv";
  // loads the trie in memory
  static {
    autoCompleteService.loadTrie();
  }

  /**
   * Singleton pattern to avoid creating different instances to this object.
   */
  private AutoCompleteService() {

  }

  /**
   * Returns the instance of this object
   * 
   * @return
   */
  public static AutoCompleteService getInstance() {
    return autoCompleteService;
  }

  /**
   * Utility method to load trie in memory
   */
  private void loadTrie() {
    List<Movie> movies = new CsvProcessor().readFile(SF_FILE_LOCATION);
    populateData(movies);
  }

  /**
   * Returns movie object that has the same title
   * 
   * @param title
   * @return
   */
  public Set<Movie> getMoviesByName(String title) {
    return nameVal.get(title);
  }

  /**
   * Synchronized method to populate insert values in trie Note that we are not creating new movie
   * object anywhere apart from CsvProcessor. Everything is a reference
   * 
   * @param movies
   */
  public synchronized void populateData(List<Movie> movies) {
    for (Movie movie : movies) {
      // split title by space.
      String[] title = movie.getTitle().split("\\s+");
      // for each term in the title insert it in the trie
      for (String tmp : title) {
        node.insertString(tmp.toUpperCase(), movie);
      }
      // map to store movies associated with the same title
      if (!nameVal.containsKey(movie.getTitle())) {
        Set<Movie> list = new HashSet<Movie>();
        nameVal.put(movie.getTitle(), list);
      }
      nameVal.get(movie.getTitle()).add(movie);
    }
  }

  /**
   * Finds the movies that are associated with a prefix
   * 
   * @param text
   * @return
   */
  public Set<Movie> findByPrefix(String text) {
    Set<Movie> movies = new LinkedHashSet<Movie>();
    node.getByPrefix(node, text.toUpperCase(), movies);
    return movies;
  }

  /**
   * The overall idea is to to parse user input by space and iterate over string tokens and find set
   * of movies for each token. And find the intersection of the returned sets.
   * 
   * @param term
   * @return
   */
  public List<AutoCompleteKeyValue> getAutoCompleteKeyValues(String term) {
    if (term == null) {
      // return empty arraylist
      return new ArrayList<AutoCompleteKeyValue>();
    }
    if (term.isEmpty()) {
      // if the term is empty string return empty list
      return new ArrayList<AutoCompleteKeyValue>();
    }
    String[] terms = term.split("\\s+");
    Set<String> ouput = new LinkedHashSet<String>();
    // temporary set to hold intermediate intersecting results from various sets.
    Set<String> result = new LinkedHashSet<String>();
    // flag to differentiate between first term and rest of the terms
    boolean first = true;
    for (String string : terms) {
      Set<Movie> movies = findByPrefix(string);
      // if this is the first term then add the results in the output set.
      if (first) {
        for (Movie movie : movies) {
          ouput.add(movie.getTitle());
        }
        first = false;
      } else {
        // for rest of the terms see if we have already seen movie title then add it else do nothing
        for (Movie movie : movies) {
          if (ouput.contains(movie.getTitle())) {
            result.add(movie.getTitle());
          }
        }
        // once we are done, In the result set we will have common movie title for the terms visited
        // so far.
        ouput.clear();
        ouput.addAll(result);
        // re-initialize the result set.
        result.clear();
      }
    }
    // Each row in a excel makes a movie object. Now there will be multiple movie object for the
    // same movie since the location is different.
    // wrapper class to hold movie title and various location associated with it
    List<AutoCompleteKeyValue> autoCompleteKeyValues = new ArrayList<AutoCompleteKeyValue>();
    for (String string : ouput) {
      // we get the set of movies that have same title
      Set<Movie> movies = getMoviesByName(string);
      AutoCompleteKeyValue autoCompleteKeyValue = new AutoCompleteKeyValue();
      autoCompleteKeyValue.setLabel(string);
      // iterate over movie and add the location in an arraylist
      for (Movie movie : movies) {
        autoCompleteKeyValue.addLocation(movie.getLocation());
      }
      autoCompleteKeyValues.add(autoCompleteKeyValue);
    }
    return autoCompleteKeyValues;
  }
}
