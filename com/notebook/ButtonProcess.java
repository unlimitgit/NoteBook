/*
This is for buttons in the button panel. It mainly includes the button processing functions.
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.EditDisplay;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.text.BadLocationException;

public class ButtonProcess{
		
	
	// Enable/disable search result panel, controlled by buttonSearch
	public static void buttonSearchPress(ActionEvent evt) {
		if (GlobalVariables.searchVisible){
			GlobalVariables.searchVisible = false;
			GlobalVariables.buttonSearch.setText("Display search result");
			GlobalVariables.frame.getContentPane().remove(GlobalVariables.searchScrollPane); 
		} else {
			GlobalVariables.searchVisible = true;
			GlobalVariables.buttonSearch.setText("Remove search result");
			GlobalVariables.frame.getContentPane().add(GlobalVariables.searchScrollPane, BorderLayout.LINE_END); 					
		}
		
		GlobalVariables.frame.validate();				
	
	}
	
	
	// Edit/save functional switch
	public static void buttonSaveEditPress(ActionEvent evt) {
		if (GlobalVariables.textEditable){
			try {
				String content = GlobalVariables.textPane.getDocument().getText(0, GlobalVariables.textPane.getDocument().getLength());
				EditDisplay.saveTextPaneProc(GlobalVariables.fileName, content);
				EditDisplay.loadFileDisplayProc(GlobalVariables.fileName);
			} catch (BadLocationException ee) {
				//handle exception
			}	
			
			
		} else {
			GlobalVariables.textEditable = true;
			GlobalVariables.buttonSaveEdit.setText("Save");
			GlobalVariables.textPane.setEditable(true);	
			GlobalVariables.textPane.setBackground(Color.WHITE);
			String content = EditDisplay.extractFileProc(GlobalVariables.fileName);	
			if (content != "") {
				try {  
					GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
					GlobalVariables.textDoc.insertString(0, content, GlobalVariables.style);						
					     
				} catch (Exception e) {  
					
				}  
			}
			
		}			
	
	}
	
	
	
	

	
}


