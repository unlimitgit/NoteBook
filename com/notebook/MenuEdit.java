/*
This is for menu creation and processing
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.EditDisplay;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;

public class MenuEdit{
	
	public static JMenuBar CreateMenuBar() {
		// Create Menu
		JMenuBar menuBar = new JMenuBar();
		
		//Build file menu
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		//New file
        JMenuItem menuItemNew = new JMenuItem("New Notebook");
        menuItemNew.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent evt) {  
                menuItemNewActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemNew);
		
		//Load file
        JMenuItem menuItemLoad = new JMenuItem("Load Notebook");
        menuItemLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {  
               menuItemLoadActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemLoad);
		
		menuFile.addSeparator();
		
		//Exit system
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {  
            public void  actionPerformed(ActionEvent evt) {  
               menuItemExitActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemExit);
		
		
		//Build edit menu
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

        		
		return menuBar;
	}
	
	private static void menuItemLoadActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here: 
		// Initial parameters
		GlobalVariables.searchVisible = false; 
		GlobalVariables.textEditable = false;
		GlobalVariables.buttonSaveEdit.setText("Edit");
				
		// Open files		
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Open", FileDialog.LOAD);  
        fd.setVisible(true);  
        String strFile = fd.getDirectory() + fd.getFile(); 		
		String line = null;
		EditDisplay.ProcResult result = new EditDisplay.ProcResult();
        if (strFile != null) {  
            try {  
                GlobalVariables.textPane.setText("");
				FileReader fileReader = new FileReader(strFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
						// System.out.println(line.length());
						// style = textDoc.getStyle("highlight");
						// textDoc.insertString(textPane.getDocument().getLength(), line+"\n", style);
						if (line.length() > 1){
							result = EditDisplay.procString(line);
							//System.out.println(result.dispStr + "  " + result.returnCode);
						} else {
							result.dispStr = "";
							result.returnCode = 3;
						}
						
						if ( result.returnCode == 2){
							GlobalVariables.style = GlobalVariables.textDoc.getStyle("bold");
						} else {
							GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
						}
						
						GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
								
					}   
				bufferedReader.close();      
            } catch (Exception e) {  
               
            }  
        }  
        
    } 
	
	private static void menuItemNewActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
        
    } 
	
	private static void menuItemExitActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
       System.exit(0);   // Exit the whole system.
    } 
	
	
}


