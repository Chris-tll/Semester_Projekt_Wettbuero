package com.example.Semester_Projekt_Wettbuero_Server.Controller;

import Entities.Horse;
import Entities.Horserace;
import com.example.Semester_Projekt_Wettbuero_Server.Services.HorseraceService;
import com.example.Semester_Projekt_Wettbuero_Server.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horserace")
public class HorseraceController {

    @Autowired
    private HorseraceService horseraceService;

    //GET MAPPING
    @GetMapping
    public List<Horserace> getAllRaces() { return horseraceService.getAllRaces(); }

    @GetMapping("/id/{id}")
    public Horserace getRaceById(@PathVariable String id) { return horseraceService.getRaceById(id); }

    @GetMapping("/name/{name}")
    public Horserace getRaceByName(@PathVariable String name) { return horseraceService.getRaceByName(name); }

    @GetMapping("/location/{location}")
    public Horserace getRaceByLocation(@PathVariable String location) { return horseraceService.getRaceByLocation(location); }

    //POST MAPPING
    @PostMapping
    public Horserace createRace(@Valid @RequestBody Horserace race) { return horseraceService.createRace(race); }

    //PUT MAPPING
    @PutMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public ResponseEntity<String> updateRace(@PathVariable String id, @RequestBody Horserace race) { return horseraceService.updateRace(id, race); }

    //DELETE MAPPING
    @DeleteMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public void cancelRace(@PathVariable String id) { horseraceService.cancelRace(id); }
}
