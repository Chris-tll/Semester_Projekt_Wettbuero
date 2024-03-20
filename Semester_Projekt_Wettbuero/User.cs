
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Semester_Projekt_Wettbuero
{
    class User
    {
        private string email;
        private string username;
        private string password;

        [JsonPropertyName("email")]
        public string Email { get => email; set => email = value; }

        [JsonPropertyName("username")]
        public string Username { get => username; set => username = value; }

        [JsonPropertyName("password")]
        public string Password { get => password; set => password = value; }
    }
}
