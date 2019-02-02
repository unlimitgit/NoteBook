/*
This is for buttons in the button panel. It mainly includes the button processing functions.
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.TextProcess;
import com.notebook.FileStringProcess;

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
		if (GlobalVariables.textEditable) { // Originally in edit mode. Need save the contents and change to display mode.
			String[] lines = GlobalVariables.textPane.getText().split("\\n"); // Extract the new page contents
			String lineContent, lineContentPre;
			String pageContents = "";
			int imIndex = -1;
			//System.out.println("file length:" + lines.length);
			for(int i = 0 ; i< lines.length; i++) {
				lineContent = lines[i].trim();
				imIndex = lineContent.toLowerCase().indexOf(GlobalVariables.symbolArray_0[1].toLowerCase());
				if (imIndex == 0){		//Has the image file displayed
					if (i == 0) {		// Image file in the first line
						pageContents = pageContents + "[Click here to open below image in windows default program |" + GlobalVariables.fileSymbol + lineContent.substring(GlobalVariables.symbolArray_0[1].length(),lineContent.length()) + "]";
						pageContents = pageContents + GlobalVariables.newline;
					} else {
						lineContentPre = lines[i-1].trim();
						if (lineContentPre.indexOf("[Click here to open below image in windows default program |") == -1) {
							pageContents = pageContents + "[Click here to open below image in windows default program |" + GlobalVariables.fileSymbol + lineContent.substring(GlobalVariables.symbolArray_0[1].length(),lineContent.length()) + "]";
							pageContents = pageContents + GlobalVariables.newline;
						}
					}
				}
				//GlobalVariables.debugDisplay.setText(Integer.toString(lineContent.toLowerCase().indexOf(GlobalVariables.symbolArray_0[1].toLowerCase())));
				pageContents = pageContents + lineContent;
				//System.out.println(lines[i].length());
				//if (i < lines.length-1) {
					pageContents = pageContents + GlobalVariables.newline;
				//}
			}
			GlobalVariables.pageContents.set(GlobalVariables.pageNumber, pageContents);
			FileStringProcess.saveToNoteFile();
			TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(GlobalVariables.pageNumber));
			TextProcess.textPaneDisplay(GlobalVariables.pageContents.get(GlobalVariables.pageNumber));
			GlobalVariables.textPane.setCaretPosition(1);
			
		} else { // Originally in display mode, need switch to edit mode.
			TextProcess.textPaneEdit(GlobalVariables.pageContents.get(GlobalVariables.pageNumber));
		}
		
	}
	
	
	
	

	
}


