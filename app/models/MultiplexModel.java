package models;

import dtos.MultiplexDto;

import java.util.List;

public class MultiplexModel {

    public String multiplexName;

    public String screenname;

    public String moviename;

    public MultiplexModel() {
    }

    public MultiplexModel(String multiplexName, String screenname, String moviename) {
        this.multiplexName = multiplexName;
        this.screenname = screenname;
        this.moviename = moviename;
    }

    public String getMultiplexName() {
        return multiplexName;
    }

    public void setMultiplexName(String multiplexName) {
        this.multiplexName = multiplexName;
    }

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @Override
    public String toString() {
        return "MultiplexModel{" +
                "multiplexName='" + multiplexName + '\'' +
                ", screenname='" + screenname + '\'' +
                ", moviename='" + moviename + '\'' +
                '}';
    }
}