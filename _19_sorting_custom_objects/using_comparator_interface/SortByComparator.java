import java.util.*;
import java.lang.*;
import java.io.*;

class Song
{
	String title,artist;

	Song(String t, String a)
	{
		title = t;
		artist = a;
	}

    public String getTitle()
    {
        return title;
    }
    
    public String getArtist()
    {
        return artist;
    }

    public String toString()
    {
    	return (title+" : "+artist+"\n");
    }
}


class SortByTitle implements Comparator <Song>{
	public int compare(Song first, Song second)
	{
		return first.getTitle().compareTo(second.getTitle());
	}
}

class SortByArtist implements Comparator <Song>{
	public int compare(Song first, Song second)
	{
		return first.getArtist().compareTo(second.getArtist());
	}
}


public class SortByComparator{

	public static void main(String[] args)
	{
		ArrayList<Song> songs = new ArrayList<Song> ();
        songs.add(new Song("Finally Found you","Enrique Iglesias"));
        songs.add(new Song("Symphony","Zara Larsson"));
        songs.add(new Song("Solo","Demi Leveto"));
        songs.add(new Song("Meteorite","Years & years"));
        songs.add(new Song("Legends never Die","League of legends"));
        songs.add(new Song("Love yourself","Justin Beiber"));

        SortByTitle st = new SortByTitle();
        SortByArtist sa = new SortByArtist();

        System.out.println("Sorted by song title :");
        Collections.sort(songs,st);
        for(Song x : songs)
        {
        	System.out.println(x.getTitle());
        }
        

        System.out.println("\nSorted by song artist :");
        Collections.sort(songs,sa);
        for(Song x : songs)
        {
        	System.out.println(x.getArtist());
        }

        System.out.println("\n\n"+songs);
	}
}