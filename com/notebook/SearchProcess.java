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
	
	
	// Extract the text from Main TextProcess panel and display in the message panel with mouse moving action.
	public static void displayDirectoryContents(String keyWord, File dir) {
		try {
			GlobalVariables.searchPane.setText(null);
			//GlobalVariables.searchFileResults.removeAll();
			GlobalVariables.searchFileResults.clear();
			GlobalVariables.searchVisible = true;
			GlobalVariables.buttonSearch.setText("Remove search result");
			GlobalVariables.frame.getContentPane().add(GlobalVariables.searchScrollPane, BorderLayout.LINE_END); 
			GlobalVariables.frame.validate();
			
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryContents(keyWord, file);
				} else {
					if (file.getName().endsWith((".java"))) {
						final Scanner scanner = new Scanner(file);
						while (scanner.hasNextLine()) {
						   final String lineFromFile = scanner.nextLine();
						   if(containsIgnoreCase(lineFromFile,keyWord)) { 
							  try {
								   GlobalVariables.searchFileResults.add(file.getCanonicalPath());
								   String content = file.getName().substring(0, file.getName().lastIndexOf('.'));
								   GlobalVariables.searchDoc.insertString(GlobalVariables.searchPane.getDocument().getLength(), content + GlobalVariables.newline, null);
							   } catch (Exception e) {  
					
							   }
							   break;
						   }
						}
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean containsIgnoreCase(String src, String what) {
		final int length = what.length();
		if (length == 0)
			return true; // Empty string is contained

		final char firstLo = Character.toLowerCase(what.charAt(0));
		final char firstUp = Character.toUpperCase(what.charAt(0));

		for (int i = src.length() - length; i >= 0; i--) {
			// Quick check before calling the more expensive regionMatches() method:
			final char ch = src.charAt(i);
			if (ch != firstLo && ch != firstUp)
				continue;

			if (src.regionMatches(true, i, what, 0, length))
				return true;
		}

		return false;
	}
		
}


