package BE;

import java.util.Date;

public class Movie {
    private int id;
    private String name;
    private double rating;
    private double personalRating;
    private String filelink;
    private Date lastview;

    public Movie(int id, String name, double rating, double personalRating, String filelink, Date lastview) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.personalRating = personalRating;
        this.filelink = filelink;
        this.lastview = lastview;
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

    public double getRating() {
        return personalRating;
    }

    public void setRating(double personalRating) {
        Movie.this.personalRating = personalRating;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        if (filelink.endsWith(".mp4") || filelink.endsWith(".mpeg4")) {
            this.filelink = filelink;
        }
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }
}
