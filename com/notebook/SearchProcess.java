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


public class SearchProcess
{
	
	// Extract the text from Main EditDisplay panel and display in the message panel with mouse moving action.
	public static void displayDirectoryContents(String keyWord, File dir) {
		try {
			GlobalVariables.searchPane.setText(null);
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
						   if(lineFromFile.contains(keyWord)) { 
							   // a match!
							   //System.out.println("I found " + keyWord + " in file " +file.getName().substring(0, file.getName().lastIndexOf('.')));
							   try {
								   String content = file.getName().substring(0, file.getName().lastIndexOf('.'));
								   GlobalVariables.searchDoc.insertString(0, content + "\n", null);
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
		
}


