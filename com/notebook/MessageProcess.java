package com.notebook;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.MouseEvent;
import javax.swing.text.Utilities;
import javax.swing.text.BadLocationException;


public class MessageProcess
{
	
	public static void textPaneMouseMove(MouseEvent e, JTextPane textPane, JTextPane messagePane, DefaultStyledDocument messageDoc) {
		int offset = textPane.viewToModel(e.getPoint());
		try {
			messagePane.setText(null);
			int rowStart = Utilities.getRowStart(textPane, offset);
		   int textLength = textPane.getDocument().getLength();
		   if (textLength > rowStart) { 
			   String dispContent1 =  textPane.getText(0, rowStart);
			   String dispContent2 =  textPane.getText(rowStart, textLength-rowStart);
			   String displayContent = null;
			   int index1 = dispContent1.lastIndexOf("\n");
			   int index2 = dispContent2.indexOf("\n");
			   if ((index2 != -1) && (index1 != -1)){
				   displayContent = dispContent1.substring(index1+1) + dispContent2.substring(0, index2);
				   messageDoc.insertString(0, displayContent, null);
			   } else if (index2 != -1) {
				   displayContent = dispContent2.substring(0, index2);
				   messageDoc.insertString(0, displayContent, null);
			   } else if (index1 != -1) {
				   displayContent = dispContent1.substring(index1+1) + dispContent2.substring(0, textLength);
				   messageDoc.insertString(0, displayContent, null);
			   } else {
				   displayContent = dispContent2.substring(0, textLength);
				   messageDoc.insertString(0, displayContent, null);
			   }
		   }

		} catch (BadLocationException e1) {
	 
		}
	
	}
		
}


