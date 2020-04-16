package controllers;

import Services.MovieService;
import Services.MultiplexService;
import dtos.CreateMultiplexDto;
import dtos.MovieDto;
import dtos.MultiplexDto;
import entities.Movie;
import entities.Multiplex;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Http;
import play.mvc.Result;
import utils.ModelConverter;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.*;

public class MultiplexController {

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    private MultiplexService multiplexService;

    @Inject
    private MovieService movieService;

    @Inject
    private ModelConverter modelConverter;

    public Result createMultiplex(Http.Request request) {
        Form<CreateMultiplexDto> multiplexForm = formFactory.form(CreateMultiplexDto.class);
        return ok(views.html.multiplex.create.render(multiplexForm, request, messagesApi.preferred(request)));
    }

    public Result saveMultiplex(Http.Request request){
        Form<CreateMultiplexDto> feedbackForm =  this.formFactory.form(CreateMultiplexDto.class).bindFromRequest(request);
        CreateMultiplexDto createMultiplexDto = feedbackForm.get();
        Integer count = multiplexService.doesMultiplexExists(createMultiplexDto);
        if(count == 0) {
            List<MultiplexDto> listOfMultiplexDto = multiplexService.addMultiplex(createMultiplexDto);
            //return ok("Inserted Multiplex Successfully");
            return redirect(routes.MultiplexController.listAllMultiplex());
        }
        else
            return ok(" Duplicate Multiplex name");
    }

    public Result editMultiplex(Http.Request request, Long id) {
        Optional<MultiplexDto> multiplexDto = multiplexService.findById(id);
        MultiplexDto multiplexDto1 = multiplexDto.get();
        Form<MultiplexDto> multiplexForm = formFactory.form(MultiplexDto.class).fill(multiplexDto1);
        return ok(views.html.multiplex.edit.render(multiplexForm, request, messagesApi.preferred(request), id));
    }

    public Result updateMultiplex(Http.Request request, Long id) {
        Form<MultiplexDto> multiplexForm = formFactory.form(MultiplexDto.class).bindFromRequest(request);
        MultiplexDto multiplexDto = multiplexForm.get();
        multiplexDto.setId(id);
        Optional<MultiplexDto> response = multiplexService.updateMultiplex(multiplexDto);
        if (response.isPresent()) {
            request.flash().adding("success", "Record Added Successfully");
            return redirect(routes.MultiplexController.listAllMultiplex());
        } else {
            return badRequest("Insert Failed");
        }
    }

    public Result removeMultiplexScreen(Long id) {
        multiplexService.removeMultiplexScreen(id);
        return redirect(routes.MultiplexController.listAllMultiplex());
    }

    public Result removeMultiplex(String multiplexName) {
        multiplexService.removeMultiplex(multiplexName);
        return redirect(routes.MultiplexController.listAllMultiplex());
    }

    public Result listAllMultiplex() {
        List<MultiplexDto> multiplexes =  this.multiplexService.getAllMultiplexes();
        return ok(views.html.multiplex.listindex.render(multiplexes));
    }

    public Result listAllMultiplexAllotment(Http.Request request) {
        List<MultiplexDto> multiplexes = this.multiplexService.getAllMultiplexes();
        return ok(views.html.multiplex.allotment.homepage.render(multiplexes));
    }

    public Result allotment(Http.Request request, Long id) {
        Optional<List<MovieDto>> optionalMovieDtoList = movieService.getAllMovies();
        List<String>  movieList = new ArrayList<>();
        if(optionalMovieDtoList.isPresent()) {
            for (MovieDto m : optionalMovieDtoList.get()){
                movieList.add(m.name);
            }
        }

        Optional<MultiplexDto> multiplexDto = multiplexService.findById(id);
        MultiplexDto multiplexDto1 = multiplexDto.get();
        multiplexDto1.setMovieName((multiplexDto1.getMovie() == null ? "" : multiplexDto1.getMovie().getName()));
        Form<MultiplexDto> multiplexForm = formFactory.form(MultiplexDto.class).fill(multiplexDto1);
        return ok(views.html.multiplex.allotment.allotmovie.render(multiplexForm, request, messagesApi.preferred(request), id, movieList));
    }

    public Result allotMovie(Http.Request request, Long id) {
        Form<MultiplexDto> multiplexForm = formFactory.form(MultiplexDto.class).bindFromRequest(request);
        MultiplexDto multiplexDto = multiplexForm.get();

        Optional<MultiplexDto> optionalMultiplexDto = multiplexService.findById(id);
        MultiplexDto multiplexDto1 = optionalMultiplexDto.get();


        Optional<MovieDto> optionalMovieDtoList = movieService.findByName(multiplexDto.getMovieName());
        multiplexDto.setId(id);
        multiplexDto.setAddress(multiplexDto1.getAddress());
        multiplexDto.setMovie(modelConverter.convertToMovie(optionalMovieDtoList.get()));

        System.out.println("multiplexDto -> "+multiplexDto);
        MultiplexDto addMovietoMultiplexDto = new MultiplexDto(multiplexDto.getId()
                                                                , multiplexDto.multiplexName
                                                                , multiplexDto.getAddress()
                                                                , multiplexDto.getScreenname()
                                                                , multiplexDto.getMovie());

        Optional<MultiplexDto> response = multiplexService.updateMovie(addMovietoMultiplexDto);
        if (response.isPresent()) {
            request.flash().adding("success", "Record Added Successfully");
            return redirect(routes.MultiplexController.listAllMultiplexAllotment());
        } else {
            return badRequest("Insert Failed");
        }
    }

    public Result flushMovie(Long id) {
        Optional<MultiplexDto> optionalMultiplexDto = multiplexService.findById(id);
        MultiplexDto multiplexDto = optionalMultiplexDto.get();
        multiplexDto.setMovie(null);
        Optional<MultiplexDto> response = multiplexService.flushMovie(multiplexDto);
        return redirect(routes.MultiplexController.listAllMultiplexAllotment());
    }
}
