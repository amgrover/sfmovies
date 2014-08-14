package com.uber.sfmovies.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to hold movie title and location where it is being screened
 * 
 * @author amangrover
 * 
 */
public class AutoCompleteKeyValue {
  private String label;
  private List<String> locations;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<String> getLocations() {
    return locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  public void addLocation(String location) {
    if (locations == null) {
      locations = new ArrayList<String>();
    }
    locations.add(location);
  }

}
