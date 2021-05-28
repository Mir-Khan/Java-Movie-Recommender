
/**
 * Write a description of Rater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public interface Rater {
    public boolean hasRating(String item);
    public void addRating(String item, double rating);
    public String getID();
    public double getRating(String item);
    public int numRatings();
    public ArrayList<String> getItemsRated();
}
