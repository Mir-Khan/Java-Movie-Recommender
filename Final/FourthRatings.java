
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {
    
    public FourthRatings() {
        // default constructor
    }
    
    private double getAverageByID(String id, int minimalRaters){
        double sumRatings = 0.0;
        int numRatersFound = 0;
        // goes through the rater list, if a rater has rated the movie
        // count them and add them to the running total of ratings
        for(Rater rater: RaterDatabase.getRaters()){
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
    
    private double dotProduct(Rater me, Rater r){
        ArrayList<String> moviesIRated = me.getItemsRated();
        ArrayList<String> moviesRaterRated = r.getItemsRated();
        double dotProd = 0;
        for(String movieId: moviesIRated){
            if(moviesRaterRated.contains(movieId) && 
            r.getRating(movieId) != 0 && 
            me.getRating(movieId) != 0){
                dotProd += ((me.getRating(movieId) - 5) * (r.getRating(movieId) - 5));
            }
        }
        return dotProd;
    }
    
    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rater> raterList = RaterDatabase.getRaters();
        ArrayList<Rating> similarityRatings = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for(Rater rater: raterList){
            String raterId = rater.getID();
            if(!raterId.equals(id)){
                double prod = dotProduct(me, rater);
                if(prod > 0){
                    Rating similar = new Rating(raterId, dotProduct(me, rater));
                    similarityRatings.add(similar);
                }
            }
        }
        Collections.sort(similarityRatings, Collections.reverseOrder());
        return similarityRatings;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRatings, int minimalRaters){
        return getSimilarRatingsByFilter(id, numSimilarRatings, minimalRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRatings, int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> similarityRatings = getSimilarities(id);
        ArrayList<Rating> returnList = new ArrayList<Rating>();
        for(String movieId: MovieDatabase.filterBy(filterCriteria)){
            // since we're getting the weighted average for a movie
            // these need to be initialized for every movie
            int numRatings = 0;
            double sumWeighted = 0;
            // this loop goes over the top raters and gets their rating of the current movie
            for(int i = 0; i < numSimilarRatings && i < similarityRatings.size(); i++){
                Rating r = similarityRatings.get(i);
                String raterId = r.getItem();
                Rater raterData = RaterDatabase.getRater(raterId);
                if(raterData.hasRating(movieId)){
                    numRatings += 1;
                    double ratingRaterGaveMovie = raterData.getRating(movieId);
                    double weight = r.getValue(); // the weight is the similarity rating calculated in another method
                    sumWeighted += (weight * ratingRaterGaveMovie);
                }
            }
            // if the movie had at least x raters, then we add it to the list to return
            // with the weighted average
            if(numRatings >= minimalRaters){
                returnList.add(new Rating(movieId, sumWeighted / numRatings));
            }
        }
        // sorting the list before its returned
        Collections.sort(returnList, Collections.reverseOrder());
        return returnList;
    }
}
