package BLL;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {
    public List<Movie> search(List<Movie> searchBase, String query){
        List<Movie> searchResult = new ArrayList<>();
        
        for(Movie movie : searchBase){
            if(compareMovieName(query, movie) || compareMovieRating(query, movie))
            {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }
    
    private boolean compareMovieName(String query, Movie movie){
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }
    
    private boolean compareMovieRating(String query, Movie movie){
        String mRating = String.valueOf(movie.getRating());
        return Boolean.parseBoolean(mRating);
    }
}

