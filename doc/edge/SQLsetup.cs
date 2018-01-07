using System;
using System.Threading.Tasks;

namespace SAPLibrary
{
        public class SQLsetup
    {
        public void Invoke()
        {
            String envName = "EDGE_SQL_CONNECTION_STRING";
            String envValue = "Data ";
         //   String envValue = "Data Source=BK-SAP;Initial Catalog=BK-LIVE;user id=sa;password=sql@123";


            // Determine whether the environment variable exists.
            if (Environment.GetEnvironmentVariable(envName) == null){
                // If it doesn't exist, create it.
                      Console.WriteLine("Enter Server Name/IP: ");
                            string Source = Console.ReadLine();
                      Console.WriteLine("Enter DataBase Name: ");
                            string Catalog = Console.ReadLine();
                      Console.WriteLine("Enter DB user name: ");
                            string id = Console.ReadLine();
                      Console.WriteLine("Enter DB password: ");
                            string password = Console.ReadLine();
                 envValue += "Source="+Source+";Initial Catalog="+Catalog+";user id="+id+";password="+password;  
                 Environment.SetEnvironmentVariable(envName, envValue);    
            }
            else
            {
             Console.WriteLine("Data Base connection setup is ready ");     
            }
         //   return;
        }
    }
}