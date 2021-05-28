
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class RecommendationRunner implements Recommender{
    private String[] genreArr = {"Drama", "Action", "Comedy", 
        "Sci-Fi", "Romance", "Crime", "Horror", "Biography", 
    "Thriller", "Animation", "Documentary", "Mystery", "Adventure"};
    private ArrayList<String> genreList = new ArrayList<String>(Arrays.asList(genreArr));
    public ArrayList<String> getItemsToRate(){
        // year filter
        Filter yearAfter = new YearAfterFilter(1990);
        // genre filter
        // getting a random genre from the list above
        int randomGenreInd = (int)(Math.random() * genreList.size());
        String randGenre = genreList.get(randomGenreInd);
        Filter genreFilter = new GenreFilter(randGenre);
        // minute filter
        int minMinute = 90;
        int maxMinute = 180;
        Filter minuteFilter = new MinutesFilter(minMinute, maxMinute);
        // combo filter
        AllFilters all = new AllFilters();
        all.addFilter(minuteFilter);
        all.addFilter(genreFilter);
        all.addFilter(yearAfter);
        // return from the database an arraylist with x movies
        ArrayList<String> retList = new ArrayList<String>();
        ArrayList<String> filteredMovieList = MovieDatabase.filterBy(all);
        ArrayList<Integer> numChosen = new ArrayList<Integer>();
        for(int i = 0; i < 20; i++){
            // get a random movie
            int randInd = (int)(Math.random() * filteredMovieList.size());
            if(!numChosen.contains(randInd)){
                // if that movie is a new one, add it and its 
                // index to the movie list and seen index list
                numChosen.add(randInd);
                retList.add(filteredMovieList.get(randInd));
            }else{
                // if that movie was already chosen, we need an extra loop
                i -= 1;
            }
        }
        return retList;
    }
    public void printRecommendationsFor (String webRaterID){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize("data/ratings.csv");
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        int numTopSimilar = 50;
        int minRaters = 5;
        ArrayList<Rating> output = fr.getSimilarRatings(webRaterID, numTopSimilar, minRaters);
        ArrayList<String> moviesRaterRated = RaterDatabase.getRater(webRaterID).getItemsRated();
        if(output.size() > 0){
            System.out.println("<table style='margin:auto; padding: 25px 40px 25px 40px; margin-top: 30px; margin-bottom: 30px; border: 1px solid black; border-radius: 1%; box-shadow: 8px 3px 2px gray; background-color:white; text-align: center;'>");
            System.out.println("<tr><th>Title</th><th>Year</th></tr>");
            for(Rating r: output){
                if(!moviesRaterRated.contains(r.getItem())){
                    String movieTitle = MovieDatabase.getTitle(r.getItem());
                    int movieYear = MovieDatabase.getYear(r.getItem());
                    System.out.println("<tr><td>" + movieTitle + "<td/><td>" 
                    + movieYear + "<td/></tr>");
                }
            }
            System.out.println("<table/>");
        }else{
            System.out.println("<h1>No movies were recommended. Something went wrong!</h1>");
        }
    }
}
