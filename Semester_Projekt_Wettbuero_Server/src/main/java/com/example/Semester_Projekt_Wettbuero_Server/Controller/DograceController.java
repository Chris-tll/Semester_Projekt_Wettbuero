package com.example.Semester_Projekt_Wettbuero_Server.Controller;

import Entities.Dograce;
import com.example.Semester_Projekt_Wettbuero_Server.Services.DograceService;
import com.example.Semester_Projekt_Wettbuero_Server.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dograce")
public class DograceController {
    @Autowired
    private DograceService dograceService;

    //GET MAPPING
    @GetMapping
    public List<Dograce> getAllRaces() { return dograceService.getAllRaces(); }

    @GetMapping("/id/{id}")
    public Dograce getRaceById(@PathVariable String id) { return dograceService.getRaceById(id); }

    @GetMapping("/name/{name}")
    public Dograce getRaceByName(@PathVariable String name) { return dograceService.getRaceByName(name); }

    @GetMapping("/location/{location}")
    public Dograce getRaceByLocation(@PathVariable String location) { return dograceService.getRaceByLocation(location); }

    //POST MAPPING
    @PostMapping
    public Dograce createRace() { return dograceService.createRace(); }

    //PUT MAPPING
    @PutMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public ResponseEntity<String> updateRace(@PathVariable String id, @RequestBody Dograce dograce) { return dograceService.updateRace(id, dograce); }

    //DELETE MAPPING
    @DeleteMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public void cancelRace(@PathVariable String id) { dograceService.cancelRace(id); }
}
