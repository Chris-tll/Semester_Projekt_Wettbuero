package com.example.Semester_Projekt_Wettbuero_Server.Services;

import Entities.*;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.*;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.SnailraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class SnailraceService {
    @Autowired
    private SnailraceRepository snailraceRepository;

    //Get all Races
    public List<Snailrace> getAllRaces() { return snailraceRepository.findAll(); }

    //Get Race by ID
    public Snailrace getRaceById(String id) { return snailraceRepository.findById(id).orElse(null); }

    //Get Race by name
    public Snailrace getRaceByName(String name) {
        for (Snailrace d : getAllRaces()) {
            if(d.getName().equals(name)){
                return d;
            }
        }
        return null;
    }

    //Get Race by location
    public Snailrace getRaceByLocation(String location) {
        for (Snailrace d : getAllRaces()) {
            if(d.getLocation().equals(location)){
                return d;
            }
        }
        return null;
    }

    //Create Race
    public Snailrace createRace() {
        Snailrace snailrace = new Snailrace();

        //Random Generator
        Random random = new Random();

        //Set Race Type
        snailrace.setType(RaceType.SNAILRACE);

        //Generiert eine zufällige Anzahl an Teilnehmer
        int participants = random.nextInt(20) + 4;
        snailrace.setNum_of_participants(participants);

        //Generiert einen zufälligen Wert für die Auswahl des Wetters
        //Danach wird das Enum an dieser Stelle ausgewählt
        int r = random.nextInt(Weather.values().length);
        snailrace.setWeather(Weather.values()[r]);

        //Es wird ein zuäflliger Wert generiert für das erste Prefix der Schnecke
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(Environment.values().length);
        snailrace.setEnvironment(Environment.values()[r]);

        //Generiert einen zufälligen Wert für die Auswahl des Rennnamen
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceNames.values().length);
        snailrace.setName(RaceNames.values()[r].toString());

        //Generiert einen zufälligen Wert für die Auswahl des Ortes
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceLocations.values().length);
        snailrace.setLocation(RaceLocations.values()[r].toString());

        //Generiert einen zufälligen Wert für die Streckenlänge
        r = random.nextInt(1, 5);
        snailrace.setLength(r);

        //Neue Liste der Teilnehmer (noch leer)
        List<Snail> participantList = new ArrayList<>();

        //For-Schleife läuft so lange, bis die generierte Teilnehmeranzahl erreicht ist
        for(int i = 0; i < participants; i++){
            //Neues Dog-Objekt
            Snail snail = new Snail();
            snail.setStartNum(i + 1);

            //Es wird ein zuäflliger Wert generiert für das erste Prefix der Schnecke
            //Danach wird das Enum an dieser Stelle ausgewählt
            r = random.nextInt(FirstSnailPrefix.values().length);
            snail.setName(FirstSnailPrefix.values()[r].toString());

            //Es wird ein zuäflliger Wert generiert für das zweite Prefix der Schnecke
            //Danach wird das Enum an dieser Stelle ausgewählt
            //Mit dem IF wird überprüft, ob "NONE" ausgewählt wurde, denn dan hat der Hund keinen zweiten Prefix
            r = random.nextInt(SecondSnailPrefix.values().length);
            if(!Objects.equals(SecondSnailPrefix.values()[r].toString(), "None")){
                String tmp = snail.getName();
                snail.setName(tmp + SecondDogPrefix.values()[r].toString());
            }

            //Es wird ein zuäflliger Wert für das Alter generiert
            r = random.nextInt(5) + 1;
            snail.setAge(r);

            //Es wird ein zufälliger Wert für die Größe im Zusammenhang mit dem Alter generiert
            double d = 0;
            if(snail.getAge() <= 2){
                d = random.nextDouble() + 0.5;
            } else if (snail.getAge() <= 5) {
                d = random.nextDouble() * 2.5 + 1.5;
            }
            else{
                d = random.nextDouble() * 2 + 4;
            }

            //Rundet auf 2 Dezimalstellen
            d = Math.round(d * 100.0) / 100.0;
            snail.setSize(d);

            //Berechne und generiere Geschwindigkeit der Schnecke (cm/s)
            if(snail.getSize() <= 1.5){
                r = random.nextInt(3) + 1;
            } else if (snail.getSize() <= 4) {
                r = random.nextInt(5) + 3;
            } else{
                r = random.nextInt(4) + 7;
            }

            snail.setSpeed(r);

            //Es wird ein zuäflliger Wert für das Stamina generiert
            r = random.nextInt(9+1 - 1) + 1;
            snail.setStamina(r);

            //Es wird ein zuäflliger Wert für die Reaction generiert (in Sekunden)
            r = random.nextInt(5) + 1;
            snail.setReaction(r);

            //Es wird ein zuäflliger Wert für den Einfluss des Wetters auf die Schnecke generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(11) - 5;
            snail.setWeather_influence(r);

            //Es wird ein zufälliger Wert generiert, der die Unberechenbarkeit der Schnecke erstellt
            r = random.nextInt(10) + 1;
            snail.setUnpredictability(r);

            //Es wird ein zuäflliger Wert für den Einfluss der Umgebung auf die Schnecke generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(11) - 5;
            snail.setEnvironment_influence(r);

            //Es wird ein zufälliger Wert generiert, der die Neugierde der Schnecke darstellt
            r = random.nextInt(10) + 1;
            snail.setCuriosity(r);

            //Es wird ein zufälliger Wert generiert, der das Temperament der Schnecke darstellt
            r = random.nextInt(10) + 1;
            snail.setTemperament(r);

            //Es wird die Gewinnchance berechnet, welche aus allen anderen Werten besteht
            double result = snail.getSpeed() + snail.getStamina() - snail.getReaction() +
                    snail.getWeather_influence() + snail.getUnpredictability() + snail.getEnvironment_influence() +
                    snail.getCuriosity() + snail.getTemperament();

            snail.setChance_of_winning((int)result);

            //Es wird der Mulitplier berechnet
            double tmp = 100 * ((double) 1 /snail.getChance_of_winning());
            tmp = tmp * 100;
            snail.setMultiplier(Math.round(tmp * 100.0) / 100.0);

            //Fertig generierte Schnecke wird zur Liste hinzugefügt
            participantList.add(snail);
        }

        //Streckenlänge wird übergeben
        int trackLength = snailrace.getLength();

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
        snailrace.setStart(startTime);
        snailrace.setEnd(endTime);
        snailrace.setEstimatedDuration(raceLength);

        //Übergibt die vollständige Teilnehmerliste
        snailrace.setParticipants(participantList);

        //Setzt Status standardmäßig auf UPCOMING
        snailrace.setStatus(RaceStatus.UPCOMING);

        //Erstellt das fertige Rennen
        return snailraceRepository.save(snailrace);
    }

    //Update Race
    public ResponseEntity<String> updateRace(String id, Snailrace snailrace) {
        if(snailraceRepository.existsById(id)) {
            snailrace.setId(id);
            snailraceRepository.save(snailrace);
            return ResponseEntity.ok("True");
        }
        return ResponseEntity.ok("False");
    }

    //Delete Race
    public void cancelRace(String id) {
        snailraceRepository.deleteById(id);
    }

    public void prozessSnailrace(){
        LocalDateTime current = LocalDateTime.now();
        List<Snailrace> checkList = getAllRaces();
        int count = 0;

        for (Snailrace srace : checkList) {
            if (srace.getStatus() != RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count < 5) {
            createRace();
            count = 0;
        }

        for (Snailrace srace : checkList){
            if (srace.getStatus() == RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count > 5) {
            Snailrace tmp = null;

            for (Snailrace srace : checkList) {
                if (srace.getStatus() == RaceStatus.FINISHED) {
                    tmp = srace;
                    break;
                }
            }
            cancelRace(tmp.getId());
            count = 0;
        }

        for (Snailrace s : getAllRaces()) {
            if(s.getStart().isBefore(current)){
                s.setStatus(RaceStatus.ACTIVE);
                snailraceRepository.save(s);
            }
        }

        for (Snailrace s : getAllRaces()) {
            if(s.getEnd().isBefore(current)){
                s.setStatus(RaceStatus.FINISHED);
                snailraceRepository.save(s);
                createRace();
            }
        }
    }
}
