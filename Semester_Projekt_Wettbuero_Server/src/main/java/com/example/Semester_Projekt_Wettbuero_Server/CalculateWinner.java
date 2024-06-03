package com.example.Semester_Projekt_Wettbuero_Server;

import Entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalculateWinner {
   public void calcWinner(Horserace horserace, Snailrace snailrace, Dograce dograce){
        int chance = 0;
        Random r = new Random();

        if (horserace != null) {
            for (Horse h : horserace.getParticipants()) {
                chance = r.nextInt(1, 100);
                h.setWinner_chance((double) h.getChance_of_winning() / chance);
            }

            Horse tmp = horserace.getParticipants().get(0);
            for (Horse h : horserace.getParticipants()) {
                if (h.getWinner_chance() > tmp.getWinner_chance()) {
                    tmp = h;
                }
            }
            horserace.setWinner(tmp);
        }

        if (dograce != null) {
            for (Dog d : dograce.getParticipants()) {
                chance = r.nextInt(1, 100);
                d.setWinner_chance((double) d.getChance_of_winning() / chance);
            }

            Dog tmp = dograce.getParticipants().get(0);
            for (Dog d : dograce.getParticipants()) {
                if (d.getWinner_chance() > tmp.getWinner_chance()) {
                    tmp = d;
                }
            }
            dograce.setWinner(tmp);
        }

        if (snailrace != null) {
            for (Snail s : snailrace.getParticipants()) {
                chance = r.nextInt(1, 100);
                s.setWinner_chance((double) s.getChance_of_winning() / chance);
            }

            Snail tmp = snailrace.getParticipants().get(0);
            for (Snail s : snailrace.getParticipants()) {
                if (s.getWinner_chance() > tmp.getWinner_chance()) {
                    tmp = s;
                }
            }
            snailrace.setWinner(tmp);
        }
   }
}
