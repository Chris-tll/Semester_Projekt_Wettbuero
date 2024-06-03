using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    class Snail
    {
        public int startNum { get; set; }

        public string name { get; set; }

        public int age { get; set; }

        public double size { get; set; }

        public int speed { get; set; }

        public int stamina { get; set; }

        public int reaction { get; set; }

        public int weather_influence { get; set; }

        public int unpredictability { get; set; }

        public int environment_influence { get; set; }

        public int curiosity { get; set; }

        public int temperament { get; set; }

        public int chance_of_winning { get; set; }

        public double multiplier { get; set; }

        public string getParticipantInfo()
        {
            string s = "StartNum.: " + this.startNum + "\n" + "Name: " + this.name + "\n" + "Age: " + this.age + " years old\n" +
                "Size: " + this.size + " cm\n" + "Speed: " + this.speed + "cm/s\n" +
                "Stamina: " + this.stamina + "\n" + "Reaction: " + this.reaction + "s\n" +
                "Weather influence: " + this.weather_influence + "\n" +
                "Unpredictability: " + this.unpredictability + "\n" + "Environment influence: " + this.environment_influence + "\n" +
                "Curiosity: " + this.curiosity + "\n" + "Temperament: " + this.temperament + "\n" +
                "Chance of Winning: " + this.chance_of_winning + "\n" + "Multiplier: " + this.multiplier + "\n\n";

            return s;
        }
    }
}
