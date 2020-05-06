package controllers;

import Services.MovieService;
import dtos.MovieDto;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.MovieValidator;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class MovieController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    private MovieService movieService;

    @Inject
    private MovieValidator movieValidator;

    public Result createMovie(Http.Request request) {
        Form<MovieDto> movieForm = formFactory.form(MovieDto.class);
        return ok(views.html.movie.create.render(movieForm, request, messagesApi.preferred(request)));
    }

    public Result editMovie(Http.Request request, Long id) {
        Optional<MovieDto> movieDto = movieService.findById(id);
        MovieDto movieDto1 = movieDto.get();
        movieDto1.setEditRequest(true);
        Form<MovieDto> movieForm = formFactory.form(MovieDto.class).fill(movieDto1);
        return ok(views.html.movie.edit.render(movieForm, request, messagesApi.preferred(request)));
    }

    public Result removeMovie(Long id) {
        movieService.removeMovie(id);
        return redirect(routes.MovieController.listAllMovies());
    }

    public Result saveMovie(Http.Request request) {
        Form<MovieDto> movieForm = formFactory.form(MovieDto.class).bindFromRequest(request);
        if (movieForm.hasErrors()) {
            //logger.error("errors = {}", movieForm.errors());
            request.flash().adding("failed", "Constraints not satisfied!!!");
            return badRequest(views.html.movie.create.render(movieForm, request, messagesApi.preferred(request)));
        }
        MovieDto movieDto = movieForm.get();
        if (!movieValidator.doesMovieExists(movieDto)) {
            Optional<MovieDto> response = movieService.createMovie(movieDto);
            if (response.isPresent()) {
                request.flash().adding("success", "Record Added Successfully");
                return redirect(routes.MovieController.listAllMovies());
            } else {
                return badRequest("Insert Failed");
            }
        } else {
            return ok(" Duplicate Movie name");
            //return ok(views.html.movie.create.render(movieForm, request, messagesApi.preferred(request)));
        }
    }

    public Result updateMovie(Http.Request request) {
        Form<MovieDto> movieForm = formFactory.form(MovieDto.class).bindFromRequest(request);
        if (movieForm.hasErrors()) {
            //logger.error("errors = {}", movieForm.errors());
            request.flash().adding("failed", "Constraints not satisfied!!!");
            return badRequest(views.html.movie.create.render(movieForm, request, messagesApi.preferred(request)));
        }
        MovieDto movieDto = movieForm.get();
        Optional<MovieDto> response = movieService.updateMovie(movieDto);
        if (response.isPresent()) {
            request.flash().adding("success", "Record Added Successfully");
            return redirect(routes.MovieController.listAllMovies());
        } else {
            return badRequest("Insert Failed");
        }
    }

    public Result listAllMovies() {
        Optional<List<MovieDto>> movies = this.movieService.getAllMovies();
        return ok(views.html.movie.listindex.render(movies.get()));
    }

}
