using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    class Dog
    {
        public int startNum { get; set; }

        public string name { get; set; }

        public int age { get; set; }

        public int wins { get; set; }

        public int took_place_in_races { get; set; }

        public int got_ranked { get; set; }

        public int fitness_level { get; set; }

        public int experience_level { get; set; }

        public int trainer_quality { get; set; }

        public int weather_influence { get; set; }

        public int terrain_influence { get; set; }

        public int chance_of_winning { get; set; }

        public double multiplier { get; set; }

        public Dog winner { get; set; }

        public string getParticipantInfo()
        {
            string s = "Name: " + this.name + "\n" + "Age: " + this.age + " years old\n" +
                "Wins: " + this.wins + "\n" + "Participated in races: " + this.took_place_in_races + " times\n" +
                "Got ranked: " + got_ranked + " times \n" + "Fitness level: " + this.fitness_level + "\n" +
                "Experience level: " + this.experience_level + "\n" + "Trainer quality: " + this.trainer_quality + "% \n" +
                "Weather influence" + this.weather_influence + "\n" +
                "Terrain influence: " + this.terrain_influence + "\n" + "Chance of Winning: " + this.chance_of_winning + "\n" +
                "Multiplier: " + this.multiplier + "\n\n";

            return s;
        }
    }
}
