package pl.marcin.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entityService.CupEntityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CupController {
    private final CupEntityService cupEntityService;

    @GetMapping("/cups")
    public List<CupEntity> getCups() {
        return cupEntityService.getAllCups();
    }

    @GetMapping("/cups/{id}")
    public CupEntity getCup(@PathVariable Long id) {
        return cupEntityService.getCups(id);
    }

    @PostMapping("/cups")
    public ResponseEntity<CupEntity> addCup(@Valid @RequestBody CupEntity cupEntity) {
        cupEntityService.addCup(cupEntity);
        return new ResponseEntity<CupEntity>(cupEntity, HttpStatus.CREATED);
    }

    @PutMapping("cups/{id}")
    public ResponseEntity<CupEntity> updateCup(
            @PathVariable Long id, @Valid @RequestBody CupEntity cupEntity) {
        cupEntity.setCup_id(id);
        cupEntityService.updateCup(cupEntity);
        return new ResponseEntity<CupEntity>(cupEntity, HttpStatus.OK);
    }

    @DeleteMapping("/cups")
    public ResponseEntity<HttpStatus> deleteCustomer(@RequestParam Long id) {
        cupEntityService.deleteCup(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
