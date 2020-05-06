package dtos;

import entities.Movie;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

public class ScreenDto {

    private Long id;

    private String screenName;

    private Movie movie;

    public ScreenDto(String screenName, Movie movie) {
        this.screenName = screenName;
        this.movie = movie;
    }

    public ScreenDto(Long id, String screenName, Movie movie) {
        this.id = id;
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
        return "ScreenDto{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", movie=" + movie +
                '}';
    }
}