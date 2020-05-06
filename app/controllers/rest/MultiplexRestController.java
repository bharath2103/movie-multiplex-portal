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
import models.MultiplexModel;
import models.MultiplexModelList;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ResponseDesigner;

import javax.inject.Inject;
import java.util.ArrayList;
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

    public CompletionStage<Result> findBySimilarName(String name) {
        // wrap whole code to return an async response
        ObjectMapper objectMapper = new ObjectMapper();
        return supplyAsync(() -> {
            // fetch records from DB
            Optional<List<MultiplexDto>> optionalMultiplexDto = this.multiplexService.findBySimilarName(name);

            if (optionalMultiplexDto.isPresent()) {
                List<MultiplexDto> multiplexList = optionalMultiplexDto.get();
                List<MultiplexModel> multiplexModels = new ArrayList<>();
                for(MultiplexDto multiplex : multiplexList) {
                    multiplexModels.add(new MultiplexModel(multiplex.getMultiplexName(), multiplex.getScreenname(), multiplex.getMovie().getName()));
                }

                MultiplexModelList multiplexModelList1 = new MultiplexModelList(multiplexModels);

                ObjectMapper mapper = new ObjectMapper();
                //convert the list to object
                String jsonString = null;
                JsonNode responseObject = null;
                try {
                    jsonString = mapper.writeValueAsString(multiplexModelList1);
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
                MultiplexModel multiplexModel = new MultiplexModel(" ", " ", " ");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseObject = mapper.convertValue(multiplexModel, ObjectNode.class);
                return notFound(responseObject);
            }
        }, httpExecutionContext.current());
    }
}
