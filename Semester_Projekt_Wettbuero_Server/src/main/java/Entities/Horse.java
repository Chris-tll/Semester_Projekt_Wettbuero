package Entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class Horse {
    @NonNull
    private int startNum;

    private String name;

    private int age;

    private int wins;

    private int took_place_in_races;

    private int got_ranked;

    private int fitness_level;

    private int experience_level;

    private int trainer_quality;

    private int jockey_quality;

    private int weather_influence;

    private int terrain_influence;

    private int chance_of_winning;

    private double multiplier;

    private double winner_chance;
}
