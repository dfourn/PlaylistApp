/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PLA.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class M3UReader {

    /**
     * @param args the command line arguments
     */
    public static M3UPlayListTableModel getPlayList(File file)
    {
        // not implemented yet
         M3UPlayListTableModel outputPlayList = new M3UPlayListTableModel();
         return outputPlayList;
      
    }
 
    
    
    public static M3UPlayListTableModel getPlayList(String filename)
    {
         List<M3UTrack> trackList = getTrackList(filename);
         M3UPlayListTableModel outputPlayList = new M3UPlayListTableModel(trackList);
         return outputPlayList;
      
    }
    
    public static void printTrackObjects(String playList)
    {
        
        List<M3UTrack> trackList = getTrackList(playList);

         
        ListIterator<M3UTrack> my_iterator = trackList.listIterator();
        
        while (my_iterator.hasNext())
        {
            M3UTrack trackInList = my_iterator.next();
            
            System.out.println(trackInList);
        }
         
    }
       static List<M3UTrack> getTrackList(File f)
    {
        
        List<M3UTrack> outputList = new ArrayList<M3UTrack>();
        
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String headerString = br.readLine();
            if(headerString.startsWith("#EXTM3U")==false)
                return null;
            String s1, s2;
            while((s1 = br.readLine())!=null)
            {
                M3UTrack trackInList = new M3UTrack();
                if(s1.startsWith("#")==true) 
                {
                    if(s1.startsWith("#EXTINF")==false) 
                    {
                        continue;
                    }
                    // next line should be a track path
                    s2 = br.readLine();
                    if(s2!=null)
                    {
                        trackInList.init(s2, s1);
                        outputList.add(trackInList);
                    }
                }
                else // we have a track path with no meta-data in line before
                {
                    trackInList.init(s1, null);
                    outputList.add(trackInList);
                }
//                System.out.println(trackInList);
            }
            br.close();
        }
        catch (Exception e)
        {
            System.err.println("could not open/read line from/close filename "+ f.getName());
        }
        return outputList;
        
    }

    
    static List<M3UTrack> getTrackList(String playList)
    {
        
        List<M3UTrack> outputList = new ArrayList<M3UTrack>();
        
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(playList)));
            String headerString = br.readLine();
            if(headerString.startsWith("#EXTM3U")==false)
                return null;
            String s1, s2;
            while((s1 = br.readLine())!=null)
            {
                M3UTrack trackInList = new M3UTrack();
                if(s1.startsWith("#")==true) 
                {
                    if(s1.startsWith("#EXTINF")==false) 
                    {
                        continue;
                    }
                    // next line should be a track path
                    s2 = br.readLine();
                    if(s2!=null)
                    {
                        trackInList.init(s2, s1);
                        outputList.add(trackInList);
                    }
                }
                else // we have a track path with no meta-data in line before
                {
                    trackInList.init(s1, null);
                    outputList.add(trackInList);
                }
//                System.out.println(trackInList);
            }
            br.close();
        }
        catch (Exception e)
        {
            System.err.println("could not open/read line from/close filename "+ playList);
        }
        return outputList;
        
    }
            
            
     
  
    public static boolean isValidHeader(String playList)
    {
        boolean returnValue = false;
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(new File(playList)));
            String s = br.readLine();
            String lowercase_s = s.toLowerCase();
            if(s.startsWith("#EXTM3U")) {
                returnValue = true;
            }
            br.close();
        }
        catch (Exception e)
        {
            System.err.println("isValidHeader:: error with file "+ playList + ": " + e.getMessage());
        }
        
        return returnValue;
    }
    public static int getNumberOfTracks(String playList)
    {
        int numberOfTracks = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(playList)));
            String s;
            while((s = br.readLine())!=null)
            {
                if(s.startsWith("#")==false) {
                    numberOfTracks++;
                }
            }
            br.close();
        }
        catch (Exception e)
        {
            numberOfTracks = -1;
            System.err.println("could not open/read line from/close filename "+ playList);
        }
        return numberOfTracks;
        
    }

    public static int getTotalMinutes(String playList)
    {
        int numberOfSeconds= 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(playList)));
            String s = br.readLine();
            if(s.startsWith("#EXTM3U")==false)
                return -1;
            
            while((s = br.readLine())!=null)
            {
                if(s.startsWith("#")==true) {
                    int i1= s.indexOf(":");
                    int i2 = s.indexOf(",");
                    String substr = s.substring(i1+1, i2);
                    int seconds = Integer.parseInt(substr);  
                    if(seconds>0)
                        numberOfSeconds += seconds;                        
                }
            }
            br.close();
        }
        catch (Exception e)
        {
            numberOfSeconds = -1;
            System.err.println("could not open/read line from/close filename "+ playList);
        }
        return (numberOfSeconds)/60;
        
    }
    
 
}
