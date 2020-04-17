package controllers;

import actor.SocketActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.MessageModel;
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.WebSocket;

import javax.inject.Inject;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SocketController extends Controller {
    // dependency
    @Inject
    private ActorSystem actorSystem;

    // materializer : factory for stream execution engine
    @Inject
    private Materializer materializer;

    // method to expose/initiate Websocket
    public WebSocket socket(){

        // WebSocket.Json.acceptOrResult(<lambda Function functional impl>)
        // in : Request Header
        // out : flow ( converted from actor ref )
        return WebSocket.Json.acceptOrResult(this::createActorFlow);
    }

    // create actorflow ( ~actor ref)
    // F class : defines a set of Functional programming helpers
    // Right : success (valid)
    private CompletionStage<F.Either<Result, Flow<JsonNode,JsonNode,?>>> createActorFlow(Http.RequestHeader header){
        // convert flow into future
        // CompletableFuture.completedFuture( <actorflow from  ref>)
        return CompletableFuture.completedFuture(F.Either.Right(createFlowForActor()));
    }

    // method to link the flow to the actor
    private Flow<JsonNode, JsonNode, ?> createFlowForActor(){
        // ActorFlow.actorRef(<lambda>,<Actor System>, <materializer>);
        // lambda : parameter : virtualGuardianActor
        // lambda will be used to initiate the custom actor
        return ActorFlow.actorRef(guardianActorRef->{
            return SocketActor.init(guardianActorRef);
        }, this.actorSystem, this.materializer);
    }

    // controller action method
    public Result index(Http.Request request){
        // create a websocket url

        String url = routes.SocketController.socket().webSocketURL(request);
        // comment the above line, and uncomment below line to expose streams
        // String url = routes.SocketController.akkaStreamsSocket().webSocketURL(request);
        System.out.println("The URL is "+url);
        return ok(views.html.socket.index.render(url));

    }

    // exposing streaming socket
    public WebSocket akkaStreamsSocket() {
        return WebSocket.Json.accept(
                request -> {
                    Sink<JsonNode, ?> in = Sink.foreach(System.out::println);
                    MessageModel messageModel = new MessageModel(1, 1, "Title", "Test Body");
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode json = mapper.convertValue(messageModel, JsonNode.class);
                    Source<JsonNode, ?> out = Source.tick(
                            Duration.ofSeconds(2),
                            Duration.ofSeconds(2),
                            json
                    );
                    return Flow.fromSinkAndSource(in, out);
                });
    }

}
