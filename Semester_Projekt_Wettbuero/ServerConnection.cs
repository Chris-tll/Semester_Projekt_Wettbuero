using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO.Packaging;
using System.Linq;
using System.Net.Http;
using System.Reflection;
using System.Security;
using System.Security.AccessControl;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace Semester_Projekt_Wettbuero
{
    class ServerConnection
    {
        public static ServerConnection INSTANCE;
        string baseUrl = "http://127.0.0.1:8080";
        HttpClient client = new HttpClient();
        HttpResponseMessage response = new HttpResponseMessage();

        public ServerConnection()
        {
            INSTANCE = this;
        }

        //----------------------------USER----------------------------
        public async Task<bool> CreateUser(string firstname, string lastname,
            string username, string gender, int age, string phone, string email, string password)
        {
            string apiUrl = baseUrl + "/users";

            try
            {
                User? tmpU = await GetUserByUsernameAsync(username);
                User? tmpE = await GetUserByEmailAsync(email);

                if (tmpU == null && tmpE == null)
                {
                    var user = new
                    {
                        firstname = firstname,
                        lastname = lastname,
                        username = username,
                        gender = gender,
                        age = age,
                        phone = phone,
                        email = email,
                        password = password
                    };

                    using (var httpClient = new HttpClient())
                    {
                        var content = new StringContent(Newtonsoft.Json.JsonConvert.SerializeObject(user), Encoding.UTF8, "application/json");

                        var response = await httpClient.PostAsync(apiUrl, content);

                        if (response.IsSuccessStatusCode)
                        {
                            MessageBox.Show("User successfully created!", "Info", MessageBoxButton.OK, MessageBoxImage.Information);
                        }
                        else
                        {
                            MessageBox.Show($"Failes to create user! Status Code: {response.StatusCode}");
                        }
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occured! Error: {ex.Message}");
                return false;
            }
        }

        public async Task<User?> GetUserByUsernameAsync(string username)
        {
            string requestUrl = $"{baseUrl}/users/username/{username}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        User? user = JsonSerializer.Deserialize<User>(values);
                        return user;
                    }
                    else { return null; }
                }
                catch (Exception)
                {
                    MessageBox.Show("Username does not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<User?> GetUserByEmailAsync(string email)
        {
            string requestUrl = $"{baseUrl}/users/email/{email}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {

                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        User? user = JsonSerializer.Deserialize<User>(values);
                        return user;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Email does not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<bool> CheckPasswordAsync(string email, string password)
        {
            string requestUrl = $"{baseUrl}/users/password/{email}/{password}";
            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    bool passwordCorrect = JsonSerializer.Deserialize<bool>(values);

                    if (passwordCorrect)
                    {
                        MessageBox.Show("Password is correct!", "Info", MessageBoxButton.OK, MessageBoxImage.Information);
                        return true;
                    }
                    else
                    {
                        MessageBox.Show("Password is incorrect!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        return false;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("General Error occured!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return false;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return false;
            }

        }

        public async Task<bool> UpdateUser(User user)
        {
            string apiUrl = baseUrl + "/users/" + user.id;

            try
            {
                var u = new
                {
                    firstname = user.firstname,
                    lastname = user.lastname,
                    username = user.username,
                    gender = user.gender,
                    age = user.age,
                    role = user.role,
                    phone = user.phone,
                    email = user.email,
                    password = user.password,
                    money = user.money,
                    money_loss = user.money_loss,
                    money_win = user.money_win,
                    allBets = user.allBets
                };

                using (var httpClient = new HttpClient())
                {
                    var content = new StringContent(Newtonsoft.Json.JsonConvert.SerializeObject(u), Encoding.UTF8, "application/json");

                    var response = await httpClient.PutAsync(apiUrl, content);
                }
                return true;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occured! Error: {ex.Message}");
                return false;
            }
        }

        //----------------------------BETS----------------------------
        public async Task<List<Bet>> GetAllBets()
        {
            string requestUrl = $"{baseUrl}/bet";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Bet> bets = JsonSerializer.Deserialize<List<Bet>>(values);
                        return bets;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Error while reloading bets!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<List<Bet>> GetBetsById(string id)
        {
            string requestUrl = $"{baseUrl}/bet/{id}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {

                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Bet>? bets = JsonSerializer.Deserialize<List<Bet>>(values);
                        return bets;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Bets do not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<bool> CreateBet(double money_bet, string participantType,
            string participantName, int startNum, string raceType, string raceId, string userId, string status)
        {
            string apiUrl = baseUrl + "/bet";

            try
            {
                List<Bet> bets = await GetBetsById(userId);

                foreach (Bet bet in bets)
                {
                    if (bet.raceId.Equals(raceId))
                    {
                        MessageBox.Show("You can not bet on the same race twice!!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        return false;
                    }
                }
                var setBet = new
                {
                    money_bet = money_bet,
                    participantType = participantType,
                    participantName = participantName,
                    startNum = startNum,
                    raceType = raceType,
                    raceId = raceId,
                    userId = userId,
                    status = status,
                };

                using (var httpClient = new HttpClient())
                {
                    var content = new StringContent(Newtonsoft.Json.JsonConvert.SerializeObject(setBet), Encoding.UTF8, "application/json");

                    var response = await httpClient.PostAsync(apiUrl, content);

                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Bet successfully created!", "Info", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                    else
                    {
                        MessageBox.Show($"Failes to create Bet! Status Code: {response.StatusCode}");
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occured! Error: {ex.Message}");
                return false;
            }
        }

        //----------------------------HORSRACE----------------------------
        public async Task<List<Horserace>> GetHorseraceAsync()
        {
            string requestUrl = $"{baseUrl}/horserace";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Horserace> participants = JsonSerializer.Deserialize<List<Horserace>>(values);

                        foreach (Horserace hrace in participants)
                        {
                            hrace.racestart = convertDateTime(hrace.racestart);
                        }

                        return participants;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Error while reloading races!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<Horserace> GetHorseraceByLocation(string loc)
        {
            string requestUrl = $"{baseUrl}/horserace/location/{loc}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        Horserace? h = JsonSerializer.Deserialize<Horserace>(values);
                        return h;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Email does not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public string convertDateTime(string value)
        {
            DateTime javaDateTime = DateTime.Parse(value, CultureInfo.InvariantCulture, DateTimeStyles.AssumeUniversal);

            // Formatieren und anzeigen des Datums und der Uhrzeit ohne Sekunden
            string formattedDateTime = javaDateTime.ToString("yyyy-MM-dd HH:mm", CultureInfo.InvariantCulture);

            return formattedDateTime;
        }


        //----------------------------DOGRACE----------------------------
        public async Task<List<Dograce>> GetDograceAsync()
        {
            string requestUrl = $"{baseUrl}/dograce";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Dograce> participants = JsonSerializer.Deserialize<List<Dograce>>(values);

                        foreach (Dograce drace in participants)
                        {
                            drace.racestart = convertDateTime(drace.racestart);
                        }

                        return participants;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Error while reloading races!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<Dograce> GetDograceByLocation(string loc)
        {
            string requestUrl = $"{baseUrl}/dograce/location/{loc}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        Dograce? d = JsonSerializer.Deserialize<Dograce>(values);
                        return d;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Email does not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }


        //----------------------------SNAILRACE----------------------------
        public async Task<List<Snailrace>> GetSnailraceAsync()
        {
            string requestUrl = $"{baseUrl}/snailrace";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Snailrace> participants = JsonSerializer.Deserialize<List<Snailrace>>(values);

                        List<Snailrace> tmplist = new List<Snailrace>();
                        foreach (Snailrace srace in participants)
                        {
                            srace.start = convertDateTime(srace.start);
                            tmplist.Add(srace);
                        }

                        return tmplist;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Error while reloading races!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        public async Task<Snailrace> GetSnailraceByLocation(string loc)
        {
            string requestUrl = $"{baseUrl}/snailrace/location/{loc}";

            response = await client.GetAsync(requestUrl);

            if (response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        Snailrace? s = JsonSerializer.Deserialize<Snailrace>(values);
                        return s;
                    }
                    else
                    {
                        return null;
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Email does not exist!!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }
            }
            else
            {
                MessageBox.Show("Error: " + response.StatusCode + " " + response.ReasonPhrase, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }
    }
}
