package com.example.Semester_Projekt_Wettbuero_Server;

import com.example.Semester_Projekt_Wettbuero_Server.Services.DograceService;
import com.example.Semester_Projekt_Wettbuero_Server.Services.HorseraceService;
import com.example.Semester_Projekt_Wettbuero_Server.Services.SnailraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private HorseraceService horseraceService;

    @Autowired
    private DograceService dograceService;

    @Autowired
    private SnailraceService snailraceService;

    @Scheduled(fixedRate = 10000)
    public void checkRaceStart(){
        horseraceService.prozessHorserace();
        dograceService.prozessDograce();
        snailraceService.prozessSnailrace();
    }
}
