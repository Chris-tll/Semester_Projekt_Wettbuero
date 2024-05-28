package com.example.Semester_Projekt_Wettbuero_Server.Services;

import Entities.Bet;
import Entities.User;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.BetRepository;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.DograceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    public List<Bet> getBetsById(String id) {
        ArrayList<Bet> betList = new ArrayList<>();
        for (Bet b : betRepository.findAll()) {
            if(b != null){
                if(id.equals(b.getUserId())){
                    betList.add(b);
                }
            }
        }
        return betList;
    }

    public void createBet(Bet bet) {
        betRepository.save(bet);
    }

    public void deleteBet(String id) {
        betRepository.deleteById(id);
    }
}
