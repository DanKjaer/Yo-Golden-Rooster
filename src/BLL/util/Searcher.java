package BLL.util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Searcher {
    /**
     * The main search method which is called from MovieManager.
     * It goes through a list of movies, and the query from the user.
     * @param searchBase - Movie list that the user want to search.
     * @param query - The search text which the user provides in the text field.
     * @return - The result from the search.
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        //Creating List to house the results in the For loop and return later.
        List<Movie> searchResult = new ArrayList<>();

        //Go through every movie in the searchBase, and adds to result if a condition is met.
        for (Movie movie : searchBase) {
            if(compareTitle(query, movie) || compareRating(query, movie) || compareCategory(query, movie)) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    /**
     * Check if rating matches the query
     * @param query - Query provided by user.
     * @param movie - The movie the foreach loop have reached.
     * @return - True if the query matches a rating.
     */

    private boolean compareRating(String query, Movie movie) {
        try{
            float queryNumber = Float.parseFloat(query);
            return movie.getRating() >= queryNumber;
        }catch(Exception e){
            return false;
        }
    }

    /**
     *Check if title matches the query
     * @param query - Query provided by user.
     * @param movie - The movie the foreach loop have reached.
     * @return - True if the query matches a movie title.
     */
    private boolean compareTitle(String query, Movie movie) {
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     *Check if category matches the query
     * @param query - Query provided by user.
     * @param movie - The movie the foreach loop have reached.
     * @return - True if the query matches a category.
     */
    private boolean compareCategory(String query, Movie movie) {
        return movie.getCategories().toLowerCase().contains(query.toLowerCase());
    }
}
