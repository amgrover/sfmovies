import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.uber.sfmovies.AutoCompleteService;
import com.uber.sfmovies.model.AutoCompleteKeyValue;
import com.uber.sfmovies.model.Movie;

/**
 * Test class to test positive and negative test cases
 * 
 * @author amangrover
 * 
 */
public class AutoCompleteTest {

  private AutoCompleteService autoCompleteService = AutoCompleteService.getInstance();

  @Before
  public void setup() {
    Movie movie = new Movie();
    movie.setTitle("A Jitney Elopement");
    Movie movie2 = new Movie();
    movie2.setTitle("After the Thin Man");
    Movie movie3 = new Movie();
    movie3.setTitle("The Big Bang Theory");
    List<Movie> movies = new ArrayList<Movie>();
    movies.add(movie);
    movies.add(movie2);
    movies.add(movie3);
    autoCompleteService.populateData(movies);
  }

  @Test
  public void testGetByPrefix() {
    Set<Movie> output = autoCompleteService.findByPrefix("The");
    Assert.assertEquals(output.size(), 2);
    Set<Movie> outputOne = autoCompleteService.findByPrefix("Theo");
    Assert.assertEquals(outputOne.size(), 1);
    Set<Movie> outputTwo = autoCompleteService.findByPrefix("A");
    Assert.assertEquals(outputTwo.size(), 2);
  }

  @Test
  public void testEmptyTerm() {
    List<AutoCompleteKeyValue> autoCompleteKeyValues =
        autoCompleteService.getAutoCompleteKeyValues(null);
    Assert.assertEquals(autoCompleteKeyValues.size(), 0);
    List<AutoCompleteKeyValue> autoCompleteKeyValues2 =
        autoCompleteService.getAutoCompleteKeyValues("");
    Assert.assertEquals(autoCompleteKeyValues2.size(), 0);
  }

  @Test
  public void testMultipleTerm() {
    List<AutoCompleteKeyValue> autoCompleteKeyValues =
        autoCompleteService.getAutoCompleteKeyValues("the bi");
    Assert.assertEquals(autoCompleteKeyValues.size(), 1);
    Assert.assertEquals(autoCompleteKeyValues.get(0).getLabel(), "The Big Bang Theory");
  }
}
