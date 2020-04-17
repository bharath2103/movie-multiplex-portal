package Repositories;

import dtos.MovieDto;
import entities.Movie;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class MovieRepository {

    @Inject
    JPAApi jpaApi;

    private <T> T wrap(Function<EntityManager, T> function) {
        return this.jpaApi.withTransaction(function);
    }

    public Movie insert(Movie movie) {
        return this.wrap(entityManager -> {
            entityManager.persist(movie);
            String queryString = "select m from Movie m where m.name = '" + movie.getName() + "'";
            Movie response = entityManager.createQuery(queryString, Movie.class).getSingleResult();
            return response;
        });
    }

    public Movie update(Movie movie) {
        return this.wrap(entityManager -> {
            String getMovieByNameQuery = "select m from Movie m where m.name = '" + movie.getName() + "'";
            Movie response = entityManager.createQuery(getMovieByNameQuery, Movie.class).getSingleResult();

            String updateMovieQuery = "update Movie " +
                    "set name = '" + movie.getName() + "'" +
                    " ,  category = '" + movie.getCategory() + "'" +
                    " ,  producer = '" + movie.getProducer() + "'" +
                    " ,  director = '" + movie.getDirector() + "'" +
                    " ,  releasedate = '" + movie.getReleasedate() + "'" +
                    " where id = " + response.getId();
            entityManager.createQuery(updateMovieQuery).executeUpdate();
            return response;
        });
    }

    public List<Movie> listAll() {
        return this.wrap(entityManager -> {
            List<Movie> movies = entityManager.createQuery("select m from Movie m ", Movie.class).getResultList();
            return movies;
        });
    }

    public Movie findById(Long id) {
        return this.wrap(entityManager -> {
            Movie movie = entityManager.createQuery("select m from Movie m where m.id = " + id, Movie.class).getSingleResult();
            return movie;
        });
    }


    public Movie findByName(String name) {
        return this.wrap(entityManager -> {
            Movie movie = entityManager.createQuery("select m from Movie m where m.name = '" + name + "'", Movie.class).getSingleResult();
            return movie;
        });
    }

    public boolean delete(Long id) {
        jpaApi.withTransaction(entityManager -> {
            Movie movie = entityManager.find(Movie.class, id);
            entityManager.remove(movie);
            return true;
        });
        return false;
    }

    /*############################# REST Services #############################*/

    public List<Movie> findBySimilarName(String name) {
        System.out.println("FindMySimilarName is "+name);
        return this.wrap(entityManager -> {
            List<Movie> movieList =  entityManager.createQuery("select m from Movie m where m.name like '"+name+"%'", Movie.class).getResultList();
            return movieList;
        });
    }
}
