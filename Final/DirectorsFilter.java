
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class DirectorsFilter implements Filter{
    private ArrayList<String> myDirectorList;
    
    public DirectorsFilter(String director){
        myDirectorList = new ArrayList<String>();
        for(String directorInput: director.split(",")){
            myDirectorList.add(directorInput.trim());
        }
    }
    
    @Override
    public boolean satisfies(String id){
        ArrayList<String> movieDirectors = new ArrayList<String>();
        for(String inputDirector: MovieDatabase.getDirector(id).split(",")){
            movieDirectors.add(inputDirector.trim());
        }
        for(String director: movieDirectors){
            if(myDirectorList.contains(director)){
                return true;
            }
        }
        
        return false;
    }
}
