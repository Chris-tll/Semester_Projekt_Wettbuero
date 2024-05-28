package com.example.Semester_Projekt_Wettbuero_Server.Controller;

import Entities.Horserace;
import Entities.Snailrace;
import com.example.Semester_Projekt_Wettbuero_Server.Services.SnailraceService;
import com.example.Semester_Projekt_Wettbuero_Server.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snailrace")
public class SnailraceController {

    @Autowired
    private SnailraceService snailraceService;

    //GET MAPPING
    @GetMapping
    public List<Snailrace> getAllRaces() { return snailraceService.getAllRaces(); }

    @GetMapping("/id/{id}")
    public Snailrace getRaceById(@PathVariable String id) { return snailraceService.getRaceById(id); }

    @GetMapping("/name/{name}")
    public Snailrace getRaceByName(@PathVariable String name) { return snailraceService.getRaceByName(name); }

    @GetMapping("/location/{location}")
    public Snailrace getRaceByLocation(@PathVariable String location) { return snailraceService.getRaceByLocation(location); }

    //POST MAPPING
    @PostMapping
    public Snailrace createRace() { return snailraceService.createRace(); }

    //PUT MAPPING
    @PutMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public ResponseEntity<String> updateRace(@PathVariable String id, @RequestBody Snailrace snailrace) { return snailraceService.updateRace(id, snailrace); }

    //DELETE MAPPING
    @DeleteMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public void cancelRace(@PathVariable String id) { snailraceService.cancelRace(id); }
}
