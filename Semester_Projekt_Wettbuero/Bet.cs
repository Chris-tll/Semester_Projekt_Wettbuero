using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.AccessControl;
using System.Text;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    public class Bet
    {
        public string id { get; set; }
        public double money_bet { get; set; }
        public string participantType { get; set; }
        public string participantName { get; set; }
        public int startNum { get; set; }
        public string raceType { get; set; }
        public string raceId { get; set; }
        public string userId { get; set; }
        public string status { get; set; }

        public string getBetInfo()
        {
            return $"RaceID: {raceId}\nRace_Type: {raceType}\nParticipant_Type: {participantType}\n" +
                   $"Participant_Name: {participantName}\nStart_Num.: {startNum}\nMoney: {money_bet}€\n" +
                   $"User: {userId}\nStatus: {status}";
        }
    }
}
