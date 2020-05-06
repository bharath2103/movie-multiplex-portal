package models;

import java.util.List;

public class MovieModelList implements MessageModelList{
    private List<MovieModel> movieModels;

    public MovieModelList() {
    }

    public MovieModelList(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }

    @Override
    public String toString() {
        return "MovieModelList{" +
                "movieModels=" + movieModels +
                '}';
    }
}
