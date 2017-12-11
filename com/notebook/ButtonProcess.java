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
		
	}
	
	
	
	

	
}


