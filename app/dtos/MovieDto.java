package dtos;

import entities.Multiplex;
import play.data.validation.Constraints;

public class MovieDto {

    public Long id;

    @Constraints.Required(message = "Movie name not provided")
    public String name;

    @Constraints.Required(message = "category not provided")
    public String category;

    @Constraints.Required(message = "enter the name of producer")
    public String producer;

    @Constraints.Required(message = "enter the name of director")
    public String director;

    @Constraints.Required(message = "Please provide the release date")
    public String releasedate;

    public Long multiplex_id;

    public boolean isCreaterequest;

    public boolean isEditRequest;

    public MovieDto() {
    }

    public MovieDto(Long id, String name, String category, String producer, String director, String releasedate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
    }

    public MovieDto(Long id, String name, String category, String producer, String director, String releasedate, Long multiplex_id) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
        this.multiplex_id = multiplex_id;
    }

    public MovieDto(String name, String category, String producer, String director, String releasedate) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
    }

    public MovieDto(String name, String category, String producer, String director, String releasedate, Long multiplex_id) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
        this.multiplex_id = multiplex_id;
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

    public boolean isCreaterequest() {
        return isCreaterequest;
    }

    public void setCreaterequest(boolean createrequest) {
        isCreaterequest = createrequest;
    }

    public boolean isEditRequest() {
        return isEditRequest;
    }

    public void setEditRequest(boolean editRequest) {
        isEditRequest = editRequest;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", director='" + director + '\'' +
                ", releasedate='" + releasedate + '\'' +
                ", isCreaterequest=" + isCreaterequest +
                ", isEditRequest=" + isEditRequest +
                '}';
    }
}
