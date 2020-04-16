package Services;

import Repositories.MultiplexRepository;
import dtos.CreateMultiplexDto;
import dtos.MovieDto;
import dtos.MultiplexDto;
import entities.Movie;
import entities.Multiplex;
import utils.ModelConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class MultiplexService {

    @Inject
    private MultiplexRepository multiplexRepository;

    @Inject
    private ModelConverter modelConverter;

    public List<MultiplexDto> addMultiplex(CreateMultiplexDto createMultiplexDto){
        List<MultiplexDto> listOfMultiplexDto = modelConverter.generateListOfMultiplexDto(createMultiplexDto);
        for (MultiplexDto multiplexDto : listOfMultiplexDto){
            multiplexRepository.insert(modelConverter.convertToMultiplex(multiplexDto));
        }
        return listOfMultiplexDto;
    }

    public Optional<MultiplexDto> updateMultiplex(MultiplexDto multiplexDto){
        Multiplex multiplex = multiplexRepository.update(modelConverter.convertToMultiplex(multiplexDto));
        MultiplexDto response = modelConverter.convertToMultiplexDto(multiplex);
        return Optional.ofNullable(response);
    }

    public Optional<MultiplexDto> updateMovie(MultiplexDto multiplexDto){
        Multiplex multiplex = multiplexRepository.updateMovie(modelConverter.convertToMultiplex(multiplexDto));
        MultiplexDto response = modelConverter.convertToMultiplexDto(multiplex);
        return Optional.ofNullable(response);
    }

    public Optional<MultiplexDto> flushMovie(MultiplexDto multiplexDto){
        Multiplex multiplex = multiplexRepository.flushMovie(modelConverter.convertToMultiplex(multiplexDto));
        MultiplexDto response = modelConverter.convertToMultiplexDto(multiplex);
        return Optional.ofNullable(response);
    }

    public List<MultiplexDto> getAllMultiplexes(){
        List<MultiplexDto> multiplexDtoList = new ArrayList<>();
        List<Multiplex> multiplexList = this.multiplexRepository.list();
        multiplexDtoList = multiplexList.stream()
                .map(multiplex-> new MultiplexDto(multiplex.getId()
                        , multiplex.getMultiplexName()
                        , multiplex.getAddress()
                        , multiplex.getScreenname()
                        , multiplex.getMovie()
                        , multiplex.getMovie() == null ? " ":multiplex.getMovie().getName()))
                .collect(Collectors.toList());
        return multiplexDtoList;
    }

    public Integer doesMultiplexExists(CreateMultiplexDto createMultiplexDto){
        List<MultiplexDto> listOfMultiplexDto = modelConverter.generateListOfMultiplexDto(createMultiplexDto);
        Multiplex multiplex = modelConverter.convertToMultiplex(listOfMultiplexDto.get(0));
        return multiplexRepository.getMultiplexCountByName(multiplex);
    }

    public boolean removeMultiplexScreen(Long id){
        return this.multiplexRepository.delete(id);
    }

    public boolean removeMultiplex(String multiplexName) {
        return this.multiplexRepository.deleteByName(multiplexName);
    }

    public Optional<MultiplexDto> findById(Long id){
        Multiplex multiplex = multiplexRepository.findById(id);
        return Optional.ofNullable(modelConverter.convertToMultiplexDto(multiplex));
    }
}
