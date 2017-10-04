/*
This is for global Variables
*/  

package com.notebook;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.text.Style;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JFrame;


public class GlobalVariables
{
	public static boolean searchVisible = false;  		// Related to search engine
    public static boolean textEditable = true;			// The main panel editable or not
	public static JTextPane textPane, messagePane;
	public static DefaultStyledDocument textDoc, messageDoc;  
	public static Style style;
	public static JButton buttonSaveEdit, buttonSearch;
	public static JFrame frame;
	
}


