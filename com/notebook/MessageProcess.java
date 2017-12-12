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
import java.io.File;
import java.text.SimpleDateFormat;
import javax.accessibility.AccessibleText;
import java.awt.Point;


public class MessageProcess
{
	
	// Extract the text from Main EditDisplay panel and display in the message panel with mouse moving action.
	
	public static void textPaneMouseMove(MouseEvent e) {

		
		try {
			GlobalVariables.messagePane.setText(null);								// Set message panel to display nothing
			
			
			// Get the text in the row to the end of the mouse
			AccessibleText accessibleText = GlobalVariables.textPane.getAccessibleContext().getAccessibleText();
			Point p = e.getPoint();
			int index = accessibleText.getIndexAtPoint(p);
			String lineKeyStr = GlobalVariables.textPane.getText(0, index);
			if (lineKeyStr.lastIndexOf("\n") != -1) {
					lineKeyStr = lineKeyStr.substring(lineKeyStr.lastIndexOf("\n") + 1);
			}
			
			// Extract the contents in the whole line of the mouse
			int offset = GlobalVariables.textPane.viewToModel(e.getPoint());			// Get mouse position
			int rowStart = Utilities.getRowStart(GlobalVariables.textPane, offset); // Get the row of the mouse start position (related to string length)
			int textLength = GlobalVariables.textPane.getDocument().getLength();	// Get the whole length of the text pane
			// If the rowStart is higher than textLength, it means the mouse is moving under the real end of text
			// Otherwise, the contents need be processed to get exact string of line
			String lineContent = null;
			if (textLength > rowStart) { 
				String dispContent1 =  GlobalVariables.textPane.getText(0, rowStart);
				String dispContent2 =  GlobalVariables.textPane.getText(rowStart, textLength-rowStart);				
				int index1 = dispContent1.lastIndexOf("\n");
				int index2 = dispContent2.indexOf("\n");
				if ((index2 != -1) && (index1 != -1)){
					lineContent = dispContent1.substring(index1+1) + dispContent2.substring(0, index2);
					//GlobalVariables.messageDoc.insertString(0, lineContent, null);
				} else if (index2 != -1) {
					lineContent = dispContent2.substring(0, index2);
					//GlobalVariables.messageDoc.insertString(0, lineContent, null);
				} else if (index1 != -1) {
					lineContent = dispContent1.substring(index1+1) + dispContent2.substring(0, textLength);
					//GlobalVariables.messageDoc.insertString(0, lineContent, null);
				} else {
					lineContent = dispContent2.substring(0, textLength);
					//GlobalVariables.messageDoc.insertString(0, lineContent, null);
				}
			}
			
			String dispContent = linkMessageProc(lineKeyStr,lineContent);
			
		
			GlobalVariables.messageDoc.insertString(0, dispContent, null);
		} catch (BadLocationException e1) {
	 
		}
		
	
	}
	
	// Extract string process
	public static String linkMessageProc(String lineKeyStr, String lineContent) {
		String result = null;
		int index1 = lineKeyStr.lastIndexOf("[");
		if (index1 > -1){
			String strProc = lineContent.substring(index1);
			String strTemp = lineKeyStr.substring(index1);
			int index2 = strProc.indexOf("]");
			int index3 = strTemp.indexOf("]");
			if (index2 > -1){
				if (index3 == -1){
					result = strProc.substring(1, index2);
				}
			}
		}
		
		return result;
	}
	
	
	// Extract the file and get the last edit information
	public static void searchPaneMouseMove(MouseEvent e) {
		int caretPos = GlobalVariables.searchPane.viewToModel(e.getPoint());
		try {
			GlobalVariables.messagePane.setText(null);	
			int rowNum = (caretPos == 0) ? 1 : 0;
			for (int offset = caretPos; offset > 0;) {
				offset = Utilities.getRowStart(GlobalVariables.searchPane, offset) - 1;
				rowNum++;
			}
			if (rowNum <= GlobalVariables.searchFileResults.size()){ 
				File file = new File(GlobalVariables.searchFileResults.get(rowNum-1));
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				GlobalVariables.messagePane.setText(GlobalVariables.searchFileResults.get(rowNum-1) + "  (" + sdf.format(file.lastModified()) + ")");
			}					
			
		} catch (BadLocationException e1) {
		   e1.printStackTrace();
		}
		
	}
		
}


