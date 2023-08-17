package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entityService.CupEntityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cups")
public class CupEntityController {
    private final CupEntityService cupEntityService;

    @PostMapping("/add")
    public CupEntity saveCup(@RequestBody CupEntity cupEntity) {
        return cupEntityService.addCup(cupEntity);
    }

    @GetMapping
    public List<CupEntity> getCups() {
        return cupEntityService.getAllCups();
    }

    @GetMapping("/{cupId}")
    public ResponseEntity<CupEntity> getCup(@PathVariable Long cupId) {
        try {
            CupEntity cupEntity = cupEntityService.getCupById(cupId);
            return ResponseEntity.ok(cupEntity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

