package com.example.Semester_Projekt_Wettbuero_Server;

import Entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalculateWinner {
   /* public Object calcWinner(Horserace horserace, Snailrace snailrace, Dograce dograce){
        List<Object> participantList = new ArrayList<>();
        double totalChance = 0;

        if (horserace != null) {
            participantList.addAll(horserace.getParticipants());
            totalChance = participantList.stream().mapToDouble(Horse::getChanceOfWinning).sum();
        } else if (dograce != null) {
            participantList.addAll(dograce.getParticipants());
            totalChance = participantList.stream().mapToDouble(Animal::getChance_of_winning).sum();
        } else if (snailrace != null) {
            participantList.addAll(snailrace.getParticipants());
            totalChance = participantList.stream().mapToDouble(Animal::getChance_of_winning).sum();
        }

        // Berechnung der Summe aller "Chance of winning"


        // Berechnung der relativen Wahrscheinlichkeit für jedes Pferd
        double[] relativeProbabilities = new double[participantList.size()];
        for (int i = 0; i < participantList.size(); i++) {
            relativeProbabilities[i] = participantList.get(i).getChance_of_winning() / totalChance;
        }

        // Generierung einer Zufallszahl zwischen 0 und 1
        Random random = new Random();
        double randomNumber = random.nextDouble();

        // Zufällige Auswahl eines Gewinners basierend auf der relativen Wahrscheinlichkeit
        double cumulativeProbability = 0;
        for (int i = 0; i < participantList.size(); i++) {
            cumulativeProbability += relativeProbabilities[i];
            if (randomNumber <= cumulativeProbability) {
                return participantList.get(i); // Gewinner gefunden
            }
        }

        // Sollte niemals erreicht werden, falls keine Pferde vorhanden sind oder die Summe der Chancen 0 ist
        return null;
    }*/


}
