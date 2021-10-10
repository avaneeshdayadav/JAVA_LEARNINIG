import java.util.*;
import java.lang.*;
import java.io.*;


class Song{

	String title, artist;

	Song(String t, String a)
	{
		title = t;
		artist = a;
	}

	public String toString()
	{
		return (title+"\n");
	}

	public String getTitle()
	{
		return title;
	}

	public String getArtist()
	{
		return artist;
	}
}

class SortByTitle implements Comparator<Song>{

	public int compare(Song s1, Song s2)
	{
		return s1.getTitle().compareTo(s2.getTitle());
	}
}

class SortByArtist implements Comparator<Song>{

	public int compare(Song s1, Song s2)
	{
		return s1.getArtist().compareTo(s2.getArtist());
	}
}

public class RemoveDupli{

	public static void main(String[] args)
	{
		ArrayList<Song> songList = new ArrayList<Song> ();

		// Although these two songs are repeated twice in arraylist but still not repeated in hashSet as they both point to smae object.
		Song specialSong = new Song("Copines","Unknown");
		Song specialSong2 = specialSong;

		// Repititions of songs below in arrayList still repeated in HashSet too and duplicates not removed.
		songList.add(new Song("Finally Found you","Enrique Iglesias"));
		songList.add(new Song("Finally Found you","Enrique Iglesias"));
        songList.add(new Song("Symphony","Zara Larsson"));
        songList.add(new Song("Solo","Demi Leveto"));
        songList.add(new Song("Solo","Demi Leveto"));
        songList.add(new Song("Solo","Demi Leveto"));        
        songList.add(new Song("Meteorite","Years & years"));
        songList.add(new Song("Legends never Die","League of legends"));
        songList.add(new Song("Love yourself","Justin Beiber"));
        songList.add(specialSong);
        songList.add(specialSong2);

        Collections.sort(songList,new SortByTitle());

        for(Song x : songList)
        	System.out.println(x.getTitle());

        HashSet<Song> songSet = new HashSet<Song>();

        songSet.addAll(songList);

        // Also the sorted order of arrayList lost after adding that in HashSet We need to sort hashSet too.
        System.out.println("\n"+songSet);

	}
}