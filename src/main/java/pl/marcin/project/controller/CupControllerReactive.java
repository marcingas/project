package pl.marcin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.project.entityService.CupEntityReactiveService;
import pl.marcin.project.model.Cup;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/cups")
public class CupControllerReactive {
    @Autowired
    private CupEntityReactiveService cupEntityReactiveService;

    @GetMapping
    public Flux<Cup> getCups() {
        return cupEntityReactiveService.getAllCups();
    }
}
