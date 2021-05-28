
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerSimilarRatings {
    private String movieFile = "ratedmoviesfull.csv";
    private String ratingsFile = "ratings.csv";
    // private String movieFile = "ratedmovies_short.csv";
    // private String ratingsFile = "ratings_short.csv";
    
    public void printAverageRatings(){
        FourthRatings fr = new FourthRatings();
        
        MovieDatabase.initialize(movieFile);
        System.out.println("There are " + 
        MovieDatabase.size() + " movies.");
        
        int minRaters = 35;
        ArrayList<Rating> minRaterList = fr.getAverageRatings(minRaters);
        System.out.println("Found " 
        + minRaterList.size() + " movies.");
        Collections.sort(minRaterList);
        for(Rating rating: minRaterList){
            System.out.println(
            rating.getValue() + " " 
            + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        FourthRatings fr = new FourthRatings();
        
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
        ArrayList<Rating> filteredList = fr.getAverageRatingsByFilter(minRaters, all);
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
    
    public void printSimilarRatings(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);
        String raterId = "71";
        int minRaters = 5;
        int numTopRaters = 20;
        ArrayList<Rating> output = fr.getSimilarRatings(raterId, numTopRaters, minRaters);
        System.out.println(MovieDatabase.getTitle(output.get(0).getItem()) 
        + " is the movie with the most similar rating.");
    }
    
    public void printSimilarRatingsByGenre(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);
        String genreStr = "Mystery";
        String raterId = "964";
        int minRaters = 5;
        int numTopRaters = 20;
        Filter genreFilter = new GenreFilter(genreStr);
        ArrayList<Rating> output = fr.getSimilarRatingsByFilter(raterId, numTopRaters, minRaters, genreFilter);
        System.out.println(MovieDatabase.getTitle(output.get(0).getItem()) + 
        " is the movie with the most similar rating.");
    }
    
    public void printSimilarRatingsByDirector(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);
        String dirStr = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        String raterId = "120";
        int minRaters = 2;
        int numTopRaters = 10;
        Filter dirFilter = new DirectorsFilter(dirStr);
        ArrayList<Rating> output = fr.getSimilarRatingsByFilter(raterId, numTopRaters, minRaters, dirFilter);
        System.out.println(MovieDatabase.getTitle(output.get(0).getItem()) + 
        " is the movie with the most similar rating.");
    }
    
    public void printSimilarRatingsByGenreAndMinutes(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);
        String genreStr = "Drama";
        Filter genreFilter = new GenreFilter(genreStr);
        int minMinute = 80;
        int maxMinute = 160;
        Filter minFilter = new MinutesFilter(minMinute, maxMinute);
        AllFilters all = new AllFilters();
        all.addFilter(minFilter);
        all.addFilter(genreFilter);
        String raterId = "168";
        int minRaters = 3;
        int numTopRaters = 10;
        ArrayList<Rating> output = fr.getSimilarRatingsByFilter(raterId, numTopRaters, minRaters, all);
        System.out.println(MovieDatabase.getTitle(output.get(0).getItem()) + 
        " is the movie with the most similar rating.");
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);
        int year = 1975;
        Filter yearFilter = new YearAfterFilter(year);
        int minMinutes = 70;
        int maxMinutes = 200;
        Filter minFilter = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters all = new AllFilters();
        all.addFilter(minFilter);
        all.addFilter(yearFilter);
        String raterId = "314";
        int minRaters = 5;
        int numTopRaters = 10;
        ArrayList<Rating> output = fr.getSimilarRatingsByFilter(raterId, numTopRaters, minRaters, all);
        System.out.println(MovieDatabase.getTitle(output.get(0).getItem()) + 
        " is the movie with the most similar rating.");
    }
}
