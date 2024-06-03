using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.AccessControl;
using System.Text;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    class Snailrace
    {
        public string id { get; set; }
        public string name { get; set; }

        public string location { get; set; }

        public int num_of_participants { get; set; }

        public List<Snail> participants { get; set; }

        public int length { get; set; }

        public string weather { get; set; }

        public string terrain { get; set; }

        public string environment { get; set; }

        public string start { get; set; }

        public string end { get; set; }

        public int estimatedDuration { get; set; }

        public string status { get; set; }

        public string type { get; set; }

        public string ToString()
        {
            string s = "Name: " + this.name + "\n" +
                "Location: " + this.location + "\n";

            return s;
        }

        public string RaceInformations()
        {
            string s = "Name: " + this.name + "\n\n" +
                "Location: " + this.location + "\n\n" +
                "Num. Participants: " + this.num_of_participants + "\n\n" +
                "Length: " + this.length + " m" + "\n\n" +
                "Weather: " + this.weather + "\n\n" +
                "Terrain: " + this.terrain + "\n\n" +
                "Environment: " + this.environment + "\n\n" +
                "Start: " + this.start + "\n\n" +
                "End: " + this.end + "\n\n" +
                "Estimated Duration: " + this.estimatedDuration + "min." + "\n\n" +
                "Status: " + this.status + "\n\n";

            return s;
        }
    }
}
