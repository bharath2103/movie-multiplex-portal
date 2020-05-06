package controllers.rest;

import Services.MovieService;
import Services.MultiplexService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtos.MovieDto;
import dtos.MultiplexDto;
import models.MovieModel;
import models.MovieModelList;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ModelConverter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class MovieRestController extends Controller {

    @Inject
    private MovieService movieService;

    @Inject
    private MultiplexService multiplexService;

    @Inject
    private HttpExecutionContext httpExecutionContext;

    @Inject
    private ModelConverter modelConverter;


    public CompletionStage<Result> findByAnyName(String name) {
        // wrap whole code to return an async response
        return supplyAsync(() -> {
            // fetch records from DB
            Optional<MovieDto> optionalMovieDto = this.movieService.findByName(name);
            if (optionalMovieDto.isPresent()) {
                MovieDto movie = optionalMovieDto.get();
                MovieModel movieModel = new MovieModel(movie.getName(), movie.getCategory(), movie.getProducer(), movie.getDirector(), movie.getReleasedate(), new ArrayList<>());

                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(movieModel, ObjectNode.class);
                return ok(responseObject);
            } else {
                //return badRequest(ResponseDesigner.design("Movies doesn't exist", false));
                MovieModel movieModel = new MovieModel(" ", " ", " ", " ", " ", new ArrayList<>());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(movieModel, ObjectNode.class);
                return notFound(responseObject);
            }
        }, httpExecutionContext.current());
    }


    public CompletionStage<Result> findBySimilarName(String name) {
        // wrap whole code to return an async response
        ObjectMapper objectMapper = new ObjectMapper();
        return supplyAsync(() -> {
            // fetch records from DB
            Optional<List<MovieDto>> optionalMovieDto = this.movieService.findBySimilarName(name);

            if (optionalMovieDto.isPresent()) {
                List<MovieDto> movieList = optionalMovieDto.get();
                List<MovieModel> movieModelList = new ArrayList<>();
                for(MovieDto movie : movieList) {
                    Optional<List<MultiplexDto>> optionalMultiplexDtoList = multiplexService.findAllMultiplexexByMovieId(movie.getId());
                    List<MultiplexDto> multiplexDtoList = optionalMultiplexDtoList.get();
                    System.out.println("The Movie runing in multiplex are "+optionalMultiplexDtoList.toString());
                    movieModelList.add(new MovieModel(movie.getName(), movie.getCategory(), movie.getProducer(), movie.getDirector(), movie.getReleasedate(), multiplexDtoList));
                }

                MovieModelList movieModelList1 = new MovieModelList(movieModelList);

                ObjectMapper mapper = new ObjectMapper();
                //convert the list to object
                String jsonString = null;
                JsonNode responseObject = null;
                try {
                    jsonString = mapper.writeValueAsString(movieModelList1);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    responseObject = mapper.readTree(jsonString);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                //JsonNode responseObject = mapper.convertValue(movieModelList, ObjectNode.class);
                return ok(responseObject);
            } else {
                //return badRequest(ResponseDesigner.design("Movies doesn't exist", false));
                MovieModel movieModel = new MovieModel(" ", " ", " ", " ", " ", new ArrayList<>());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(movieModel, ObjectNode.class);
                return notFound(responseObject);
            }
        }, httpExecutionContext.current());
    }
}
