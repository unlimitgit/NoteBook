/*
This is for message panel display and process
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.MouseEvent;
import javax.swing.text.Utilities;
import javax.swing.text.BadLocationException;


public class MessageProcess
{
	
	// Extract the text from Main EditDisplay panel and display in the message panel with mouse moving action.
	public static void textPaneMouseMove(MouseEvent e) {
		int offset = GlobalVariables.textPane.viewToModel(e.getPoint());			// Get mouse position
		try {
			GlobalVariables.messagePane.setText(null);								// Set message panel to display nothing
			int rowStart = Utilities.getRowStart(GlobalVariables.textPane, offset); // Get the row of the mouse start position (related to string length)
			int textLength = GlobalVariables.textPane.getDocument().getLength();	// Get the whole length of the text pane
			// If the rowStart is higher than textLength, it means the mouse is moving under the real end of text
			// Otherwise, the contents need be processed to get exact string of line
			if (textLength > rowStart) { 
				String dispContent1 =  GlobalVariables.textPane.getText(0, rowStart);
				String dispContent2 =  GlobalVariables.textPane.getText(rowStart, textLength-rowStart);
				String displayContent = null;
				int index1 = dispContent1.lastIndexOf("\n");
				int index2 = dispContent2.indexOf("\n");
				if ((index2 != -1) && (index1 != -1)){
					displayContent = dispContent1.substring(index1+1) + dispContent2.substring(0, index2);
					GlobalVariables.messageDoc.insertString(0, displayContent, null);
				} else if (index2 != -1) {
					displayContent = dispContent2.substring(0, index2);
					GlobalVariables.messageDoc.insertString(0, displayContent, null);
				} else if (index1 != -1) {
					displayContent = dispContent1.substring(index1+1) + dispContent2.substring(0, textLength);
					GlobalVariables.messageDoc.insertString(0, displayContent, null);
				} else {
					displayContent = dispContent2.substring(0, textLength);
					GlobalVariables.messageDoc.insertString(0, displayContent, null);
				}
			}

		} catch (BadLocationException e1) {
	 
		}
	
	}
		
}


