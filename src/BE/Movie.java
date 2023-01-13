package BE;

import java.util.Date;
import java.util.List;

/**
 * BE layer is accessed by all other classes, but cannot access any of them.
 */
public class Movie {

    private int id;
    private String name;
    private float rating;
    private String filelink;
    private Date lastview;
    private String website;
    private final List<Category> categories;

    public Movie(int id, String name, String filelink, List<Category> categories, String website) {
        this.id = id;
        this.name = name;
        this.filelink = filelink;
        this.categories = categories;
        this.website = website;
    }

    public Movie(int id, String name, float rating, String filelink, Date lastview, List<Category> categories, String website) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
        this.categories = categories;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilelink() {
        return filelink;
    }

    public float getRating() {
        return rating;
    }

    public Date getLastview() {
        return lastview;
    }

    public String getCategories() {
        String output = "";
        for(int i=0; i<categories.size(); i++) {
            if(i == categories.size()-1){
                output += categories.get(i).getName();
            }else{
                output += categories.get(i).getName() + ", ";
            }
        }
        return output;
    }

    public String getWebsite() {
        return website;
    }
}
