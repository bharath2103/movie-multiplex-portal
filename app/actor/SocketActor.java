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
import controllers.routes;
import models.MessageModel;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;

import static play.mvc.Results.redirect;

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
        System.out.println("Message : "+message);
        CompletionStage<HttpResponse> responseFuture= this.callRestApi(message);
        // 1. consume and convert into my model format
        // 2. convert into JsonNode and send it to client
        responseFuture.thenCompose(this::consumeHttpResponse)
                .thenAccept(messageModel -> {
                    System.out.println("DATA : " + messageModel);
                    // convert to JsonNode : design util classes
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode json = mapper.convertValue(messageModel, JsonNode.class);
                    // sent to guardian actor
                    this.guardian.tell(json, getSelf());
                });
        //return redirect(routes.MovieController.listAllMovies());

    }

    // method to generate a http call to an REST API
    private CompletionStage<HttpResponse> callRestApi(String message){
        // generate a random number
        System.out.println("Calling Rest API");
/*        int id = ThreadLocalRandom.current().nextInt(0,100);*/
        System.out.println("http://localhost:9000/api/movie/findByAnyName/" + message);
        return Http.get(getContext().getSystem()).singleRequest(HttpRequest.create("http://localhost:9000/api/movie/findByAnyName/" + message));
    }

    // method to consume httpResponse
    private CompletionStage<MessageModel> consumeHttpResponse(HttpResponse httpResponse){
        // get mat from actorSystem
        System.out.println("Consuming");
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());
        return Jackson.unmarshaller(MessageModel.class)
                .unmarshal(httpResponse.entity(), materializer)
                .thenApply(messageModel -> {
                    this.discardEntity(httpResponse, materializer);
                    return messageModel;
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
