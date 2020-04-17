package controllers.rest;

import Services.MovieService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtos.MovieDto;
import models.MessageModel;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class MovieRestController extends Controller {

    @Inject
    private MovieService movieService;

    @Inject
    private HttpExecutionContext httpExecutionContext;


    public CompletionStage<Result> findByAnyName(String name) {
        // wrap whole code to return an async response
        return supplyAsync(() -> {
            // fetch records from DB
            Optional<MovieDto> optionalMovieDto = this.movieService.findByName(name);
            if (optionalMovieDto.isPresent()) {
                MovieDto movie = optionalMovieDto.get();
                MessageModel messageModel = new MessageModel(1, 1, movie.getDirector(), movie.getName());

                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(messageModel, ObjectNode.class);
                return ok(responseObject);
            } else {
                //return badRequest(ResponseDesigner.design("Movies doesn't exist", false));
                MessageModel messageModel = new MessageModel(1, 1, " ", " ");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(messageModel, ObjectNode.class);
                return notFound(responseObject);
            }
        }, httpExecutionContext.current());
    }
}
