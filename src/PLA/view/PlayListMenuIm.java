/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PLA.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import PLA.model.M3UPlayListTableModel;
import PLA.model.SleepyRunnable;
import javax.swing.event.MenuEvent;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PlayListMenuIm {

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu fileMenu;

        JMenuItem saveMenuItem, openMenuItem, deleteMenuItem,
                singleThreadMenuItem, multiThreadMenuItem;

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        //fileMenu.addMenuListener(this);


        saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 savePlayList();
            }
        });
        //...same for openMenuItem
        openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPlayList();
            }
        });

        deleteMenuItem = new JMenuItem("Delete", KeyEvent.VK_O);
        fileMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = theView.getSelectedRows();
                if (selectedRows != null) {
                    thePlayList.deleteTrack(selectedRows);
                    theView.repaint();
                }
            }
        });



        return menuBar;
    }
    private M3UPlayListTableModel thePlayList;
    final JFileChooser fc;
    //JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filterm = new FileNameExtensionFilter(
            "M3U", "m3u");
    FileNameExtensionFilter filterj = new FileNameExtensionFilter("Json", "json"); //this could be put straight into fc in the constructor
    //JComponent theView;
    DemoPanel theView;

    public PlayListMenuIm(M3UPlayListTableModel aPlayList, DemoPanel aView) {
        thePlayList = aPlayList;
        theView = aView;
        fc = new JFileChooser();
        fc.addChoosableFileFilter(filterm);
        fc.addChoosableFileFilter(filterj);


    }

    private void savePlayList() {
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            String file = selectedFile.toString();
            if (!(file.toLowerCase().endsWith(".json") || file.toLowerCase().endsWith(".m3u"))) {
                if (fc.getFileFilter()==filterj) {
                    file += ".json";
                }
                else { //if nothing selected, it will just save as m3u, it's not a bug, it's a feature!
                    file += ".m3u";
                }
                selectedFile = new File(file);
            }
            thePlayList.save(selectedFile);
        }
    }

    private void openPlayList() {
        int returnVal = fc.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fc.getSelectedFile();
            if (thePlayList.replaceWith(selectedFile) == false) {
                JOptionPane.showMessageDialog(null, "Oops, I couldn't load a playlist from "
                        + selectedFile.getName());
            }
            theView.repaint();

        }
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String s = source.getActionCommand();

    }

    public void itemStateChanged(ItemEvent e) {
    }

    public void doSingleThread() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            String str = "Sleeping for iteration " + i + "\n";
            //theView.appendOutput(str);
            System.out.print(str);

        }
    }

    public void doMultiThread() {
        SleepyRunnable mySleepyRunnable = new SleepyRunnable(theView);

        Thread mySleepyThread = new Thread(mySleepyRunnable);

        mySleepyThread.start();

    }

}
