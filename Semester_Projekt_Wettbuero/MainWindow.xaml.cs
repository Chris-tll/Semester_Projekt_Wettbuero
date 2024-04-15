using System;
using System.Collections.Generic;
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
    public partial class MainWindow : Window
    {
        User? user = null;
        List<Horserace> hraces = new List<Horserace>();
        public MainWindow()
        {
            InitializeComponent();
            ServerConnection sc = new ServerConnection();
            GetAllRaces();
            //login_grid.Visibility = Visibility.Visible;
            register_grid.Visibility = Visibility.Hidden;

            ComboBox_gender.Items.Add("Female");
            ComboBox_gender.Items.Add("Male");
            //Application.Current.MainWindow.WindowState = WindowState.Maximized;
            //Application.Current.MainWindow.WindowStyle = WindowStyle.None;
        }

        //-------------LOGIN-BTN---------------
        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            user = await ServerConnection.INSTANCE.GetUserByEmailAsync(TextBox_Name.Text);

            if (user != null)
            {
                bool password = await ServerConnection.INSTANCE.CheckPasswordAsync(user.Email, PasswordBox_Password.Password);
            }

            login_grid.Visibility = Visibility.Hidden;
            game_grid.Visibility = Visibility.Visible;
            Application.Current.MainWindow.Height = 900;
            Application.Current.MainWindow.Width = 1500;

            TextBlock_StartPage_Money.Text = user?.Money.ToString() + "€";
            TextBlock_StartPage_Username.Text = user?.Username.ToString();
        }

        //-------------PasswordChange Hide Placeholders-------------
        private void OnPasswordChanged(object sender, RoutedEventArgs e)
        {
            if (!string.IsNullOrEmpty(PasswordBox_Password.Password))
                Label_Password_Placeholder.Visibility = Visibility.Hidden;

            else
                Label_Password_Placeholder.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(PasswordBox_Password_Set.Password))
                Label_SetPass.Visibility = Visibility.Hidden;

            else
                Label_SetPass.Visibility= Visibility.Visible;

            if (!string.IsNullOrEmpty(PasswordBox_Password_Confirm.Password))
                Label_ConfirmPass.Visibility = Visibility.Hidden;

            else
                Label_ConfirmPass.Visibility = Visibility.Visible;
        }

        //-------------Change to Register Window-------------
        private void Btn_register_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.MainWindow.Height = 600;
            login_grid.Visibility = Visibility.Hidden;
            register_grid.Visibility = Visibility.Visible;
        }

        //-------------If REGISTER-BTN is pressed-------------
        private async void On_Btn_Register(object sender, RoutedEventArgs e)
        {
            // Validierung des Alters
            if (!ContainsOnlyNumbers(TextBox_Age.Text) || TextBox_Age.Text.Length > 3)
            {
                MessageBox.Show("Input not possible!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return; // Beenden Sie die Methode frühzeitig, wenn die Validierung fehlschlägt
            }

            // Validierung der Telefonnummer
            if (!ContainsOnlyNumbers(TextBox_Phone.Text) || TextBox_Phone.Text.Length < 12 || TextBox_Phone.Text.Length > 13)
            {
                MessageBox.Show("Valid length is 12-13 digits! OR it is not a number!" + TextBox_Phone.Text.Length + " is not a valid length!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return; // Beenden Sie die Methode frühzeitig, wenn die Validierung fehlschlägt
            }

            // Validierung der E-Mail-Adresse
            if (!IsValidEmail(TextBox_Email.Text))
            {
                MessageBox.Show("Not a valid email!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return; // Beenden Sie die Methode frühzeitig, wenn die Validierung fehlschlägt
            }

            if (!ConfirmPassword())
            {
                MessageBox.Show("Password does not match!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            bool success = await ServerConnection.INSTANCE.CreateUser(TextBox_Firstname.Text, TextBox_Lastname.Text, TextBox_Username.Text,
                ComboBox_gender.SelectedItem.ToString(), int.Parse(TextBox_Age.Text), TextBox_Phone.Text,
                TextBox_Email.Text, PasswordBox_Password_Set.Password.ToString());

            if (success)
            {
                login_grid.Visibility = Visibility.Visible;
                register_grid.Visibility = Visibility.Collapsed;
                Application.Current.MainWindow.Height = 450;
            }
        }

        //-------------Checks if there are only numbers in TextBox-------------
        static bool ContainsOnlyNumbers(string input)
        {
            // Regulärer Ausdruck für die Überprüfung, ob eine Zeichenfolge nur Zahlen enthält
            string pattern = @"^[0-9]+$";

            // Überprüfen, ob die Zeichenfolge dem regulären Ausdruck entspricht
            return Regex.IsMatch(input, pattern);
        }

        //-------------Checks with Pattern if it is a valid email-------------
        static bool IsValidEmail(string email)
        {
            // Regulärer Ausdruck für die Überprüfung einer E-Mail-Adresse
            string pattern = @"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$";

            // Überprüfen, ob die E-Mail-Adresse dem regulären Ausdruck entspricht
            return Regex.IsMatch(email, pattern);
        }

        //-------------Makes sure that first digit of FIRSTNAME and LASTNAME is ToUpper-------------
        private void TextBox_PreviewTextInput_FirstDigit_ToUpper(object sender, TextCompositionEventArgs e)
        {
            TextBox? textBox = sender as TextBox;
            string text = textBox.Text + e.Text;

            if (text.Length == 1) // Wenn das Eingabefeld leer ist, wird der erste Buchstabe groß geschrieben
            {
                textBox.Text = e.Text.ToUpper();
                textBox.CaretIndex = 1;
                e.Handled = true;
            }
            else if (text.Length > 1) // Ab dem zweiten Zeichen werden alle Buchstaben klein geschrieben
            {
                textBox.Text = text.Substring(0, 1).ToUpper() + text.Substring(1).ToLower();
                textBox.CaretIndex = text.Length;
                e.Handled = true;
            }
        }

        private void TextBox_LostFocus_FirstDigit_ToUpper(object sender, RoutedEventArgs e)
        {
            TextBox? textBox = sender as TextBox;
            textBox.Text = CultureInfo.CurrentCulture.TextInfo.ToTitleCase(textBox.Text.ToLower()); // Formatierung des Textes
        }

        //-------------Checks if password is confirmed-------------
        private bool ConfirmPassword()
        {
            if (PasswordBox_Password_Set.Password == PasswordBox_Password_Confirm.Password)
                return true;
            else
                return false;
        }

        //-------------Makes sure that the User can only type digits-------------
        private void TextBox_PreviewTextInput_Digits(object sender, TextCompositionEventArgs e)
        {
            // Überprüfen, ob das eingegebene Zeichen eine Zahl ist
            foreach (char c in e.Text)
            {
                if (!char.IsDigit(c)) // Überprüfen, ob das Zeichen keine Zahl ist
                {
                    e.Handled = true; // Das Ereignis abbrechen, wenn keine Zahl eingegeben wurde
                    return;
                }
            }
        }

        private void OnTextBoxGotInput(object sender, RoutedEventArgs e)
        {
            if (!string.IsNullOrEmpty(TextBox_Firstname.Text))
                Label_Firstname.Visibility = Visibility.Hidden;

            else
                Label_Firstname.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(TextBox_Lastname.Text))
                Label_Lastname.Visibility = Visibility.Hidden;

            else
                Label_Lastname.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(TextBox_Username.Text))
                Label_Username.Visibility = Visibility.Hidden;

            else
                Label_Username.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(TextBox_Age.Text))
                Label_Age.Visibility = Visibility.Hidden;

            else
                Label_Age.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(TextBox_Phone.Text))
                Label_Phone.Visibility = Visibility.Hidden;

            else
                Label_Phone.Visibility = Visibility.Visible;

            if (!string.IsNullOrEmpty(TextBox_Email.Text))
                Label_Email.Visibility = Visibility.Hidden;

            else
                Label_Email.Visibility = Visibility.Visible;
        }

        //-------------Change window when pressing button horserace-------------
        private async void Button_HorseRace_Click(object sender, RoutedEventArgs e)
        {
            LoadAllHorseraces();

            HorseRace_grid.Visibility = Visibility.Visible;
            game_grid.Visibility = Visibility.Hidden;
        }

        private async void GetAllRaces()
        {
            hraces = await ServerConnection.INSTANCE.GetHorseraceAsync();
        }

        private async void LoadAllHorseraces()
        {
            foreach (Horserace h in hraces)
            {
                if (h.status != "FINISHED")
                {
                    ListView_Horserace_Upcoming.Items.Add(h.ToString());
                }
                else
                {
                    ListView_Horserace_Finished.Items.Add(h.ToString());
                }
            }
        }

        private async void ListView_Horserace_Upcoming_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            SetInfo_Horserace();
        }

        private void ListView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            SetInfo_Horserace();
        }

        private async void SetInfo_Horserace()
        {
            ListView_Horserace_Info.Items.Clear();

            string tmp = "";
            tmp = ListView_Horserace_Upcoming.SelectedItem as string;

            // Split the string by newline characters ('\n')
            string[] parts = tmp.Split('\n');

            // Extract the location from the second part after removing leading and trailing spaces
            string location = parts[1].Substring("Location: ".Length).Trim();

            Horserace h = await ServerConnection.INSTANCE.GetHorseraceByLocation(location);

            ListView_Horserace_Info.Items.Add(h.RaceInformations());
        }

        private void Btn_Horserace_Upcoming_Reload_Click(object sender, RoutedEventArgs e)
        {
            GetAllRaces();
            LoadAllHorseraces();
        }
    }
}
