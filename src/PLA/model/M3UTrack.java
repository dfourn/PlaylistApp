/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PLA.model;

import java.io.BufferedWriter;
import java.io.IOException;


public class M3UTrack {
    private String path;
    private int secondsDuration;
    private String label;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSecondsDuration() {
        return secondsDuration;
    }

    public void setSecondsDuration(int secondsDuration) {
        this.secondsDuration = secondsDuration;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public M3UTrack()
    {
        path = null;
        label = null;
        secondsDuration = 0;
       
    }
    
    
    public boolean init(String pathString, String metaDataString)
    {
        path = new String(pathString);
        secondsDuration = -1;
        
        if(metaDataString!=null && metaDataString.startsWith(""))
        {
            int i1= metaDataString.indexOf(":");
            int i2 = metaDataString.indexOf(",");
            String substr = metaDataString.substring(i1+1, i2);
            int seconds = Integer.parseInt(substr);  
            if(seconds>0)
                secondsDuration = seconds;
            label = metaDataString.substring(i2+1, metaDataString.length());
        }
        return true;
    }
    public String toString()
    {
        if(path==null)
            return new String("track not initialised");
        
        String out = "Track path: \"" + path;
        if (label!=null)
        {
            out += "\", label: " + label;
        }
        if(secondsDuration>0)
        {
            out += ", duration: " + secondsDuration + "\n";
        }
        return out;
        
    }
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write("#EXTINF:" + this.secondsDuration + ","+this.label + "\n");
        bw.write(this.path + "\n");
    }
    
}
