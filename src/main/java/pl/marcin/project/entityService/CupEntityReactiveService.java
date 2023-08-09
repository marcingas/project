package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityReactiveRepository;
import pl.marcin.project.entity.CupEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CupEntityReactiveService {
    @Autowired
    private final CupEntityReactiveRepository cupEntityReactiveRepository;

    public Flux<CupEntity> getAllCups() {
        return cupEntityReactiveRepository.findAll();
    }

    public Mono<CupEntity> getCupById(long id) {
        return cupEntityReactiveRepository.findById(id);
    }

    public Mono<CupEntity> saveCup(Mono<CupEntity> cupEntityMono) {
        return cupEntityMono.flatMap(cupEntityReactiveRepository::save);
    }

    public Mono<CupEntity> updateCUp(Mono<CupEntity> cupEntityMono) {
        return cupEntityMono.flatMap(cupEntityReactiveRepository::save);
    }

    public Mono<Void> deleteCup(Long id) {
        return cupEntityReactiveRepository.deleteById(id);
    }


}
