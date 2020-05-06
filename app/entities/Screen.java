package entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

public class Screen {

    private Long id;

    private String screenName;

    @OneToOne
    private Movie movie;

    public Screen(String screenName, Movie movie) {
        this.screenName = screenName;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", movie=" + movie +
                '}';
    }
}