using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    class Horserace
    {
        public string id { get; set; }

        [JsonPropertyName("name")]
        public string racename {  get; set; }

        [JsonPropertyName("location")]
        public string racelocation { get; set; }

        [JsonPropertyName("num_of_participants")]
        public int totalParticipants { get; set; }

        [JsonPropertyName("participants")]
        public List<Horse> participants { get; set; }

        [JsonPropertyName("length")]
        public int racelength { get; set; }

        public string weather { get; set; }

        public string terrain { get; set; }

        [JsonPropertyName("start")]
        public string racestart { get; set; }

        [JsonPropertyName("end")]
        public string raceend { get; set; }

        public int estimatedDuration { get; set; }

        public string status { get; set; }

        public string type { get; set; }

        public string ToString()
        {
            string s = "Name: " + this.racename + "\n" +
                "Location: " + this.racelocation + "\n";

            return s;
        }

        public string RaceInformations()
        {
            string s = "Name: " + this.racename + "\n\n" +
                "Location: " + this.racelocation + "\n\n" +
                "Num. Participants: " + this.totalParticipants + "\n\n" +
                "Length: " + this.racelength + " m" + "\n\n" +
                "Weather: " + this.weather + "\n\n" +
                "Terrain: " + this.terrain + "\n\n" +
                "Start: " + this.racestart + "\n\n" +
                "End: " + this.raceend + "\n\n" +
                "Estimated Duration: " + this.estimatedDuration + "min." + "\n\n" +
                "Status: " + this.status + "\n\n";

            return s;
        }
    }
}
