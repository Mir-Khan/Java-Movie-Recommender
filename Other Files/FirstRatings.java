
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> loadedMovies = new ArrayList<Movie>(); 
        FileResource fr = new FileResource("data/" + filename);
        for(CSVRecord record: fr.getCSVParser()){
            Movie currentMovie = new Movie(record.get("id"), 
            record.get("title"), record.get("year"), 
            record.get("genre"),
            record.get("director"), record.get("country"), 
            record.get("poster"), Integer.parseInt(record.get("minutes")));
            loadedMovies.add(currentMovie);
        }
        return loadedMovies;
    }
    
    public ArrayList<Rater> loadRaters(String filename){
        // this is a little bit different since the Rater object
        // needs to be updated itself inside the arraylist
        ArrayList<Rater> loadedRaters = new ArrayList<Rater>();
        HashMap<String,Rater> uniqueRaters = new HashMap<String,Rater>();
        FileResource fr = new FileResource("data/" + filename);
        for(CSVRecord record: fr.getCSVParser()){
            String id = record.get("rater_id");
            String movie = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            if(uniqueRaters.containsKey(id)){
                // if found, the hashmap and arraylist Rater objects are updated and logged
                Rater foundRater = uniqueRaters.get(id);
                int indexOfFound = loadedRaters.indexOf(foundRater);
                foundRater.addRating(movie, rating);
                loadedRaters.set(indexOfFound, foundRater);
                uniqueRaters.replace(id, foundRater);
            }else{
                Rater foundRater = new EfficientRater(id);
                foundRater.addRating(movie, rating);
                loadedRaters.add(foundRater);
                uniqueRaters.put(id, foundRater);
            }
        }
        return loadedRaters;
    }
    
    public void testLoadMovies(){
        // String filename = "ratedmovies_short.csv";
        String filename = "ratedmoviesfull.csv";
        int comedyCount = 0;
        int longerThanCount = 0;
        ArrayList<Movie> loadTest = loadMovies(filename);
        HashMap<String, Integer> directorCount = new HashMap<String, Integer>();
        for(Movie movie: loadTest){
            // System.out.println(movie.toString());
            if((movie.getGenres()).indexOf("Comedy") != -1){
                comedyCount += 1;
            }
            if(movie.getMinutes() > 150){
                longerThanCount += 1;
            }
            String director = movie.getDirector();
            if(directorCount.containsKey(director)){
                int prevVal = directorCount.get(director);
                directorCount.put(director, prevVal + 1);
            }else{
                directorCount.put(director, 1);
            }
        }
        int maxMoviesDirected = 0;
        String prolificDirector = "";
        for(String director: directorCount.keySet()){
            int moviesDirected = directorCount.get(director);
            if(moviesDirected > maxMoviesDirected){
                maxMoviesDirected = moviesDirected;
                prolificDirector = director;
            }
        }
        System.out.println("There's " + longerThanCount + " longer than 150 minutes.");
        System.out.println("There's " + comedyCount + " comedy movie(s).");
        System.out.println("There are " + loadTest.size() + " movies.");
        System.out.println(prolificDirector + " has directed the most with " + maxMoviesDirected + " movies");
    }
    
    public void testLoadRaters(){
        // String filename = "ratings_short.csv";
        String filename = "ratings.csv";
        ArrayList<Rater> loadTest = loadRaters(filename);
        // code to print each rater, and the movies they rated alongside with the rating given
        // for(Rater rater: loadTest){
            // System.out.println("Rater " + rater.getID() + " has done " + rater.numRatings() + " ratings.");
            // ArrayList<String> itemsRated = rater.getItemsRated();
            // for(String movieId: itemsRated){
                // System.out.println("Movie ID: " + movieId + ", Rating: " + rater.getRating(movieId));
            // }
        // }
        
        // code to find the ratings of a specific rater
        // String raterId = "193";
        // int indexOfRater = -1;
        // for(Rater rater: loadTest){
            // if((rater.getID()).equals(raterId)){
                // indexOfRater = loadTest.indexOf(rater);
            // }
        // }
        
        // if(indexOfRater == -1){
            // System.out.println("Rater was not found.");
        // }else{
            // Rater searchedRater = loadTest.get(indexOfRater);
            // System.out.println("The rater with ID " + raterId + 
            // " has " + searchedRater.numRatings() + " ratings.");
            
        // }
        
        // this code finds the raters with the max num of ratings and who they are
        // int maxRatings = 0;
        // for(Rater rater: loadTest){
            // if(rater.numRatings() > maxRatings){
                // maxRatings = rater.numRatings();
            // }
        // }
        // ArrayList<Rater> ratersWithMaxRatings = new ArrayList<Rater>();
        // for(Rater rater: loadTest){
            // if(rater.numRatings() == maxRatings){
                // ratersWithMaxRatings.add(rater);
            // }
        // }
        // System.out.println("There are " + ratersWithMaxRatings.size() 
        // + " rater(s) with " + maxRatings + " and they are:");
        // for(Rater rater: ratersWithMaxRatings){
            // System.out.println("Rater with ID " + rater.getID());
        // }
        
        // this code counts the number of ratings a movie has amongst raters
        // String movieId = "1798709";
        // int counter = 0;
        // for(Rater rater: loadTest){
            // if(rater.hasRating(movieId)){
                // counter += 1;
            // }
        // }
        
        // System.out.println("Movie with ID " + movieId + 
        // " has " + counter + " ratings.");
        
        // This code finds the number of unique movies in the file
        // HashSet<String> moviesRated = new HashSet<String>();
        // for(Rater rater: loadTest){
            // ArrayList<String> currentRatersMovies = rater.getItemsRated();
            // for(String movieId : currentRatersMovies){
                // moviesRated.add(movieId);
            // }
        // }
        // System.out.println("There are " + moviesRated.size() + " unique movies.");
        
        System.out.println("There's " + loadTest.size() + " raters.");
    }
}
