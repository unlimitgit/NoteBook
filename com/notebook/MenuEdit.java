/*
This is for menu creation and processing
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.FileStringProcess;
import com.notebook.TextProcess;


import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.awt.Color;
import java.io.File;



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
		menuItemSaveAs.setForeground(Color.GRAY);
        // menuItemSaveAs.addActionListener(new ActionListener() {
            // public void actionPerformed(ActionEvent evt) {  
               // menuItemSaveAsActionPerformed(evt);  
            // }  
        // });  
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
		
		JMenuItem menuItemHelp = new JMenuItem("Help");
        menuItemHelp.addActionListener(new ActionListener() {  
            public void  actionPerformed(ActionEvent evt) {  
               menuItemHelpActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemHelp);
		
		
		//Build edit menu
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		menuEdit.setForeground(Color.GRAY);
		
		//Build help menu
		JMenu menuHelp = new JMenu("Help");
		menuHelp.addActionListener(new ActionListener() {  
            public void  actionPerformed(ActionEvent evt) {  
               menuHelpActionPerformed(evt);  
            }  
        });  
		menuBar.add(menuHelp);
		
		//Build help menu
		JMenu menuManual = new JMenu("Manual");
		menuHelp.addActionListener(new ActionListener() {  
            public void  actionPerformed(ActionEvent evt) {  
               menuManualActionPerformed(evt);  
            }  
        });  
		menuBar.add(menuManual);

        		
		return menuBar;
	}
	
	// Generate new notebook file. 
	private static void menuItemNewActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Create a new notebook", FileDialog.SAVE); 
		fd.setFile("*.jntk");  // Add file filter		
        fd.setVisible(true); 
		String strFile = fd.getDirectory() + fd.getFile(); 
		File file = new File(strFile);
		boolean exists = file.exists();
		// File not exist, need create an new notebook file
		if (!exists) {
			String fileContents = "PAGE::::Home" + GlobalVariables.newline;
			BufferedWriter bWriter = null;
			try {  
				bWriter = new BufferedWriter(new FileWriter(file));  
				bWriter.write(fileContents); 
				bWriter.flush();	
				bWriter.close();   
			} catch (Exception e) {  
			  
			} 
			loadNewNoteBookFile(fd);
		}
		
        
    } 
	
	// Load new notebook file. 
	// By default, when loading notebook file, it will goes to display mode (the other one is edit).
	// The search window is non visible.
	private static void menuItemLoadActionPerformed(ActionEvent evt) {  
		
		// Open files		
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Open", FileDialog.LOAD);
		fd.setFile("*.jntk");  // Add file filter
        fd.setVisible(true); 
		
			
		
		// Save loaded file information
		if (fd.getFile() != null && !fd.getFile().isEmpty()) {
			loadNewNoteBookFile(fd);
		}
    } 
	
	private static void menuItemSaveAsActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:
		FileDialog fd = new FileDialog(GlobalVariables.frame, "Save As", FileDialog.SAVE); 
		fd.setFile("*.jntk");  // Add file filter		
        fd.setVisible(true); 
		String string1 = GlobalVariables.pageTitle + fd.getFile() 
		          + GlobalVariables.newline + GlobalVariables.textPane.getText();  
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
	
	private static void menuItemHelpActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
        	String helpFile =  "help.chm";
		File file = new File(helpFile); 
		// try to rename the file with the same name 
		File sameFileName = new File(helpFile); 
                
        try
        {
			if(file.renameTo(sameFileName)){ 
			// if the file is renamed 
				Runtime.getRuntime().exec("hh.exe " + helpFile);
			}
        } catch (Exception e){
        
        }
    } 
	
	
	
	private static void menuHelpActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here: 
		GlobalVariables.buttonSearch.setText("Test search result");
		String helpFile =  "help.chm";
		File file = new File(helpFile); 
		// try to rename the file with the same name 
		File sameFileName = new File(helpFile); 
                
        try
        {
			if(file.renameTo(sameFileName)){ 
			// if the file is renamed 
				Runtime.getRuntime().exec("hh.exe " + helpFile);
			}
        } catch (Exception e){
        
        }
    } 
	
	private static void menuManualActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here: 
		GlobalVariables.buttonSearch.setText("Test search result");
		String helpFile =  "help.chm";
		File file = new File(helpFile); 
		// try to rename the file with the same name 
		File sameFileName = new File(helpFile); 
                
        try
        {
			if(file.renameTo(sameFileName)){ 
			// if the file is renamed 
				Runtime.getRuntime().exec("hh.exe " + helpFile);
			}
        } catch (Exception e){
        
        }
    }
	
	public static boolean loadNewNoteBookFile(FileDialog fd ){
		// Goes to default status
			GlobalVariables.searchVisible = false;
			GlobalVariables.buttonSearch.setText("Display search result");
			GlobalVariables.frame.getContentPane().remove(GlobalVariables.searchScrollPane);
			GlobalVariables.frame.validate();
			// Clear file search result
			GlobalVariables.pageSequences.clear();
			String strFile = fd.getDirectory() + fd.getFile(); 
			GlobalVariables.pageSequences.add("Home");
			GlobalVariables.pageSeqDepth = 0;   // Home page
			GlobalVariables.pageNumber = 0;	// Home page
			GlobalVariables.fileName = strFile;
			GlobalVariables.dirName = fd.getDirectory();	
			GlobalVariables.frameDisplay = "Notebook with Java: " +  strFile + "--PAGE::";
			GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + "Home");	
			
			String contents = FileStringProcess.readFileContents(strFile); // Read out the contents in the loaded file
			GlobalVariables.pageList = FileStringProcess.extractPageList(contents);
			GlobalVariables.pageListLowerCase = FileStringProcess.convertArrayStringLowerCase(GlobalVariables.pageList);
			GlobalVariables.pageContents = FileStringProcess.extractPageContents(contents);
			
			
			String dispContents = GlobalVariables.pageContents.get(0);
			// TextProcess.setDisplayMode();
			// GlobalVariables.textPane.setText("");
			TextProcess.textPaneTitleDisplay(GlobalVariables.pageSymbol);
			TextProcess.textPaneDisplay(dispContents);
			
			// Try to let the scroll go to the top, failed
			try {
				GlobalVariables.textDoc.insertString(0, "", GlobalVariables.style);	
			} catch (Exception e) {  
			}
			GlobalVariables.textScrollPane.validate();
			GlobalVariables.textScrollPane.repaint(); 	//Refresh frame
			//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
			GlobalVariables.textPane.setCaretPosition(1);
			return true;
	}
}


