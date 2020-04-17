package Repositories;

import entities.Movie;
import entities.Multiplex;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Singleton
public class MultiplexRepository {

    @Inject
    JPAApi jpaApi;

    private <T> T wrap(Function<EntityManager, T> function){
        return this.jpaApi.withTransaction(function);
    }

    public void insert(Multiplex multiplex) {
        jpaApi.withTransaction(entityManager -> {
            entityManager.persist(multiplex);
        });
    }

    public List<Multiplex> list(){
        return this.wrap(entityManager -> {
            List<Multiplex> multiplexes =  entityManager.createQuery("select m from Multiplex m", Multiplex.class).getResultList();
            return multiplexes;
        });
    }

    public Integer getMultiplexCountByName(Multiplex multiplex) {
        return jpaApi.withTransaction(entityManager -> {
            String query = "select m from Multiplex m where m.multiplexName ='"+multiplex.getMultiplexName()+"'";
            List<Multiplex> multiplexList = entityManager.createQuery(query).getResultList();
            return multiplexList.size();
        });
    }

    public Multiplex update(Multiplex multiplex) {
        return this.wrap(entityManager -> {
            String updateMultiplexQuery = "update Multiplex " +
                    "set address = '"+multiplex.getAddress()+"'" +
                    " ,  screenname = '"+multiplex.getScreenname()+"'" +
                    " where id = "+multiplex.getId();
            entityManager.createQuery(updateMultiplexQuery).executeUpdate();
            return this.findById(multiplex.getId());
        });
    }

    public Multiplex updateMovie(Multiplex multiplex) {
        return this.wrap(entityManager -> {
            String updateMultiplexQuery = "update Multiplex " +
                    "set address = '"+multiplex.getAddress()+"'" +
                    " ,  screenname = '"+multiplex.getScreenname()+"'" +
                    " ,  movie_id = '"+multiplex.getMovie().getId()+"'" +
                    " where id = "+multiplex.getId();
            entityManager.createQuery(updateMultiplexQuery).executeUpdate();
            return this.findById(multiplex.getId());
        });
    }

    public Multiplex flushMovie(Multiplex multiplex) {
        return this.wrap(entityManager -> {
            String updateMultiplexQuery = "update Multiplex " +
                    "set address = '"+multiplex.getAddress()+"'" +
                    " ,  screenname = '"+multiplex.getScreenname()+"'" +
                    " ,  movie_id = "+null+"" +
                    " where id = "+multiplex.getId();
            entityManager.createQuery(updateMultiplexQuery).executeUpdate();
            return this.findById(multiplex.getId());
        });
    }

    public boolean delete(Long id){
        jpaApi.withTransaction(entityManager -> {
            Multiplex multiplex = entityManager.find(Multiplex.class,id);
            entityManager.remove(multiplex);
            return true;
        });
        return false;
    }

    public boolean deleteByName(String multiplexName) {
        jpaApi.withTransaction(entityManager -> {
            List<Multiplex> multiplexList =  entityManager.createQuery("select m from Multiplex m where m.multiplexName = '"+multiplexName+"'", Multiplex.class).getResultList();
            for(Multiplex m : multiplexList){
                this.delete(m.getId());
            }
            return true;
        });
        return false;
    }

    public Multiplex findById(Long id){
        return this.wrap(entityManager -> {
            Multiplex multiplex =  entityManager.createQuery("select m from Multiplex m where m.id = "+id, Multiplex.class).getSingleResult();
            //entityManager.find(Multiplex.class, id);
            return multiplex;
        });
    }

}
