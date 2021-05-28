
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String movieFile, String ratingsFile){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(movieFile);
        myRaters = fr.loadRaters(ratingsFile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters){
        double sumRatings = 0.0;
        int numRatersFound = 0;
        // goes through the rater list, if a rater has rated the movie
        // count them and add them to the running total of ratings
        for(Rater rater: myRaters){
            if(rater.hasRating(id)){
                numRatersFound += 1;
                sumRatings += rater.getRating(id);
            }
        }
        
        // if the num of raters found greater than or 
        // equal to minimal raters
        if(numRatersFound >= minimalRaters){
            return sumRatings / numRatersFound;
        }
        
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        for(Movie movie: myMovies){
            String movieId = movie.getID();
            double averageRating = getAverageByID(movieId, minimalRaters);
            if(averageRating > 0.0){
                ratings.add(new Rating(movieId, averageRating));
            }
        }
        return ratings;
    }
    
    public String getTitle(String id){
        for(Movie movie: myMovies){
            if((movie.getID()).equals(id)){
                return movie.getTitle();
            }
        }
        return "Movie with ID " + id + " not found.";
    }
    
    public String getID(String title){
        for(Movie movie: myMovies){
            if((movie.getTitle()).equals(title)){
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }
}
