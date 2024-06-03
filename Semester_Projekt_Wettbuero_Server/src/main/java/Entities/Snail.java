package Entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class Snail {

    @NonNull
    private int startNum;

    private String name;

    private int age;

    private double size;

    private int speed;

    private int stamina;

    private int reaction;

    private int weather_influence;

    private int unpredictability;

    private int environment_influence;

    private int curiosity;

    private int temperament;

    private int chance_of_winning;

    private double multiplier;

    private double winner_chance;
}
