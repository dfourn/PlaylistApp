/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package PLA.view;

/*
 * TableSelectionDemo.java requires no other files.
 */

import PLA.model.M3UReader;
import PLA.model.M3UPlayListTableModel;
import PLA.model.TableRowTransferHandler;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class DemoPanel extends JPanel 
                                implements ActionListener 
{ 
    
    private M3UPlayListTableModel playList;
    private JTable table;
    //private JCheckBox rowCheck;
    //private JCheckBox columnCheck;
    //private JCheckBox cellCheck;
    //private ButtonGroup buttonGroup;
    //private JTextArea output;

    //private PlayListMenu theMenu;
    private PlayListMenuIm theMenu;
    
    private int selectedRow;
    
    public DemoPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        playList = M3UReader.getPlayList("test-data/classicalPlaylist.m3u");
        
        //theMenu = new PlayListMenu(playList, this);
        theMenu = new PlayListMenuIm(playList, this);
        
        
        table = new JTable(playList);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        //table.getSelectionModel().addListSelectionListener(new RowListener());
        //table.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());        


        add(new JScrollPane(table));

//        add(new JLabel("Selection Mode"));
//        buttonGroup = new ButtonGroup();
//        addRadio("Multiple Interval Selection").setSelected(true);
//        addRadio("Single Selection");
//        addRadio("Single Interval Selection");
//
//        add(new JLabel("Selection Options"));
//        rowCheck = addCheckBox("Row Selection");
//        rowCheck.setSelected(true);
//        columnCheck = addCheckBox("Column Selection");
//        cellCheck = addCheckBox("Cell Selection");
//        cellCheck.setEnabled(false);


        //output = new JTextArea(5, 40);
        //output.setEditable(false);
        
        //add(new JScrollPane(output));
        selectedRow = -1;
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setTransferHandler(new TableRowTransferHandler(table));
        
    }

//    private JCheckBox addCheckBox(String text) {
//        JCheckBox checkBox = new JCheckBox(text);
//        checkBox.addActionListener(this);
//        add(checkBox);
//        return checkBox;
//    }
//
//    private JRadioButton addRadio(String text) {
//        JRadioButton b = new JRadioButton(text);
//        b.addActionListener(this);
//        buttonGroup.add(b);
//        add(b);
//        return b;
//    }

    public void actionPerformed(ActionEvent event) {
//        String command = event.getActionCommand();
//        //Cell selection is disabled in Multiple Interval Selection
//        //mode. The enabled state of cellCheck is a convenient flag
//        //for this status.
//        if ("Row Selection" == command) {
//            table.setRowSelectionAllowed(rowCheck.isSelected());
//            //In MIS mode, column selection allowed must be the
//            //opposite of row selection allowed.
//            if (!cellCheck.isEnabled()) {
//                table.setColumnSelectionAllowed(!rowCheck.isSelected());
//            }
//        } else if ("Column Selection" == command) {
//            table.setColumnSelectionAllowed(columnCheck.isSelected());
//            //In MIS mode, row selection allowed must be the
//            //opposite of column selection allowed.
//            if (!cellCheck.isEnabled()) {
//                table.setRowSelectionAllowed(!columnCheck.isSelected());
//            }
//        } else if ("Cell Selection" == command) {
//            table.setCellSelectionEnabled(cellCheck.isSelected());
//        } else if ("Multiple Interval Selection" == command) { 
//            table.setSelectionMode(
//                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//            //If cell selection is on, turn it off.
//            if (cellCheck.isSelected()) {
//                cellCheck.setSelected(false);
//                table.setCellSelectionEnabled(false);
//            }
//            //And don't let it be turned back on.
//            cellCheck.setEnabled(false);
//        } else if ("Single Interval Selection" == command) {
//            table.setSelectionMode(
//                    ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//            //Cell selection is ok in this mode.
//            cellCheck.setEnabled(true);
//        } else if ("Single Selection" == command) {
//            table.setSelectionMode(
//                    ListSelectionModel.SINGLE_SELECTION);
//            //Cell selection is ok in this mode.
//            cellCheck.setEnabled(true);
//        }
//
//        //Update checkboxes to reflect selection mode side effects.
//        rowCheck.setSelected(table.getRowSelectionAllowed());
//        columnCheck.setSelected(table.getColumnSelectionAllowed());
//        if (cellCheck.isEnabled()) {
//            cellCheck.setSelected(table.getCellSelectionEnabled());
//        }
    }
    public int[] getSelectedRows()
    {
        int[] out = table.getSelectedRows();
        return out;
    }
//    public synchronized void appendOutput(String str)
//    {
//        output.append(str);
//        repaint();
//    }
//    private synchronized void outputSelection() {
//        output.append(String.format("Lead: %d, %d. ",
//                    table.getSelectionModel().getLeadSelectionIndex(),
//                    table.getColumnModel().getSelectionModel().
//                        getLeadSelectionIndex()));
//        output.append("Rows:");
//        for (int c : table.getSelectedRows()) {
//            output.append(String.format(" %d", c));
//        }
//        output.append(". Columns:");
//        for (int c : table.getSelectedColumns()) {
//            output.append(String.format(" %d", c));
//        }
//        output.append(".\n");
//    }

//    private class RowListener implements ListSelectionListener {
//        public void valueChanged(ListSelectionEvent event) {
//            if (event.getValueIsAdjusting()) {
//                return;
//            }
//            output.append("ROW SELECTION EVENT. ");
//            outputSelection();
//        }
//    }

//    private class ColumnListener implements ListSelectionListener {
//        public void valueChanged(ListSelectionEvent event) {
//            if (event.getValueIsAdjusting()) {
//                return;
//            }
//            output.append("COLUMN SELECTION EVENT. ");
//            outputSelection();
//        }
//    }

 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Disable boldface controls.
        UIManager.put("swing.boldMetal", Boolean.FALSE); 

        //Create and set up the window.
        JFrame frame = new JFrame("TableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DemoPanel newContentPane = new DemoPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        frame.setJMenuBar(newContentPane.theMenu.createMenuBar());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
