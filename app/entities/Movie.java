package entities;

import javax.persistence.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public String category;

    public String producer;

    public String director;

    public String releasedate;

    public Long multiplex_id;

    public Movie() {
    }

    public Movie(String name, String category, String producer, String director, String releasedate) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMultiplex_id() {
        return multiplex_id;
    }

    public void setMultiplex_id(Long multiplex_id) {
        this.multiplex_id = multiplex_id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", director='" + director + '\'' +
                ", releasedate='" + releasedate + '\'' +
                '}';
    }
}
