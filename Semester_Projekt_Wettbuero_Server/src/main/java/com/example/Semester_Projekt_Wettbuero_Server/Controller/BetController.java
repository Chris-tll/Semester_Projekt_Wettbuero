package com.example.Semester_Projekt_Wettbuero_Server.Controller;

import Entities.Bet;
import Entities.Dograce;
import com.example.Semester_Projekt_Wettbuero_Server.Services.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bet")
public class BetController {
    @Autowired
    private BetService betService;

    //GET MAPPING
    @GetMapping("/{id}")
    public List<Bet> getBetsByUsername(@PathVariable String id) { return betService.getBetsById(id); }

    @PostMapping
    public void createBet(@RequestBody Bet bet) {
        betService.createBet(bet);
    }

    @DeleteMapping
    public void deleteBet(String id) { betService.deleteBet(id); }

}
