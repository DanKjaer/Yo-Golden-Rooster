package BLL.util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : searchBase) {
            if(compareTitle(query, movie) || compareRating(query, movie) || compareCategory(query, movie)) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    private boolean compareRating(String query, Movie movie) {
        try{
            float queryNumber = Float.parseFloat(query);
            return movie.getRating() >= queryNumber;
        }catch(Exception e){
            return false;
        }

    }

    private boolean compareTitle(String query, Movie movie) {
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareCategory(String query, Movie movie) {
        return movie.getCategories().toLowerCase().contains(query.toLowerCase());
    }
}
