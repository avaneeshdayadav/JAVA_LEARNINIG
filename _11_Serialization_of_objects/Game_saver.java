// Serialization

import java.io.*;

class Game_character implements Serializable{

	int power;
	String type;
	String weapons[];

	Game_character(int p, String t, String[] w)
	{
		power = p;
		type = t;
		weapons = w;
	}

	public String getType() {
		return type;
	}
}


public class Game_saver {

	public static void main(String[] args)
	{
		Game_character one = new Game_character(20,"Elf", new String[] {"bow" , "sword" , "dust"});
		Game_character two = new Game_character(29, "Troll", new String[] {"bare hands" , "big-axe"});
		Game_character three = new Game_character(309, "Magician", new String[] {"spells" , "invisibility"});

		// We can have more code that plays with those values and changes current state of those variables.

		// Now save this state
		try
		{
			FileOutputStream outFile = new FileOutputStream("Game.ser");
			ObjectOutputStream os = new ObjectOutputStream(outFile);
			os.writeObject(one);
			os.writeObject(two);
			os.writeObject(three);
			os.close();
		catch(IOException ex)
		{
			ex.printStackTrace();
		}

		// We set them to null so we can’t access the objects on the heap.
		one = null;
		two = null;
		three = null;



		// Now read them back in from the file.
		try
		{
			FileInputStream inpFile = new FileInputStream("Game.ser");
			ObjectInputStream inpStr = new ObjectInputStream(inpFile);

			Game_character oneRestore = (Game_character) inpStr.readObject();
			Game_character twoRestore = (Game_character) inpStr.readObject();
			Game_character threeRestore = (Game_character) inpStr.readObject();

			System.out.println(oneRestore.getType());
			System.out.println(twoRestore.getType());
			System.out.println(threeRestore.getType());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
}

