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
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.awt.Color;

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
		
		
		//Save as file
        JMenuItem menuItemSaveAs = new JMenuItem("Save as Notebook");
        menuItemSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {  
               menuItemSaveAsActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemSaveAs);
		
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
	
	private static void menuItemNewActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here: 
		
		// Make textPane editable		
		GlobalVariables.textEditable = true;	
		GlobalVariables.textPane.setEditable(true);	
		GlobalVariables.textPane.setBackground(Color.WHITE);
		GlobalVariables.fileName = null;	
		GlobalVariables.buttonSaveEdit.setText("Save");		
        
    } 
	
	private static void menuItemLoadActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here: 
	
		
		// Open files		
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Open", FileDialog.LOAD);
		fd.setFile("*.jntk");  // Add file filter
        fd.setVisible(true); 
		GlobalVariables.searchVisible = false;
		GlobalVariables.buttonSearch.setText("Display search result");
		GlobalVariables.frame.getContentPane().remove(GlobalVariables.searchScrollPane);
		GlobalVariables.frame.validate();
		GlobalVariables.fileSequences.clear();	
        String strFile = fd.getDirectory() + fd.getFile(); 
		GlobalVariables.fileSequences.add(strFile);
		GlobalVariables.fileLevel = 1;
		GlobalVariables.fileName = strFile;
		GlobalVariables.dirName = fd.getDirectory();		
		GlobalVariables.frame.setTitle("Notebook with Java: " +  strFile);	
		EditDisplay.loadFileDisplayProc(strFile);
		
        
    } 
	
	private static void menuItemSaveAsActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Save As", FileDialog.SAVE); 
		fd.setFile("*.jntk");  // Add file filter		
        fd.setVisible(true); 
		String string1 = "file::" + fd.getFile() 
		          + "\n" + GlobalVariables.textPane.getText();  
		String stringfile = fd.getDirectory()+fd.getFile();  
		BufferedWriter bWriter = null;  
		try {  
			bWriter = new BufferedWriter(new FileWriter(stringfile));  
			bWriter.write(string1);   
			bWriter.close();   
		} catch (Exception e) {  
			// TODO Auto-generated catch block  
			System.out.println("Save failed");  
		}   
        
    } 
	
	private static void menuItemExitActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
       System.exit(0);   // Exit the whole system.
    } 
	
	
}


