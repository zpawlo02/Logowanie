using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LogowaniedoApki
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string poprawnyLogin = "gigant";
            string poprawneHaslo = "gigant2020";

            if(poprawnyLogin == loginTextBox.Text && poprawneHaslo == passwordTextBox.Text)
            {
                MessageBox.Show("Zalogowano poprawnie", "Komunikat", MessageBoxButtons.OK);
            }
            else
            {
                MessageBox.Show("Login lub haslo niepoprawne", "Blad", MessageBoxButtons.OK);
            }
        }
    }
}
