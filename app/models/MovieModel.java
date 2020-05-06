package models;

import dtos.MultiplexDto;

import java.util.List;

public class MovieModel {

    public String name;

    public String category;

    public String producer;

    public String director;

    public String releasedate;

    public List<MultiplexDto> multiplexDtoList;

    public MovieModel() {
    }

    public MovieModel(String name, String category, String producer, String director, String releasedate, List<MultiplexDto> multiplexDtoList) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
        this.multiplexDtoList = multiplexDtoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public List<MultiplexDto> getMultiplexDtoList() {
        return multiplexDtoList;
    }

    public void setMultiplexDtoList(List<MultiplexDto> multiplexDtoList) {
        this.multiplexDtoList = multiplexDtoList;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", director='" + director + '\'' +
                ", releasedate='" + releasedate + '\'' +
                ", multiplexDtoList=" + multiplexDtoList +
                '}';
    }
}