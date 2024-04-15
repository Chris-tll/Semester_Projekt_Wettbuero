package Entities;

import lombok.NonNull;
import org.springframework.data.annotation.Id;

public class Dog {
    @Id
    @NonNull
    private String id;

    private String name;

    private int age;

    private int wins;

    private int took_place_in_races;

    private int got_ranked;

    private int fitness_level;

    private int experience_level;

    private int trainer_quality;

    private int weather_influence;

    private int terrain_influence;

    private int chance_of_winning;
}
