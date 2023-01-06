package BLL.util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : searchBase) {
            if(compareTitle(query, movie) || compareRating(query, movie)) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    private boolean compareRating(String query, Movie movie) {
        return Float.toString(movie.getRating()).contains(query);
    }

    private boolean compareTitle(String query, Movie movie) {
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

}
