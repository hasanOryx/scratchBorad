#r "SS.dll"

using System;
using System.Speech.Synthesis;
using System.Speech.Recognition;

async (input) => {
      Console.WriteLine("Hi");

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

      return null;
}
