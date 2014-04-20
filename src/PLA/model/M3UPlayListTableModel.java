/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PLA.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class M3UPlayListTableModel extends AbstractTableModel implements Reorderable {

    @JsonProperty
    private List<M3UTrack> theListOfTracks;
    @JsonIgnore
    private String[] columnNames = {"path",
        "duration",
        "track and artist"};

    @JsonProperty
    public List<M3UTrack> getTheListOfTracks() {
        return theListOfTracks;
    }

    @JsonProperty
    public void setTheListOfTracks(List<M3UTrack> theListOfTracks) {
        this.theListOfTracks = theListOfTracks;
    }

    public void deleteTrack(int[] theseIndices) {
        for (int i = 0; i < theseIndices.length; i++) {
            if (theseIndices[i] < theListOfTracks.size()) {
                theListOfTracks.remove(theseIndices[i]);
            }
        }

    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return theListOfTracks.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {

        M3UTrack item = theListOfTracks.get(row);
        if (col == 0) {
            return item.getPath();
        } else if (col == 1) {
            return item.getSecondsDuration();
        } else if (col == 2) {
            return item.getLabel();
        } else {
            return null;
        }
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return true;
    }


    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */

    public void setValueAt(Object value, int row, int col) {
        //System.out.println(value.toString() + " " + row + " " + " " + col);
        M3UTrack item = theListOfTracks.get(row);
        if (col == 0) {
            item.setPath(value.toString());
        } else if (col == 1) {
            item.setSecondsDuration(Integer.parseInt(value.toString()));
        } else if (col == 2) {
            item.setLabel(value.toString());
        }
        theListOfTracks.set(row, item);
    }

    public void reorder(int fromIndex, int toIndex) { //Drag&Drop

        if (fromIndex<toIndex) toIndex--; //decrease value by 1 since it removes one record which changes the index
        Collections.swap(theListOfTracks, toIndex, fromIndex);
    }

    public boolean saveAsJSON(File f) {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        try {
            mapper.writeValue(f, this);
        } catch (IOException e) {
            System.out.println("couldn't save JSON file " + f + " e: " + e);
            return false;
        }
        return true;

    }

    public boolean save(File f) {
        String filename = f.toString().toLowerCase();
        if (filename.endsWith("json")) {
            return saveAsJSON(f);
        }
        if (filename.endsWith("m3u") == false) {
            return false;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("#EXTM3U\n");
            for (M3UTrack item : theListOfTracks) {
                item.save(bw);
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("couldn't write ");
            return false;
        }
        return true;
    }

    public boolean replaceWith(File f) {
        String filename = f.toString().toLowerCase();

        if (filename.endsWith("m3u")) {
            List<M3UTrack> replacementListOfTracks =
                    M3UReader.getTrackList(f);

            if (replacementListOfTracks == null) {
                return false;
            }
            theListOfTracks = replacementListOfTracks;
            return true;
        } else if (filename.endsWith("json")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                M3UPlayListTableModel playlist =
                        (M3UPlayListTableModel) mapper.readValue(f,
                        M3UPlayListTableModel.class);
                theListOfTracks = playlist.getTheListOfTracks();

            } catch (IOException e) {
                System.out.println("could not make a JSON file out of "
                        + filename + ". Sorry.");
                return false;
            }

        }
        return true;
    }

    M3UPlayListTableModel() {
        theListOfTracks = new ArrayList<M3UTrack>();
    }

    M3UPlayListTableModel(List<M3UTrack> anotherList) {
        theListOfTracks = anotherList;
    }

    void add(M3UTrack m) {
        theListOfTracks.add(m);
    }

    @Override
    public String toString() {
        String out = "";

        for (M3UTrack item : theListOfTracks) {
            out = out + item.toString();
        }
        return out;
    }

    public void copyHalfByHalf(M3UPlayListTableModel first_half, M3UPlayListTableModel second_half) {
        List h1 = theListOfTracks.subList(0, (getNumTracks() + 1) / 2);
        first_half.theListOfTracks.addAll(h1);

        int s = getNumTracks() / 2 + getNumTracks() % 2;
        int e = getNumTracks();
        System.out.println("s is " + s + " and  e is " + e);
        List h2 = theListOfTracks.subList(s, e);
        second_half.theListOfTracks.addAll(h2);
    }

    public M3UPlayListTableModel(M3UPlayListTableModel list1, M3UPlayListTableModel list2) {
        theListOfTracks = new ArrayList<M3UTrack>();
        Iterator<M3UTrack> it1 = list1.theListOfTracks.iterator();
        Iterator<M3UTrack> it2 = list2.theListOfTracks.iterator();
        while (true) {
            if (it1.hasNext()) {
                theListOfTracks.add(it1.next());
            }
            if (it2.hasNext()) {
                theListOfTracks.add(it2.next());
            }

            if (it1.hasNext() == false && it2.hasNext() == false) {
                break;
            }

        }

    }



    public int getNumTracks() {
        return theListOfTracks.size();
    }
}
