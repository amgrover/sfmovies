package com.uber.sfmovies.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Core trie implementation. Leaf nodes and non leaf nodes that ends in a word, a set of movie
 * object is associated with the nodes
 * 
 * @author amangrover
 * 
 */
public class Node {
  public char c;
  private Set<Movie> movies;
  public Map<Character, Node> trieMap = new HashMap<Character, Node>();
  public boolean isEnd = false;

  /**
   * Insert a text in the trie. At the end it creates a set and add movie object to it
   * 
   * @param text
   * @param movie
   */
  public void insertString(String text, Movie movie) {
    // gets the first character and checks if there is already an existing node then it proceeds
    // else it creates a new one
    char a = text.charAt(0);
    if (trieMap.get(a) == null) {
      Node node = new Node();
      node.c = a;
      trieMap.put(a, node);
    }
    // if this the last character than create a set and add movie object to the set
    if (text.length() == 1) {
      Node node = trieMap.get(a);
      node.isEnd = true;
      Set<Movie> nodes = node.movies;
      if (nodes == null) {
        nodes = new LinkedHashSet<Movie>();
      }
      nodes.add(movie);
      node.movies = nodes;
    } else {
      trieMap.get(a).insertString(text.substring(1), movie);
    }
  }

  public void getByPrefix(Node trie, String prefix, Set<Movie> movies) {
    if (prefix.isEmpty()) {
      return;
    }
    char c = prefix.charAt(0);
    // if this is the last character of prefix then perform dfs on rest of the tree
    if (prefix.length() == 1) {
      if (trie.trieMap.get(c) != null) {
        dfs(trie.trieMap.get(c), movies);
      }
    } else {
      if (trie.trieMap.get(c) != null) {
        getByPrefix(trie.trieMap.get(c), prefix.substring(1), movies);
      }
    }
  }

  /**
   * Classical depth first search algorithm
   * 
   * @param trie
   * @param movies
   */
  private void dfs(Node trie, Set<Movie> movies) {
    Map<Character, Node> nodes = trie.trieMap;
    if (trie.isEnd) {
      movies.addAll(trie.movies);
    }
    for (Map.Entry<Character, Node> node : nodes.entrySet()) {
      Node tmp = node.getValue();
      if (tmp != null) {
        if (tmp.isEnd) {
          movies.addAll(tmp.movies);
          dfs(tmp, movies);
        } else {
          dfs(tmp, movies);
        }
      }
    }

  }



}
