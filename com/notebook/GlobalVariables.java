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
import java.util.List;
import java.util.ArrayList;


public class GlobalVariables
{
	
	public static class InterpDispResult{
		int indexStyle = 0;	
		int number = 0;
		Boolean symbolFind = false;
		String dispContent ;
	}
	
	public static class LinkResult{
		public static Boolean linkFind = false;
		public static Boolean linkExit = false;
		public static String linkName = null ;
	}
	
	public static boolean searchVisible = false;  		// Related to search engine
    public static boolean textEditable = false;			// The main panel editable or not
	public static JTextPane textPane,searchPane;
	public static JTextField messageField;
	public static DefaultStyledDocument textDoc, searchDoc;  
	public static Style style;
	public static JButton buttonSaveEdit, buttonSearch, buttonPrevious, buttonNext;
	public static JFrame frame;
	public static String  fileName ;
	public static String  dirName = "./";
	public static JScrollPane searchScrollPane;
	public static final Color textDisplayColor = new Color(250,250,250);
	public static final Color textEditColor = new Color(255,255,255);
	public static JLabel searchLabel = new JLabel("Enter here to search: ");
	public static JTextField searchKeyWord ;
	public static List<String> searchFileResults = new ArrayList<String>();
	public static List<String> fileSequences = new ArrayList<String>();
	public static ArrayList<String> pageList = new ArrayList<String>();
	public static ArrayList<String> pageListLowerCase = new ArrayList<String>();
	public static ArrayList<String> pageContents = new ArrayList<String>();
	public static int pageNumber = 0 ;  // The page number related to pagelist.
	public static final String pageTitle = "PAGE::::";  // The string add before page name to represent the beginning of one page
	public static String pageSymbol = "Home";    // The first line of one page. Root page: pageTitle + Home; Child page: pageTitle + ***
	public static String fileSymbol = "file::::";
	public static String webSymbol = "http";
	public static int linkNumber = 0; // 0: Link not existing; 1: Link is existing with page; 2: link is webpage; 3: link is file
	public static final int columns_1 = 2,  rows_1 = 3, rows_2 = 3, columns_3 = 2, rows_3 = 5, columns_4 = 2, rows_4 = 1; // It coresponds to the dimension of symbols
	public static String[][] symbolArray_1 = new String[][]{{"= ", " ="}, {"== ", " =="}, {"=== ", " ==="}};
	public static String[] symbolArray_2 = new String[]{"*","#",":"};
	public static String[][] symbolArray_3 = new String[][]{{"<h>", "</h>"}, {"<b>", "</b>"}, {"<i>", "</i>"}, {"<s>", "</s>"}, {"<x>", "</x>"}};
	public static String[][] symbolArray_4 = new String[][]{{"[", "]"}};
	public static LinkResult linkProcResult = new LinkResult();
    public static boolean messageEditable = false;	
	public static Color customGray = new Color(230, 230, 230); 
	//public static String newline = System.getProperty("line.separator");
	public static String newline = "\n";
	
}


