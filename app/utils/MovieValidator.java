package utils;

import Services.MovieService;
import dtos.MovieDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class MovieValidator {

    @Inject
    private MovieService movieService;

    @Inject
    private ModelConverter modelConverter;

    public boolean isDuplicate = false;

    public boolean doesMovieExists(MovieDto movieDto) {
        boolean isDuplicate = false;
        Optional<List<MovieDto>> movieDtoOptionallist = movieService.getAllMovies();
        if (movieDtoOptionallist.isPresent()) {
            outer:
            for (MovieDto m : movieDtoOptionallist.get()) {
                if (m.getName().equals(movieDto.getName())) {
                    isDuplicate = true;
                    break outer;
                }
            }
        } else
            return false;
        return isDuplicate;
    }


}
