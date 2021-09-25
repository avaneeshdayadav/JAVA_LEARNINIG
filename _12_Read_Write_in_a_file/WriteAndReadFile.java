import java.io.*;

public class WriteAndReadFile{
	public static void main(String[] args){

		// Writing in a file.
		try
		{
			// Although its opening and closing again and again but it always overwrite prev content and don't append.
			for(int i=0;i<4;i++)
			{
				/*
					We can directly use FileWriter writer = new FileWriter("file.txt"); but using buffer increases efficiency.
					FileWriter is a connection stream while BufferedWriter is a chain stream that works with characters.
					Also instead of passing directly the file name to FileWriter(), We can pass a File object.
				*/

				BufferedWriter writer = new BufferedWriter(new FileWriter("write_in_me.txt"));
				writer.write("Hello this is a experimental file.How are you all doing?\nNow this is second line.This is some second dummy sentence.I just can't think of writing something good now"+i);
				writer.close();
			}
	
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}


		System.out.println("\nReading from file now\n");

		// Reading in a file
		try{

			// Here we have used file object instead of directly passing name of file to FileReader & chained it with BufferedReader.
			BufferedReader reader = new BufferedReader(new FileReader(new File("write_in_me.txt")));

			String line = null;

			System.out.println("Content inside file is :");
			while((line = reader.readLine()) != null)
			{
				System.out.println(line);
			}
			reader.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}


		try{

			// Here we have used file object instead of directly passing name of file to FileReader & chained it with BufferedReader.
			BufferedReader reader = new BufferedReader(new FileReader(new File("write_in_me.txt")));

			String line = null;

			System.out.println("\nSplited String is :");
			while((line = reader.readLine()) != null)
			{
				String[] arr = line.split("\\.");

				for(String str : arr)
					System.out.println(str);
				System.out.println("\n");
			}
			reader.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}