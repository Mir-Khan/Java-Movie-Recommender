
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerAverage { 
    private String movieFile = "ratedmoviesfull.csv";
    private String ratingsFile = "ratings.csv";
    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings(movieFile, ratingsFile);
        int minRatings = 12;
        ArrayList<Rating> ratings = sr.getAverageRatings(minRatings);
        Collections.sort(ratings);
        for(Rating rating: ratings){
            String title = sr.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
        System.out.println("The number of movies that have at least " + 
        minRatings + " ratings: " + ratings.size());
        System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of ratings: " + sr.getRaterSize());
    }
    
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings(movieFile, ratingsFile);
        String movieTitle = "Vacation";
        String movieId = sr.getID(movieTitle);
        ArrayList<Rating> ratings = sr.getAverageRatings(0);
        for(Rating rating: ratings){
            if(rating.getItem() == movieId){
                System.out.println(movieTitle + " " + rating.getValue());
            }
        }
    }
}
