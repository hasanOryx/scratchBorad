using System;
using System.Threading.Tasks;
/*
async (input) => {
      Console.WriteLine("Hi");
      await callMe();
      Console.WriteLine("Done");
      return null;
}
void callMe ()
{
      Console.WriteLine("Bye");
}
*/
/*
async (input) => {
        Console.WriteLine("Start");;
        return (Func<object,Task<object>>)(async (i) => { 
            Console.WriteLine("Hello from .NET {0}", i); 
            return null; 
        });
    }
    */
    public class Startup
    {
        public async Task<object> Invoke(int startingSalary)
        {
            Console.WriteLine("1");
            
            return new {
                getSalary = (Func<object,Task<object>>)(
                    async (i) => 
                    { 
                        Console.WriteLine("2"); 
                        await giveRaise(20, true);
                        return null;
                    }
                ),
                giveRaise = (Func<object,Task<object>>)(
                    async (amount) => 
                    { 
                        Console.WriteLine("3");
                        return null;
                    }
                )
            };
        }
    }

