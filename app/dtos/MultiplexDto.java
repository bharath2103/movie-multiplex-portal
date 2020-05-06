package dtos;

import entities.Movie;
import play.data.validation.Constraints;

public class MultiplexDto {

    public Long id;

    @Constraints.Required(message = "Multiplex name not provided")
    public String multiplexName;

    @Constraints.Required(message = "Address not provided")
    public String address;

    @Constraints.Required(message = "Screenname not provided")
    public String screenname;

    public Movie movie;

    public String movieName;

    public MultiplexDto() {
    }

    public MultiplexDto(Long id, String multiplexName, String address, String screenname) {
        this.id = id;
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
    }

    public MultiplexDto(Long id, String multiplexName, String address, String screenname, Movie movie) {
        this.id = id;
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
        this.movie = movie;
    }

    public MultiplexDto(String multiplexName, String address, String screenname) {
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
    }

    public MultiplexDto(String multiplexName, String address, String screenname, Movie movie) {
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
        this.movie = movie;
    }

    public MultiplexDto(Long id, String multiplexName, String address, String screenname, Movie movie, String movieName) {
        this.id = id;
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
        this.movie = movie;
        this.movieName = movieName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMultiplexName() {
        return multiplexName;
    }

    public void setMultiplexName(String multiplexName) {
        this.multiplexName = multiplexName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public String toString() {
        return "MultiplexDto{" +
                "id=" + id +
                ", multiplexName='" + multiplexName + '\'' +
                ", address='" + address + '\'' +
                ", screenname='" + screenname + '\'' +
                ", movie=" + movie +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}