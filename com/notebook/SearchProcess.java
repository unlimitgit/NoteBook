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


public class SearchProcess
{
	
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
					//System.out.println("directory:" + file.getCanonicalPath());
					displayDirectoryContents(keyWord, file);
				} else {
					if (file.getName().endsWith((".java"))) {
						//System.out.println("     file:" + file.getCanonicalPath());
						final Scanner scanner = new Scanner(file);
						while (scanner.hasNextLine()) {
						   final String lineFromFile = scanner.nextLine();
						   //if(lineFromFile.contains(keyWord)) { 
						   if(containsIgnoreCase(lineFromFile,keyWord)) { 
							   // a match!
							   //System.out.println("I found " + keyWord + " in file " +file.getName().substring(0, file.getName().lastIndexOf('.')));
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


