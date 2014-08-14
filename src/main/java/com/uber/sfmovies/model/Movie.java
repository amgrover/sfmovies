package com.uber.sfmovies.model;

import java.util.List;
/**
 * Wrapper class to hold movie info.
 * @author amangrover
 *
 */
public class Movie {
  private String title;
  private Integer releaseYear;
  private String location;
  private String funFacts;
  private String productionCompany;
  private String distributor;
  private String director;
  private String writer;
  private List<String> actors;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getFunFacts() {
    return funFacts;
  }

  public void setFunFacts(String funFacts) {
    this.funFacts = funFacts;
  }

  public String getProductionCompany() {
    return productionCompany;
  }

  public void setProductionCompany(String productionCompany) {
    this.productionCompany = productionCompany;
  }

  public String getDistributor() {
    return distributor;
  }

  public void setDistributor(String distributor) {
    this.distributor = distributor;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public List<String> getActors() {
    return actors;
  }

  public void setActors(List<String> actors) {
    this.actors = actors;
  }
}
