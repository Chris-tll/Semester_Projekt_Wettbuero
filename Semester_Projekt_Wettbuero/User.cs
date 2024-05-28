
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    public class User
    {
        public string id { get; set; }
        public string firstname { get; set; }
        public string lastname { get; set; }
        public string username { get; set; }
        public string gender { get; set; }
        public int age { get; set; }
        public string role { get; set; }
        public string phone { get; set; }
        public string email { get; set; }
        public string password { get; set; }
        public double money { get; set; }
        public double money_loss { get; set; }
        public double money_win { get; set; }
        public List<Bet>? allBets { get; set; }

    }
}
