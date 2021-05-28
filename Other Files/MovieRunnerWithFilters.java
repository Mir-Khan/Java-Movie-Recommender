
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerWithFilters {
    private String movieFile = "ratedmoviesfull.csv";
    private String ratingsFile = "ratings.csv";
    // private String movieFile = "ratedmovies_short.csv";
    // private String ratingsFile = "ratings_short.csv";
    
    public void printAverageRatings(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 35;
        ArrayList<Rating> minRaterList = tr.getAverageRatings(minRaters);
        System.out.println("Found " 
        + minRaterList.size() + " movies.");
        Collections.sort(minRaterList);
        for(Rating rating: minRaterList){
            System.out.println(
            rating.getValue() + " " 
            + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfter(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 20;
        int yearSearch = 2000;
        YearAfterFilter year = new YearAfterFilter(yearSearch);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, year);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " matches");
        for(Rating rating : filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " " + 
            MovieDatabase.getYear(movieId) + " " + 
            MovieDatabase.getTitle(movieId));
        }
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 20;
        String genreTerm = "Comedy";
        GenreFilter genre = new GenreFilter(genreTerm);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, genre);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " matches");
        for(Rating rating : filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " " + 
            MovieDatabase.getTitle(movieId));
            System.out.println(MovieDatabase.getGenres(movieId));
        }
    }
    
    public void printAverageRatingsByMinutes(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 5;
        int min = 105;
        int max = 135;
        MinutesFilter minMax = new MinutesFilter(min, max);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, minMax);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " matches");
        for(Rating rating: filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " Time: " + 
            MovieDatabase.getMinutes(movieId) + " " + 
            MovieDatabase.getTitle(movieId));
        }
    }
    
    public void printAverageRatingsByDirectors(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 4;
        String directorList = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        DirectorsFilter directors = new DirectorsFilter(directorList);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, directors);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " matches");
        for(Rating rating: filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " " + 
            MovieDatabase.getTitle(movieId) + " " + 
            MovieDatabase.getDirector(movieId));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 8;
        int yearSearch = 1990;
        String genreList = "Drama";
        GenreFilter genre = new GenreFilter(genreList);
        YearAfterFilter year = new YearAfterFilter(yearSearch);
        AllFilters all = new AllFilters();
        all.addFilter(genre);
        all.addFilter(year);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, all);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " match(es)");
        for(Rating rating: filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " " + 
            MovieDatabase.getYear(movieId) + " " + 
            MovieDatabase.getTitle(movieId) + " " +
            MovieDatabase.getGenres(movieId));
        }
    }
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("There are " + 
        tr.getRaterSize() + " ratings.");
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 3;
        int minMinutes = 90;
        int maxMinutes = 180;
        String directorList = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        MinutesFilter minMax = new MinutesFilter(minMinutes, maxMinutes);
        DirectorsFilter df = new DirectorsFilter(directorList);
        AllFilters all = new AllFilters();
        all.addFilter(minMax);
        all.addFilter(df);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, all);
        Collections.sort(filteredList);
        System.out.println("Found " + filteredList.size() + " match(es)");
        for(Rating rating: filteredList){
            String movieId = rating.getItem();
            System.out.println(rating.getValue() + " Time: " 
            + MovieDatabase.getMinutes(movieId) + " " + 
            MovieDatabase.getTitle(movieId) + " " + 
            MovieDatabase.getDirector(movieId));
        }
    }
}
