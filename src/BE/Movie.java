package BE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private float rating;
    private String personalRating;
    private String filelink;
    private Date lastview;

    private List<Category> categories = new ArrayList<>();

    public Movie(int id, String name, String filelink, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.filelink = filelink;
        this.categories = categories;
    }


    public Movie(int id, String name, float rating, String filelink, Date lastview, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        if (filelink.endsWith(".mp4") || filelink.endsWith(".mpeg4")) {
            this.filelink = filelink;
        }
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (rating >= 1 && rating <= 10) {
            this.rating = rating;
        }
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
