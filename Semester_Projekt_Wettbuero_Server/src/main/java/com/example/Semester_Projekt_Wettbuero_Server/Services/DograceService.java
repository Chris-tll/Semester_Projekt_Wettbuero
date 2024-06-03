package com.example.Semester_Projekt_Wettbuero_Server.Services;

import Entities.Dog;
import Entities.Dograce;
import Entities.Horserace;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.*;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.DograceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DograceService {

    @Autowired
    private DograceRepository dograceRepository;

    //Get all Races
    public List<Dograce> getAllRaces() { return dograceRepository.findAll(); }

    //Get Race by ID
    public Dograce getRaceById(String id) { return dograceRepository.findById(id).orElse(null); }

    //Get Race by name
    public Dograce getRaceByName(String name) {
        for (Dograce d : getAllRaces()) {
            if(d.getName().equals(name)){
                return d;
            }
        }
        return null;
    }

    //Get Race by location
    public Dograce getRaceByLocation(String location) {
        for (Dograce d : getAllRaces()) {
            if(d.getLocation().equals(location)){
                return d;
            }
        }
        return null;
    }

    //Create Race
    public Dograce createRace() {
        Dograce dograce = new Dograce();

        //Random Generator
        Random random = new Random();

        //Set Race Type
        dograce.setType(RaceType.DOGRACE);

        //Generiert eine zufällige Anzahl an Teilnehmer
        int participants = random.nextInt(8) + 4;
        dograce.setNum_of_participants(participants);

        //Generiert einen zufälligen Wert für die Auswahl des Wetters
        //Danach wird das Enum an dieser Stelle ausgewählt
        int r = random.nextInt(Weather.values().length);
        dograce.setWeather(Weather.values()[r]);

        //Generiert einen zufälligen Wert für die Auswahl des Rennnamen
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceNames.values().length);
        dograce.setName(RaceNames.values()[r].toString());

        //Generiert einen zufälligen Wert für die Auswahl des Ortes
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(RaceLocations.values().length);
        dograce.setLocation(RaceLocations.values()[r].toString());

        //Generiert einen zufälligen Wert für die Auswahl des Gelände
        //Danach wird das Enum an dieser Stelle ausgewählt
        r = random.nextInt(Terrain.values().length);
        dograce.setTerrain(Terrain.values()[r]);

        //Generiert einen zufälligen Wert für die Streckenlänge
        r = random.nextInt(300, 800);
        dograce.setLength(r);

        //Neue Liste der Teilnehmer (noch leer)
        List<Dog> participantList = new ArrayList<>();

        //For-Schleife läuft so lange, bis die generierte Teilnehmeranzahl erreicht ist
        for(int i = 0; i < participants; i++){
            //Neues Dog-Objekt
            Dog dog = new Dog();
            dog.setStartNum(i + 1);

            //Es wird ein zuäflliger Wert generiert für das erste Prefix des Hundes
            //Danach wird das Enum an dieser Stelle ausgewählt
            r = random.nextInt(FirstDogPrefix.values().length);
            dog.setName(FirstDogPrefix.values()[r].toString());

            //Es wird ein zuäflliger Wert generiert für das zweite Prefix des Hundes
            //Danach wird das Enum an dieser Stelle ausgewählt
            //Mit dem IF wird überprüft, ob "NONE" ausgewählt wurde, denn dan hat der Hund keinen zweiten Prefix
            r = random.nextInt(SecondDogPrefix.values().length);
            if(!Objects.equals(SecondDogPrefix.values()[r].toString(), "None")){
                String tmp = dog.getName();
                dog.setName(tmp + SecondDogPrefix.values()[r].toString());
            }

            //Es wird ein zuäflliger Wert für das Alter generiert
            r = random.nextInt(6) + 1;
            dog.setAge(r);

            //Es wird ein zuäflliger Wert für die Wins generiert
            r = random.nextInt(9 + 1);
            dog.setWins(r);

            //Es wird ein zuäflliger Wert für die Platzierungen generiert
            //Hierbei ist die untere Grenze der generierte Wert der Wins, da es keinen Sinn machen würde, wenn der Hund weniger
            //Platzierungen erhalten hat, als es Wins hat
            r = random.nextInt(24 + 1 - dog.getWins()) + dog.getWins();
            dog.setGot_ranked(r);

            //Es wird das Erfahrungslevel berechnet mit den Wins und den Platzierungen
            dog.setExperience_level(dog.getWins() + (dog.getGot_ranked()/2));

            //Es wird ein zuäflliger Wert für die eigentliche Anzahl der Teilnahmen an Rennen generiert
            r = random.nextInt(dog.getGot_ranked() + 2 - dog.getGot_ranked()) + dog.getGot_ranked();
            dog.setTook_place_in_races(r);

            //Es wird ein zuäflliger Wert für das Fitnesslevel generiert
            r = random.nextInt(9+1 - 1) + 1;
            dog.setFitness_level(r);

            //Es wird ein zuäflliger Wert für die Qualität des Trainers generiert (Effizienz des Trainers)
            r = random.nextInt(99+1 - 10) + 10;
            dog.setTrainer_quality(r);

            //Es wird ein zuäflliger Wert für den Einfluss des Wetters auf den Hund generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(21) - 10;
            dog.setWeather_influence(r);

            //Es wird ein zuäflliger Wert für den Einfluss des Gelände auf den Hund generiert
            //Dieser kann negativ (Schlechter Einfluss) sowohl als auch positiv (Guter Einfluss) sein
            r = random.nextInt(21) - 10;
            dog.setTerrain_influence(r);

            //Es wird die Gewinnchance berechnet, welche aus allen anderen Werten besteht
            double result = ((dog.getWins()+dog.getGot_ranked())/(double)dog.getTook_place_in_races())*(dog.getFitness_level() +
                    dog.getTrainer_quality() + dog.getExperience_level() + dog.getWeather_influence() + dog.getTerrain_influence());
            dog.setChance_of_winning((int)result);

            //Es wird der Mulitplier berechnet
            double tmp = 100 * ((double) 1 /dog.getChance_of_winning());
            tmp = tmp * 100;
            dog.setMultiplier(Math.round(tmp * 100.0) / 100.0);

            //Fertig generierter Hund wird zur Liste hinzugefügt
            participantList.add(dog);
        }

        //Streckenlänge wird übergeben
        int trackLength = dograce.getLength();

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
        dograce.setStart(startTime);
        dograce.setEnd(endTime);
        dograce.setEstimatedDuration(raceLength);

        //Übergibt die vollständige Teilnehmerliste
        dograce.setParticipants(participantList);

        //Setzt Status standardmäßig auf UPCOMING
        dograce.setStatus(RaceStatus.UPCOMING);

        //Erstellt das fertige Rennen
        return dograceRepository.save(dograce);
    }

    //Update Race
    public ResponseEntity<String> updateRace(String id, Dograce dograce) {
        if(dograceRepository.existsById(id)) {
            dograce.setId(id);
            dograceRepository.save(dograce);
            return ResponseEntity.ok("True");
        }
        return ResponseEntity.ok("False");
    }

    //Delete Race
    public void cancelRace(String id) {
        dograceRepository.deleteById(id);
    }

    public void prozessDograce(){
        LocalDateTime current = LocalDateTime.now();
        List<Dograce> checkList = getAllRaces();
        int count = 0;

        for (Dograce drace : checkList) {
            if (drace.getStatus() != RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count < 5 && checkList.size() <= 11) {
            createRace();
            count = 0;
        }

        for (Dograce drace : checkList){
            if (drace.getStatus() == RaceStatus.FINISHED) {
                count ++;
            }
        }

        if (count > 5 && checkList.size() > 10) {
            Dograce tmp = null;

            for (Dograce drace : checkList) {
                if (drace.getStatus() == RaceStatus.FINISHED) {
                    tmp = drace;
                    break;
                }
            }

            if (tmp != null){
                cancelRace(tmp.getId());
            }

            count = 0;
        }

        for (Dograce d : getAllRaces()) {
            if(d.getStart().isBefore(current)){
                d.setStatus(RaceStatus.ACTIVE);
                dograceRepository.save(d);
            }
        }

        for (Dograce d : getAllRaces()) {
            if(d.getEnd().isBefore(current)){
                d.setStatus(RaceStatus.FINISHED);
                dograceRepository.save(d);
            }
        }
    }
}
