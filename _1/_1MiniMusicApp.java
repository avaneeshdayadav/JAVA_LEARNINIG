/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import javax.sound.midi.*;

class _1MiniMusicApp
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
	    System.out.println("hello");
	    _1MiniMusicApp mini = new _1MiniMusicApp();
		9mini.play();
	}
	
	public void play() {
        try
        {

            // get a Sequencer and open it (so we can use it... a Sequencer doesn’t come already open)
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            // Don’t worry about the arguments to the Sequence constructor. Just copy these (think of ‘em as Ready-bake arguments)
            Sequence seq = new Sequence(Sequence.PPQ, 4);

            // Ask the Sequence for a Track. Remember, the Track lives in the Sequence, and the MIDI data lives in the Track.
            Track track = seq.createTrack();

            // Prepare a message that has data about the Midi events. See pg.334 for info on arguments of message. 192 to change the instrument, 144 means noteOn & 128 means noteOff.
            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 50, 100);

            // Pass message to MidiEvent with starting duration of the beat.
            MidiEvent noteOn = new MidiEvent(a, 1);

            // Add this MidiEvent to track.
            track.add(noteOn);


            // In same way prepare a note off message and MidiEvent for above note to get off.
            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 50, 100);
            MidiEvent noteOff = new MidiEvent(b, 40);
            track.add(noteOff);

            // Add this sequence to the player.
            player.setSequence(seq);


            // Play the sequence containing this added track which further contains MidiEvent dat
            player.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    } // close play
}
