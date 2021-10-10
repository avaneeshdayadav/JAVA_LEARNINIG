import java.util.*;
import java.lang.*;
import java.io.*;

class Song implements Comparable<Song>{
    String title,artist;
    
    Song(String t, String a)
    {
        title = t;
        artist = a;
    }
    
    // Every Object has a toString() method and it can be overriden to customize the output while printing using System.out.println()
    public String toString()
    {
        return title;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getArtist()
    {
        return artist;
    }
    
    // This is the method which needs to be overriden as class is implementing Comparable interface.
    public int compareTo(Song s)
    {
        return title.compareTo(s.getTitle()); // This compareTo() is call to the String object's compareTo() method and not to this.compareTo() method.
    }
    
    
}
public class SortByComparable{
    public static void main(String[] args)
    {
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song("Finally Found you","Enrique Iglesias"));
        songs.add(new Song("Symphony","Zara Larsson"));
        songs.add(new Song("Solo","Demi Leveto"));
        songs.add(new Song("Meteorite","Years & years"));
        songs.add(new Song("Legends never Die","League of legends"));
        songs.add(new Song("Love yourself","Justin Beiber"));

        Collections.sort(songs);
        for(Song s : songs)
        {
            System.out.println(s);
        }
    }
    
    
}