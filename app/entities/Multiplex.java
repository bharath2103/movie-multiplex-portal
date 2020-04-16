package entities;

import javax.persistence.*;

@Entity
public class Multiplex {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String multiplexName;

    private String address;

    private String screenname;

    @OneToOne
    private Movie movie;

    public Multiplex() {
    }

    public Multiplex(String multiplexName, String address, String screenname) {
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
    }

    public Multiplex(String multiplexName, String address, String screenname, Movie movie) {
        this.multiplexName = multiplexName;
        this.address = address;
        this.screenname = screenname;
        this.movie = movie;
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

    @Override
    public String toString() {
        return "Multiplex{" +
                "id=" + id +
                ", multiplexName='" + multiplexName + '\'' +
                ", address='" + address + '\'' +
                ", screenname='" + screenname + '\'' +
                ", movie=" + movie +
                '}';
    }
}