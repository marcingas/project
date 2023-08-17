package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityReactiveRepository;
import pl.marcin.project.database.PurchaseEntityReactiveRepository;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Cup;
import pl.marcin.project.utils.AddressCupUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CupEntityReactiveService {
    @Autowired
    private final CupEntityReactiveRepository cupEntityReactiveRepository;


    public Flux<Cup> getAllCups() {
        return cupEntityReactiveRepository.findAll()
                .map(AddressCupUtilities::cupEntityToDto);
    }

    public Flux<Cup> getAllCupsById(List<Long> cupIds) {
        return cupEntityReactiveRepository.findAllById(cupIds)
                .map(AddressCupUtilities::cupEntityToDto);
    }

    public Mono<Cup> getCupById(Integer id) {
        return cupEntityReactiveRepository.findById(id.longValue())
                .map(AddressCupUtilities::cupEntityToDto);
    }

    public Mono<Cup> saveCup(Mono<Cup> cupMono) {

        return cupMono.map(AddressCupUtilities::dtoToCupEntity)
                .flatMap(cupEntityReactiveRepository::save)
                .map(AddressCupUtilities::cupEntityToDto);
    }

    public Mono<Cup> updateCup(Mono<Cup> cupMono, Integer id) {
        return cupEntityReactiveRepository.findById(id.longValue())
                .flatMap(cupEntity -> cupMono.map(AddressCupUtilities::dtoToCupEntity))
                .doOnNext(cupEntityReactiveRepository::save)
                .map(AddressCupUtilities::cupEntityToDto);
    }

    public Mono<Void> deleteCup(Integer id) {
        return cupEntityReactiveRepository.deleteById(id.longValue());
    }


}
