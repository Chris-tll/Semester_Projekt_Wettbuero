package com.example.Semester_Projekt_Wettbuero_Server.Services;

import Entities.*;
import com.example.Semester_Projekt_Wettbuero_Server.CalculateWinner;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.*;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.HorseraceRepository;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.UserRepository;
import com.example.Semester_Projekt_Wettbuero_Server.ScheduleTask;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HorseraceService {

    @Autowired
    private HorseraceRepository horseraceRepository;
    private CalculateWinner calculateWinner = new CalculateWinner();

    @Autowired
    private UserRepository userRepository;

    //Get all Races
    public List<Horserace> getAllRaces() { return horseraceRepository.findAll(); }

    //Get Race by ID
    public Horserace getRaceById(String id) { return horseraceRepository.findById(id).orElse(null); }

    //Get Race by name
    public Horserace getRaceByName(String name) {
        for (Horserace h : getAllRaces()) {
            if(h.getName().equals(name)){
                return h;
            }
        }
        return null;
    }

    //Get Race by location
    public Horserace getRaceByLocation(String location) {
        for (Horserace h : getAllRaces()) {
            if(h.getLocation().equals(location)){
                return h;
            }
        }
        return null;
    }

    //Create Race
    public Horserace createRace() {
        Horserace horserace = new Horserace();

        //Random Generator
        Random random = new Random();

        //Set Race Type
        horserace.setType(RaceType.HORSERACE);

        //Generiert eine zufällige Anzahl an Teilnehmer
        int participants = random.nextInt(24) + 2;
        horserace.setNum_of_participants(participants);

        //Generiert einen zufälligen Wert für die Auswahl des Wetters
        //Danach wird das Enum an dieser Stelle ausgewählt
        int r = random.nextInt(Weather.values().length);
        horserace.setWeather(Weather.values()[r]);

        //Generiert einen zufälligen Wert für die Auswahl des Rennnamen
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceNames.values().length);
        horserace.setName(RaceNames.values()[r].toString());

        //Generiert einen zufälligen Wert für die Auswahl des Ortes
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceLocations.values().length);
        horserace.setLocation(RaceLocations.values()[r].toString());

        //Generiert einen zufälligen Wert für die Auswahl des Gelände
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(Terrain.values().length);
        horserace.setTerrain(Terrain.values()[r]);

        //Generiert einen zufälligen Wert für die Streckenlänge
        r = random.nextInt(350, 6901);
        horserace.setLength(r);

        //Neue Liste der Teilnehmer (noch leer)
        List<Horse> participantList = new ArrayList<>();

        //For-Schleife läuft so lange, bis die generierte Teilnehmeranzahl erreicht ist
        for(int i = 0; i < participants; i++){
            //Neues Horse-Objekt
            Horse horse = new Horse();
            horse.setStartNum(i + 1);

            //Es wird ein zuäflliger Wert generiert für das erste Prefix des Pferdes
            //Danach wird das Enum an dieser Stelle ausgewählt
            r = random.nextInt(FirstHorsePrefix.values().length);
            horse.setName(FirstHorsePrefix.values()[r].toString());

            //Es wird ein zuäflliger Wert generiert für das zweite Prefix des Pferdes
            //Danach wird das Enum an dieser Stelle ausgewählt
            //Mit dem IF wird überprüft, ob "NONE" ausgewählt wurde, denn dan hat das Pferd keinen zweiten Prefix
            r = random.nextInt(SecondHorsePrefix.values().length);
            if(!Objects.equals(SecondHorsePrefix.values()[r].toString(), "None")){
                String tmp = horse.getName();
                horse.setName(tmp + SecondHorsePrefix.values()[r].toString());
            }

            //Es wird ein zuäflliger Wert für das Alter generiert
            r = random.nextInt(20- 2 + 1) + 2;
            horse.setAge(r);

            //Es wird ein zuäflliger Wert für die Wins generiert
            r = random.nextInt(24 + 1);
            horse.setWins(r);

            //Es wird ein zuäflliger Wert für die Platzierungen generiert
            //Hierbei ist die untere Grenze der generierte Wert der Wins, da es keinen Sinn machen würde, wenn das Pferd weniger
            //Platzierungen erhalten hat, als es Wins hat
            r = random.nextInt(54 + 1 - horse.getWins()) + horse.getWins();
            horse.setGot_ranked(r);

            //Es wird das Erfahrungslevel berechnet mit den Wins und den Platzierungen
            horse.setExperience_level(horse.getWins() + (horse.getGot_ranked()/2));

            //Es wird ein zuäflliger Wert für die eigentliche Anzahl der Teilnahmen an Rennen generiert
            r = random.nextInt(horse.getGot_ranked() + 5 - horse.getGot_ranked()) + horse.getGot_ranked();
            horse.setTook_place_in_races(r);

            //Es wird ein zuäflliger Wert für das Fitnesslevel generiert
            r = random.nextInt(9+1 - 1) + 1;
            horse.setFitness_level(r);

            //Es wird ein zuäflliger Wert für die Qualität des Trainers generiert (Effizienz des Trainers)
            r = random.nextInt(99+1 - 10) + 10;
            horse.setTrainer_quality(r);

            //Es wird ein zuäflliger Wert für die Qualität des Jockeys generiert (Wie gut er reiten kann)
            r = random.nextInt(99+1 - 10) + 10;
            horse.setJockey_quality(r);

            //Es wird ein zuäflliger Wert für den Einfluss des Wetters auf das Pferd generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(21) - 10;
            horse.setWeather_influence(r);

            //Es wird ein zuäflliger Wert für den Einfluss des Gelände auf das Pferd generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(21) - 10;
            horse.setTerrain_influence(r);

            //Es wird die Gewinnchance berechnet, welche aus allen anderen Werten besteht
            double result = ((horse.getWins()+horse.getGot_ranked())/(double)horse.getTook_place_in_races())*(horse.getFitness_level() +
                    horse.getTrainer_quality() + horse.getJockey_quality() + horse.getExperience_level() + horse.getWeather_influence() + horse.getTerrain_influence());
            horse.setChance_of_winning((int)result);

            //Es wird der Mulitplier berechnet
            double tmp = 100 * ((double) 1 /horse.getChance_of_winning());
            tmp = tmp * 100;
            horse.setMultiplier(Math.round(tmp * 100.0) / 100.0);

            //Fertig generiertes Pferd wird zur Liste hinzugefügt
            participantList.add(horse);
        }

        //Streckenlänge wird übergeben
        int trackLength = horserace.getLength();

        //Aktueller Zeitpunkt wird gespeichert
        LocalDateTime currentTime = LocalDateTime.now();

        //Generiert eine zufällige Zeitdifferenz zwischen 3 und 10 Minuten
        Random rnd = new Random();
        int minutesToAdd = rnd.nextInt(3, 11); // 11, weil die obere Grenze exklusiv ist

        //Berechnet den Startzeitpunkt des Rennens
        LocalDateTime startTime = currentTime.plusMinutes(minutesToAdd);

        //Berechne/Schätze die Renndauer basierend auf der Rennstreckenlänge und einer durchschnittlichen Geschwindigkeit von 55 km/h
        double averageSpeed = 60.0; //in km/h
        double raceDurationHours = (double)trackLength / averageSpeed;
        int raceLength = (int)Math.round(raceDurationHours * 60); //Konvertiert Stunden in Minuten und rundet auf ganze Minuten

        //Begrenzt die Renndauer auf maximal 15 Minuten
        raceLength = Math.min(raceLength, 15);

        //Berechnet den Endzeitpunkt des Rennens
        LocalDateTime endTime = startTime.plusMinutes(raceLength);

        //Setzt Start- und Endzeitpunkt des Rennesn sowie die geschätzte Dauer des Rennens
        horserace.setStart(startTime);
        horserace.setEnd(endTime);
        horserace.setEstimatedDuration(raceLength);

        //Übergibt die vollständige Teilnehmerliste
        horserace.setParticipants(participantList);

        //Setzt Status standardmäßig auf UPCOMING
        horserace.setStatus(RaceStatus.UPCOMING);

        //Erstellt das fertige Rennen
        return horseraceRepository.save(horserace);
    }

    //Update Race
    public ResponseEntity<String> updateRace(String id, Horserace horserace) {
        if(horseraceRepository.existsById(id)) {
            horserace.setId(id);
            horseraceRepository.save(horserace);
            return ResponseEntity.ok("True");
        }
        return ResponseEntity.ok("False");
    }

    //Delete Race
    public void cancelRace(String id) {
        horseraceRepository.deleteById(id);
    }

    //Timer
    public void prozessHorserace(){
        LocalDateTime current = LocalDateTime.now();
        List<Horserace> checkList = getAllRaces();
        int count = 0;

        for (Horserace hrace : checkList) {
            if (hrace.getStatus() != RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count < 5 && checkList.size() <= 11) {
            createRace();
            count = 0;
        }

        for (Horserace hrace : checkList){
            if (hrace.getStatus() == RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count > 5 && checkList.size() > 10) {
            Horserace tmp = null;

            for (Horserace hrace : checkList) {
                if (hrace.getStatus() == RaceStatus.FINISHED) {
                    tmp = hrace;
                    break;
                }
            }
            if (tmp != null){
                cancelRace(tmp.getId());
            }
            count = 0;
        }

        for (Horserace h : getAllRaces()) {
            if(h.getStart().isBefore(current)){
                h.setStatus(RaceStatus.ACTIVE);
                horseraceRepository.save(h);
            }
        }

        for (Horserace h : getAllRaces()) {
            if(h.getEnd().isBefore(current)){
                h.setStatus(RaceStatus.FINISHED);
                calculateWinner.calcWinner(h, null, null);
                horseraceRepository.save(h);

                for (User u : userRepository.findAll()) {
                    if (u.getAllBets() != null){
                        for (Bet b : u.getAllBets()) {
                            if (b.getRaceType() == h.getType()) {
                                if (h.getId().equals(b.getRaceId())) {
                                    if (b.getStartNum() == h.getWinner().getStartNum()) {
                                        u.setMoney(u.getMoney() + (b.getMoney_bet() * h.getWinner().getMultiplier()));
                                        b.setStatus(BetStatus.WON);
                                        u.getAllBets().add(b);
                                        userRepository.save(u);
                                    }
                                    else {
                                        b.setStatus(BetStatus.LOST);
                                        u.getAllBets().add(b);
                                        userRepository.save(u);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
