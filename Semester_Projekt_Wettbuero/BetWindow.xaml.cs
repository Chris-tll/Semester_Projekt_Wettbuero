using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Semester_Projekt_Wettbuero
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class BetWindow : Window
    {
        User user = null;
        string raceMode = "", participantType = "", raceId = "", name = "";
        public BetWindow(string loc, User u, string mode, string participant, string id)
        {
            InitializeComponent();
            user = u;
            raceMode = mode;
            participantType = participant;
            raceId = id;
            Btn_SetBet.IsEnabled = false;
            GetRaceByLoc(loc);
        }

        private void ListView_Participants_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            string? tmp = ListView_Participants.SelectedItem as string;

            string[] parts = tmp.Split('\n');

            // Extract the location from the second part after removing leading and trailing spaces
            name = parts[0].Substring("Name: ".Length).Trim();
            Btn_SetBet.IsEnabled = true;
        }

        public async Task GetRaceByLoc(string loc)
        {
            ListView_Participants.Items.Clear();
            Console.WriteLine(participantType);

            if (participantType.Equals("HORSE"))
            {
                Horserace hrace = await ServerConnection.INSTANCE.GetHorseraceByLocation(loc);

                foreach (Horse horse in hrace.participants)
                {
                    ListView_Participants.Items.Add(horse.getParticipantInfo());
                }
                Label_Participants.Content = "Participants: " + hrace.totalParticipants;
            }
            else if (participantType.Equals("DOG"))
            {
                Dograce drace = await ServerConnection.INSTANCE.GetDograceByLocation(loc);

                foreach (Dog dog in drace.participants)
                {
                    ListView_Participants.Items.Add(dog.getParticipantInfo());
                }
                Label_Participants.Content = "Participants: " + drace.totalParticipants;
            }
            else if (participantType.Equals("SNAIL"))
            {
                Snailrace srace = await ServerConnection.INSTANCE.GetSnailraceByLocation(loc);

                foreach (Snail snail in srace.participants)
                {
                    ListView_Participants.Items.Add(snail.getParticipantInfo());
                }
                Label_Participants.Content = "Participants: " + srace.num_of_participants;
            }
        }

        private async void Btn_SetBet_Click(object sender, RoutedEventArgs e)
        {
            if (Convert.ToInt32(TextBox_SetBet.Text) <= user.money && TextBox_SetBet.Text != null) {
                await ServerConnection.INSTANCE.CreateBet(Convert.ToDouble(TextBox_SetBet.Text), 
                    participantType, name, raceMode, raceId, user.id, "OUTSTANDING");
            }
            else
            {
                MessageBox.Show("You do not own enough money!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
