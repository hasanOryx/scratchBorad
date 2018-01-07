using System;
using SS.dll;
using System.Runtime.InteropServices;
/*
namespace HelloWorld
{
class Example
{
    // Use DllImport to import the Win32 MessageBox function.
    [DllImport("user32.dll", CharSet = CharSet.Unicode)]
    public static extern int MessageBox(IntPtr hWnd, String text, String caption, uint type);

    static void Main()
    {
        // Call the MessageBox function using platform invoke.
        MessageBox(new IntPtr(0), "Hello World!", "Hello Dialog", 0);
    }
}
}
*/
class Example
{
    // Use DllImport to import the Win32 MessageBox function.
    [DllImport("System.Speech.dll", CharSet = CharSet.Unicode)]
    public static extern void MessageBox()
        {

            // Initialize a new instance of the SpeechSynthesizer.
            SpeechSynthesizer synth = new SpeechSynthesizer();

            // Configure the audio output. 
            synth.SetOutputToDefaultAudioDevice();

            Console.WriteLine("Press any key to exit...");
            Console.ReadKey();
            // Speak a string.
            synth.Speak("Dana Hasan and Maha, I LOVE you, so can you please take care of Yara and Karam");

            Console.WriteLine();
            Console.WriteLine("Press any key to exit...");
            Console.ReadKey();

            // Create a new SpeechRecognitionEngine instance.
            SpeechRecognizer recognizer = new SpeechRecognizer();

            // Create a simple grammar that recognizes "red", "green", or "blue".
            Choices colors = new Choices();
            colors.Add(new string[] { "red", "green", "blue" });

            // Create a GrammarBuilder object and append the Choices object.
            GrammarBuilder gb = new GrammarBuilder();
            gb.Append(colors);

            // Create the Grammar instance and load it into the speech recognition engine.
            Grammar g = new Grammar(gb);
            recognizer.LoadGrammar(g);

            // Register a handler for the SpeechRecognized event.
            recognizer.SpeechRecognized +=
              new EventHandler<SpeechRecognizedEventArgs> (sre_SpeechRecognized);

        }

        // Create a simple handler for the SpeechRecognized event
        void sre_SpeechRecognized (object sender, SpeechRecognizedEventArgs e)
        {
            Console.WriteLine("Speech recognized: {0}", e.Result.Text);
        }


    static void Main()
    {
        // Call the MessageBox function using platform invoke.
        MessageBox();
    }
}
