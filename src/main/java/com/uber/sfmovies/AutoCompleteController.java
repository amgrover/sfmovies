package com.uber.sfmovies;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;
import com.uber.sfmovies.model.AutoCompleteKeyValue;

/**
 * Controller class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class AutoCompleteController {
  private AutoCompleteService autoCompleteService;

  public AutoCompleteController() {
    autoCompleteService = AutoCompleteService.getInstance();

  }

  /**
   * Method processing HTTP GET requests, producing "application/json" MIME media type.
   * 
   * @return String that will be send back as a response of type "text/plain".
   */
  @GET
  @Produces("application/json")
  public String getByPrefix(@QueryParam("term") String term) {
    List<AutoCompleteKeyValue> autoCompleteKeyValues =
        autoCompleteService.getAutoCompleteKeyValues(term);
    // return a json representation of the list.
    return new Gson().toJson(autoCompleteKeyValues);
  }
}
