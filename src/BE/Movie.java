package BE;

import java.util.Date;

public class Movie {
    private int Id;
    private String Name;
    private double Rating;
    private String Filelink;
    private Date Lastview;

    public Movie(int id, String name, double rating, String filelink, Date lastview) {
        Id = id;
        Name = name;
        Rating = rating;
        Filelink = filelink;
        Lastview = lastview;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getFilelink() {
        return Filelink;
    }

    public void setFilelink(String filelink) {
        if (filelink.endsWith(".mp4") || filelink.endsWith(".mpeg4")) {
            Filelink = filelink;
        }
    }

    public Date getLastview() {
        return Lastview;
    }

    public void setLastview(Date lastview) {
        Lastview = lastview;
    }
}
