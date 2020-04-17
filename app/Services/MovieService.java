package Services;

import Repositories.MovieRepository;
import dtos.MovieDto;
import entities.Movie;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import utils.ModelConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class MovieService {

    @Inject
    private MovieRepository movieRepository;

    @Inject
    private ModelConverter modelConverter;

    public Optional<MovieDto> createMovie(MovieDto movieDto){
        Movie movie = movieRepository.insert(modelConverter.convertToMovie(movieDto));
        MovieDto response = modelConverter.convertToMovieDto(movie);
        return Optional.ofNullable(response);
    }

    public Optional<MovieDto> updateMovie(MovieDto movieDto){
        Movie movie = movieRepository.update(modelConverter.convertToMovie(movieDto));
        MovieDto response = modelConverter.convertToMovieDto(movie);
        return Optional.ofNullable(response);
    }

    public boolean removeMovie(Long id){
        return this.movieRepository.delete(id);
    }

    public Optional<List<MovieDto>> getAllMovies(){
        List<MovieDto> movieDtoList = new ArrayList<>();
        List<Movie> movieList = this.movieRepository.listAll();
        movieDtoList = movieList.stream()
                .map(movie-> new MovieDto(movie.getId(), movie.getName(), movie.getCategory(), movie.getProducer(), movie.getDirector(), movie.getReleasedate()))
                .collect(Collectors.toList());
        return Optional.ofNullable(movieDtoList);
    }

    public Optional<MovieDto> findById(Long id){
        Movie movie = movieRepository.findById(id);
        return Optional.ofNullable(modelConverter.convertToMovieDto(movie));
    }

    /*############################# REST Services #############################*/

    public Optional<MovieDto> findByName(String name){
        Movie movie = movieRepository.findByName(name);
        return Optional.ofNullable(modelConverter.convertToMovieDto(movie));
    }

    public Optional<List<MovieDto>> findBySimilarName(String name){
        List<MovieDto> movieDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findBySimilarName(name);
        for(Movie movie: movieList){
            movieDtoList.add(modelConverter.convertToMovieDto(movie));
        }
        return Optional.ofNullable(movieDtoList);
    }

}
