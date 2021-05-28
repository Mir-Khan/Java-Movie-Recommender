
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsFile){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsFile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String movie: movies){
            double averageRating = getAverageByID(movie, minimalRaters);
            if(averageRating > 0.0){
                ratings.add(new Rating(movie, averageRating));
            }
        }
        return ratings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        // get the filtered movie ids
        ArrayList<String> movieIds = MovieDatabase.filterBy(filterCriteria);
        
        // then get all the average ratings of the same file
        ArrayList<Rating> unfilteredRatings = getAverageRatings(minimalRaters);
        
        ArrayList<Rating> minRatingFiltered = new ArrayList<Rating>();
        for(Rating rating : unfilteredRatings){
            String movie = rating.getItem();
            // if the current rating's movie is in the filtered
            // movieId dataset, then the rating has to be added 
            // in the arraylist to be returned
            if(movieIds.contains(movie)){
                minRatingFiltered.add(rating);
            }
        }
        return minRatingFiltered;
    }
}
