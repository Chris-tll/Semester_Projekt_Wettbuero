using System;
using System.Collections.Generic;
using System.IO.Packaging;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;

namespace Semester_Projekt_Wettbuero
{
    class ServerConnection
    {
        public static ServerConnection INSTANCE;
        string baseUrl = "http://10.10.2.71:8080";
        HttpClient client = new HttpClient();
        HttpResponseMessage response = new HttpResponseMessage();

        public ServerConnection()
        {
            INSTANCE = this;
        }

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
            
            if(response.IsSuccessStatusCode)
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

            if(response.IsSuccessStatusCode)
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

        //----------------------------HORSRACE----------------------------
        public async Task<List<Horserace>> GetHorseraceAsync()
        {
            string requestUrl = $"{baseUrl}/horserace";

            response = await client.GetAsync(requestUrl);

            if(response.IsSuccessStatusCode)
            {
                try
                {
                    string values = await response.Content.ReadAsStringAsync();
                    if (values != "")
                    {
                        List<Horserace> participants = JsonSerializer.Deserialize<List<Horserace>>(values);
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
    }
}
