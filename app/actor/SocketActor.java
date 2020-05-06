package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.MovieModelList;
import models.MultiplexModelList;

import java.util.concurrent.CompletionStage;

public class SocketActor extends AbstractActor {

    private ActorRef guardian;

    public SocketActor(ActorRef guardian){
        this.guardian = guardian;
    }

    // not going to return actorRef
    // special object : Props
    public static Props init(ActorRef guardian){
        // this will initiate the set of prop for current actor
        return Props.create(SocketActor.class, ()-> new SocketActor(guardian));
    }

    // message processing method
    private void processMessage(JsonNode jsonNode){
        // if required convert into request model
        // fetch random message from Rest API
        System.out.println("Processing message");
        String message = jsonNode.get("message").textValue();
        String messageType = jsonNode.get("messagetype").textValue();;
        System.out.println("Message : "+message);
        CompletionStage<HttpResponse> responseFuture= this.callRestApi(message, messageType);
        // 1. consume and convert into my model format
        // 2. convert into JsonNode and send it to client
        if(messageType.equals("movie")) {
            responseFuture.thenCompose(this::consumeHttpResponse)
                    .thenAccept(movieModelList -> {
                        System.out.println("DATA : " + movieModelList);
                        // convert to JsonNode : design util classes
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode json = mapper.convertValue(movieModelList, JsonNode.class);
                        // sent to guardian actor
                        this.guardian.tell(json, getSelf());
                    });
        }
        if(messageType.equals("multiplex")) {
            responseFuture.thenCompose(this::consumeMultiplexHttpResponse)
                    .thenAccept(multiplexModelList -> {
                        System.out.println("DATA : " + multiplexModelList);
                        // convert to JsonNode : design util classes
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode json = mapper.convertValue(multiplexModelList, JsonNode.class);
                        // sent to guardian actor
                        this.guardian.tell(json, getSelf());
                    });
        }
        //return redirect(routes.MovieController.listAllMovies());
    }

    // method to generate a http call to an REST API
    private CompletionStage<HttpResponse> callRestApi(String message, String messageType){
        // generate a random number
        System.out.println("Calling Rest API");
/*        int id = ThreadLocalRandom.current().nextInt(0,100);*/
        System.out.println("http://localhost:9000/api/"+messageType+"/findBySimilarName/"+message);
        return Http.get(getContext().getSystem()).singleRequest(HttpRequest.create("http://localhost:9000/api/"+messageType+"/findBySimilarName/"+message));
    }

    // method to consume httpResponse
    private CompletionStage<MovieModelList> consumeHttpResponse(HttpResponse httpResponse){
        // get mat from actorSystem
        System.out.println("Consuming");
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());
        return Jackson.unmarshaller(MovieModelList.class)
                .unmarshal(httpResponse.entity(), materializer)
                .thenApply(movieModelList -> {
                    this.discardEntity(httpResponse, materializer);
                    return movieModelList;
                });
    }

    private CompletionStage<MultiplexModelList> consumeMultiplexHttpResponse(HttpResponse httpResponse){
        // get mat from actorSystem
        System.out.println("Consuming");
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());
        return Jackson.unmarshaller(MultiplexModelList.class)
                .unmarshal(httpResponse.entity(), materializer)
                .thenApply(multiplexModelList -> {
                    this.discardEntity(httpResponse, materializer);
                    return multiplexModelList;
                });
    }

    private void discardEntity(HttpResponse httpResponse, Materializer materializer) {
        httpResponse.discardEntityBytes(materializer)
                .completionStage()
                .whenComplete((done, ex) -> System.out.println("Discarded"));
    }


    @Override
    public Receive createReceive() {
        System.out.println("Received Mesage in SockerActor");
        return receiveBuilder()
        // we recieve message of type JsonNode
                .match(JsonNode.class, this::processMessage)
                .build();
    }
}
