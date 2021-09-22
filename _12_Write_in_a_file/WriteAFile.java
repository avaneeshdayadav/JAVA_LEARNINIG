import java.io.*;

public class WriteAFile{
	public static void main(String[] args){
		try
		{
			// Although its opening and closing again and again but it always overwrite prev content and don't append.
			for(int i=0;i<4;i++)
			{
				FileWriter writer = new FileWriter("write_in_me.txt");
				writer.write("Hello this is a experimental file.\nHow are you all doing ?"+i);
				writer.close();
			}
	
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}