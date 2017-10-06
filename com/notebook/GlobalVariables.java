/*
This is for global Variables
*/  

package com.notebook;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.text.Style;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GlobalVariables
{
	public static boolean searchVisible = false;  		// Related to search engine
    public static boolean textEditable = false;			// The main panel editable or not
	public static JTextPane textPane, messagePane, searchPane;
	public static DefaultStyledDocument textDoc, messageDoc, searchDoc;  
	public static Style style;
	public static JButton buttonSaveEdit, buttonSearch;
	public static JFrame frame;
	public static String  fileName ;
	public static String  dirName = "./";
	public static JScrollPane searchScrollPane;
	public static final Color textDisplayColor = new Color(240,240,240);
	public static JLabel searchLabel = new JLabel("Enter here to search: ");
	public static JTextField searchKeyWord ;
	
	
	
}


