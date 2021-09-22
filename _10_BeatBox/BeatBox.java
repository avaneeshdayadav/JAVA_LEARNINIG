import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;


public class BeatBox{

	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;

	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat","Acoustic Snare", "Crash Cymbal", "Hand Clap", 
	"High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};

	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main (String[] args) {
		new BeatBox().buildGUI();
	}


	public void buildGUI()
	{
		theFrame = new JFrame("My Own Beatbox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);	// We could have also used .setLayout() on background object.
		
		// An ‘empty border’ gives us a margin between the edges of the panel and where the components are placed. Purely aesthetic
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		checkboxList = new ArrayList<JCheckBox>();

		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		JButton start = new JButton("Start");						// Make a start button
		start.addActionListener(new MyStartListener());				// Register start button with MyStartListener object
		buttonBox.add(start);										// Add this start button to box of buttons.


		JButton stop = new JButton("Stop");							// Make a stop button
		stop.addActionListener(new MyStopListener());				// Register stop button with MyStopListener object
		buttonBox.add(stop);										// Add this stop button to box of buttons.


		JButton upTempo = new JButton("Tempo Up");					// Make a TempoUp button similarly.
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);


		JButton downTempo = new JButton("Tempo Down");				// Make a TempoDown button similarly.
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);


		Box nameBox = new Box(BoxLayout.Y_AXIS);					// Make another box for all instrument names.

		for (int i = 0; i < 16; i++)							
		{
			nameBox.add(new Label(instrumentNames[i]));				// Add all instrument names in this names box.
		}

		background.add(BorderLayout.EAST, buttonBox);				// Add buttonBox box on background panel in east.
		background.add(BorderLayout.WEST, nameBox);					// Add nameBox box on background panel in west.

		theFrame.getContentPane().add(background);					// Add this background panel on frame.

		GridLayout grid = new GridLayout(16,16);					// Create a grid layout object that will show all added elements(checkboxes) in 16x16 matrix.
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);								// Create a panel of grid layout.
		background.add(BorderLayout.CENTER, mainPanel);				// Add this grid panel to background panel in center.

		for (int i = 0; i < 256; i++)
		{
			JCheckBox c = new JCheckBox();							// Creatr a check box object.
			c.setSelected(false);									// Make checkbox untick.
			checkboxList.add(c);									// Add this checkbox to the check box array.
			mainPanel.add(c);										// Add this checkbox on panel with grid layout.
		} // end loop

		setUpMidi();


		/*
		The pack method sizes the frame so that all its contents are at or above their preferred sizes. An alternative to pack 
		is to establish a frame size explicitly by calling setSize or setBounds (which also sets the frame location).In general,
		using pack is preferable to calling setSize, since pack leaves the frame layout manager in charge of the frame size, and
		layout managers are good at adjusting to platform dependencies and other factors that affect component size.
		*/
		theFrame.setBounds(60,50,300,300);							// (x,y,width,height)
		theFrame.pack();


		theFrame.setVisible(true);									// Make this frame visible.

	} // close method

	public void setUpMidi()
	{
		try {
		sequencer = MidiSystem.getSequencer();						// Get a sequencer				
		sequencer.open();											// Open sequencer
		sequence = new Sequence(Sequence.PPQ,4);					// Create a sequence
		track = sequence.createTrack();								// Create a track
		sequencer.setTempoInBPM(120);								// Set Beats per min = 120
		} catch(Exception e) {e.printStackTrace();}
	} // close method



	// This method is called when start button clicked.
	public void buildTrackAndStart()
	{
		int[] trackList = null;										// Data (16 checkbox) for one row (instrument) is stored in this list.

		sequence.deleteTrack(track);								// Delete the previous track on sequence.
		track = sequence.createTrack();								// Make a fresh track.


		for (int i = 0; i < 16; i++)
		{
			trackList = new int[16];

			int key = instruments[i];								// Take the instrument number.

			// Do this 16 times for each row.
			for (int j = 0; j < 16; j++ )
			{
				JCheckBox jc = checkboxList.get(j + 16*i);			// Grab the checkbox (numbered form 0 to 255)

				if ( jc.isSelected())
				{
					trackList[j] = key;								// If checkbox ticked then store instrument number in the tracklist else store zero.
				}
				else
				{
					trackList[j] = 0;
				}
			} // close inner loop

			makeTracks(trackList);									// Send this tracklist containing one row data, for track making i.e note off and note on.

			//track.add(makeEvent(176,1,127,0,16));					


		} // close outer

		track.add(makeEvent(192,9,1,0,15));							// Change instrument at last beat of this instrument.
		
		try {

		sequencer.setSequence(sequence);							// Add sequence to the sequencer
		sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);		// Loop this sequence infinite times.
		sequencer.start();											// Play the sequence.
		sequencer.setTempoInBPM(120);								// Set Beat per min of the sequence.

		} catch(Exception e) {e.printStackTrace();}

	}// close buildTrackAndStart method
	


	public class MyStartListener implements ActionListener
	{

		public void actionPerformed(ActionEvent a)
		{
			buildTrackAndStart();
		}

	} // close inner class

	public class MyStopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	} // close inner class



	/*
		The Tempo Factor scales the sequencer’s tempo by the factor provided. The default is 1.0, so we’re adjusting +/- 3% per click
	*/
	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
		}
	} // close inner class


	public class MyDownTempoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * .97));
		}
	} // close inner class


	public void makeTracks(int[] list)
	{
		for (int i = 0; i < 16; i++)
		{
			int key = list[i];

			if (key != 0)
			{
				track.add(makeEvent(144,9,key, 100, i));					// Add Note ON event at nth interval 
				track.add(makeEvent(128,9,key, 100, i+1));					// Add Note OFF event at (n+1)th interval.
			}
		}
	}



	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick)
	{
		MidiEvent event = null;
		try
		{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		}catch(Exception e) {e.printStackTrace(); }

		return event;
	}


} // close class