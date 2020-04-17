package controllers.rest;

import Services.MovieService;
import Services.MultiplexService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.MovieDto;
import dtos.MultiplexDto;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ResponseDesigner;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class MultiplexRestController extends Controller {

    @Inject
    private MultiplexService multiplexService;

    @Inject
    private HttpExecutionContext httpExecutionContext;

    public CompletionStage<Result> list() {
        // wrap whole code to return an async response
        return supplyAsync(() -> {
            // fetch records from DB
            Optional<List<MultiplexDto>> optionalMultiplexDtoList = this.multiplexService.getAllMultiplexesRest();
            if (optionalMultiplexDtoList.isPresent()) {
                List<MultiplexDto> movieList = optionalMultiplexDtoList.get();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(movieList, JsonNode.class);
                return ok(ResponseDesigner.design(responseObject, true));
            } else {
                return badRequest(ResponseDesigner.design("Multiplex doesn't exist", false));
            }
        }, httpExecutionContext.current());
    }
}
