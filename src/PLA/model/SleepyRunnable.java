/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PLA.model;

import PLA.view.DemoPanel;

/**
 *
 * 
 */
public class SleepyRunnable implements Runnable
{
    
    DemoPanel myReferenceToPanel;
    
    public SleepyRunnable(DemoPanel referenceFromElsewhere)
    {
        myReferenceToPanel = referenceFromElsewhere;
        
    }
    public void run()
    {
        for(int i = 0;i<10;i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                
            }
            String str = "Sleeping for iteration " + i + "\n";
            //myReferenceToPanel.appendOutput(str);
            System.out.print(str);

            
        }
    }

    
}
