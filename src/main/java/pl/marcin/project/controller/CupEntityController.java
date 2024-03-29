package pl.marcin.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.serviceentity.CupEntityService;

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

    @PutMapping("/{cupId}/update")
    public CupEntity updateCup(@PathVariable Long cupId, @RequestBody CupEntity updatedCup) {
        updatedCup.setCupId(cupId);
        return cupEntityService.updateCup(updatedCup);
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

    @DeleteMapping("/{cupId}/delete")
    public Long deleteCup(@PathVariable Long cupId) {
        cupEntityService.deleteCup(cupId);
        return cupId;
    }

    @GetMapping("/addcup")
    public String showForm(Model model) {
        model.addAttribute("cupEntity", new CupEntity());
        return "cupEntity-form";
    }

    @PostMapping("/processForm")
    public String processForm(@Valid @ModelAttribute("cupEntity") CupEntity cupEntity) {
        return "cupEntity-confirmation";
    }
}

