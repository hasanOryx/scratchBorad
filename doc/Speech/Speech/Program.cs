using System;
using System.Speech.Synthesis;
using System.Speech.Recognition;

namespace SampleSynthesis
{
    class Program
    {
        static void Main(string[] args)
        {

            // Initialize a new instance of the SpeechSynthesizer.
            SpeechSynthesizer synth = new SpeechSynthesizer();

            // Configure the audio output. 
            synth.SetOutputToDefaultAudioDevice();

            // Speak a string.
            synth.Speak("This example demonstrates a basic use of Speech Synthesizer");

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
            recognizer.SpeechRecognized += sre_SpeechRecognized;
           //   new EventHandler<SpeechRecognizedEventArgs> (sre_SpeechRecognized);

        }

        // Create a simple handler for the SpeechRecognized event
        static void sre_SpeechRecognized (object sender, SpeechRecognizedEventArgs e)
        {
            Console.WriteLine("Speech recognized: {0}", e.Result.Text);
        }
    }
}
