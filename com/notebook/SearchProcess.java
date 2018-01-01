/*
This is for Search process. Mainly for search textfield and search panel
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.BorderLayout;
import javax.swing.text.BadLocationException;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import javax.swing.text.Utilities;

import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;

import java.awt.Color;

import javax.swing.text.Document;


public class SearchProcess
{
	// 
	public static void searchResultDisplay(String keyWord) {
		if(GlobalVariables.pageContents.size() > 0) { // Has file loaded
			GlobalVariables.searchFileResults.clear(); // Clear the previous search result
			GlobalVariables.searchPane.setText(null); 
			GlobalVariables.searchVisible = true;
			GlobalVariables.buttonSearch.setText("Remove search result");
			GlobalVariables.frame.getContentPane().add(GlobalVariables.searchScrollPane, BorderLayout.LINE_END); 
			GlobalVariables.frame.validate();			
			boolean titleContain = false;
			boolean contentContain = false;
			for (int i = 0; i < GlobalVariables.pageContents.size(); i++) {
				contentContain = GlobalVariables.pageContents.get(i).toLowerCase().contains(keyWord.toLowerCase());
				titleContain = GlobalVariables.pageList.get(i).toLowerCase().contains(keyWord.toLowerCase());
				if (contentContain || titleContain) {
					GlobalVariables.searchFileResults.add(GlobalVariables.pageList.get(i));
					try {
			
						GlobalVariables.searchDoc.insertString(GlobalVariables.searchPane.getDocument().getLength(), GlobalVariables.pageList.get(i) + GlobalVariables.newline, null);
			
					} catch (Exception e) {
						
					}
				}
			}
		}
	
	}
	
	// Extract the file and get the last edit information
	public static void searchPaneMouseMove(MouseEvent e) {
		int caretPos = GlobalVariables.searchPane.viewToModel(e.getPoint());
		try {
			GlobalVariables.messageField.setText(null);	
			int rowNum = (caretPos == 0) ? 1 : 0;
			for (int offset = caretPos; offset > 0;) {
				offset = Utilities.getRowStart(GlobalVariables.searchPane, offset) - 1;
				rowNum++;
			}
			if (rowNum <= GlobalVariables.searchFileResults.size()){ 
				File file = new File(GlobalVariables.searchFileResults.get(rowNum-1));
				//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				//GlobalVariables.messageField.setText(GlobalVariables.searchFileResults.get(rowNum-1) + "  (" + sdf.format(file.lastModified()) + ")");
				GlobalVariables.messageField.setText(GlobalVariables.searchFileResults.get(rowNum-1));
				GlobalVariables.searchResultFile = GlobalVariables.searchFileResults.get(rowNum-1);
			} else {
				GlobalVariables.searchResultFile = "";
			}				
			
		} catch (BadLocationException e1) {
		   e1.printStackTrace();
		}
		
	}
	
	// Creates highlights around all occurrences of pattern in textComp
    public static void highlight(JTextComponent textComp, String pattern) {
        // First remove all old highlights
        removeHighlights(textComp);
		if(GlobalVariables.searchKeyWord.getText() != null && !GlobalVariables.searchKeyWord.getText().isEmpty()){
			try {
				Highlighter hilite = textComp.getHighlighter();
				Document doc = textComp.getDocument();
				String text = doc.getText(0, doc.getLength());

				int pos = 0;
				// Search for pattern
				while ((pos = text.toLowerCase().indexOf(pattern.toLowerCase(), pos)) >= 0) {
					// Create highlighter using private painter and apply around pattern
					hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
					pos += pattern.length();
				}

			} catch (BadLocationException e) {
			}
		}
    }

    // Removes only our private highlights
    public static  void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        for (int i = 0; i < hilites.length; i++) {
            if (hilites[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
    // An instance of the private subclass of the default highlight painter
    // static Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.yellow);
	static Highlighter.HighlightPainter myHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
}


