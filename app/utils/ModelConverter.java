package utils;

import dtos.CreateMultiplexDto;
import dtos.MovieDto;
import dtos.MultiplexDto;
import entities.Movie;
import entities.Multiplex;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ModelConverter {

    public MovieDto convertToMovieDto(Movie movie){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Movie.class, MovieDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(movie, MovieDto.class);
    }

    public Movie convertToMovie(MovieDto movieDto){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(MovieDto.class, Movie.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(movieDto, Movie.class);
    }

    public MultiplexDto convertToMultiplexDto(Multiplex multiplex){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Movie.class, MultiplexDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(multiplex, MultiplexDto.class);
    }

    public Multiplex convertToMultiplex(MultiplexDto multiplexDto){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(MovieDto.class, Multiplex.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(multiplexDto, Multiplex.class);
    }

    public List<MultiplexDto> generateListOfMultiplexDto(CreateMultiplexDto createMultiplexDto) {

        List<MultiplexDto> ListOfMultiplexDto = new ArrayList<>();
        List<String> screenNames = Arrays.asList("Screen A", "Screen B", "Screen C", "Screen D", "Screen E", "Screen F");
        Map<Integer, String> screenMap = new HashMap<>();

        for (int i = 1; i <= createMultiplexDto.getNumberOfScreens(); i++) {
            screenMap.put(i, screenNames.get(i - 1));
        }

        for (int j = 1; j <= screenMap.size(); j++) {
            ListOfMultiplexDto.add(new MultiplexDto(createMultiplexDto.getMultiplexName()
                    , createMultiplexDto.getAddress()
                    , screenMap.get(j)));
        }
        return ListOfMultiplexDto;
    }


}
