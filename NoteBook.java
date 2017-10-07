/*
The Notebook in java for real development.
*/  
import com.notebook.*;


import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

import javax.swing.text.Utilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



  
public class NoteBook {
	
	// Define parameters
	// boolean searchVisible = false;  		// Related to search engine
	// boolean textEditable = true;			// The main panel editable or not
	
	// JFrame frame ;
	// JTextPane textPane, GlobalVariables.messagePane;
	// DefaultStyledDocument textDoc, messageDoc;  
	// Style style;
	// JButton buttonSaveEdit, buttonSearch, buttonTest;
	JButton buttonTest;
	
	private NoteBook()  {
			
		// Define its own color
		Color customGray = new Color(230, 230, 230); 
		
		// Create frame as main display interface
		GlobalVariables.frame = new JFrame("Notebook with Java");
		GlobalVariables.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imgIcon = new ImageIcon("icon.jpg");
		GlobalVariables.frame.setIconImage(imgIcon.getImage());
		
		//Create Menubar
		JMenuBar menuBar = MenuEdit.CreateMenuBar();
		GlobalVariables.frame.setJMenuBar(menuBar);
		
		//Create button panel, mainPanel, and message Panel
		//Refer to readme.docx for further information
		JPanel buttonPanel = new JPanel();		
			
		// Add buttons
        GlobalVariables.buttonSaveEdit = new JButton();
		GlobalVariables.buttonSearch = new JButton();
		if (GlobalVariables.textEditable) {
			GlobalVariables.buttonSaveEdit.setText("Save");
		} else {
			GlobalVariables.buttonSaveEdit.setText("Edit");
		}
		if (GlobalVariables.searchVisible) {
			GlobalVariables.buttonSearch.setText("Remove search result");
		} else {
			GlobalVariables.buttonSearch.setText("Display search result");
		}		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(GlobalVariables.buttonSaveEdit);
		buttonPanel.add(GlobalVariables.buttonSearch);
		GlobalVariables.buttonSaveEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
		GlobalVariables.buttonSearch.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest = new JButton("Test");
		buttonPanel.add(buttonTest);
		buttonTest.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		buttonPanel.add(GlobalVariables.searchLabel);
		GlobalVariables.searchLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.searchKeyWord = new JTextField("Test");
		buttonPanel.add(GlobalVariables.searchKeyWord);
		GlobalVariables.searchKeyWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		// Add textpane and searchPane
		GlobalVariables.textDoc = new DefaultStyledDocument();
        GlobalVariables.textPane = new JTextPane(GlobalVariables.textDoc);
		GlobalVariables.textPane.setBackground(GlobalVariables.textDisplayColor);
		GlobalVariables.style = CreateStyles.CreateStyles(GlobalVariables.textDoc);
		GlobalVariables.textPane.setPreferredSize(new Dimension(800, 100));
		JScrollPane textScrollPane = new JScrollPane(GlobalVariables.textPane);
        textScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setPreferredSize(new Dimension(800, 300));
        textScrollPane.setMinimumSize(new Dimension(10, 10));
		
		GlobalVariables.searchDoc = new DefaultStyledDocument();
        GlobalVariables.searchPane = new JTextPane(GlobalVariables.searchDoc);
		GlobalVariables.searchPane.setPreferredSize(new Dimension(800, 100));
		GlobalVariables.searchPane.setBackground(customGray);
		GlobalVariables.searchScrollPane = new JScrollPane(GlobalVariables.searchPane);
        GlobalVariables.searchScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GlobalVariables.searchScrollPane.setPreferredSize(new Dimension(250, 155));
        GlobalVariables.searchScrollPane.setMinimumSize(new Dimension(10, 10));
		GlobalVariables.searchPane.setEditable(false);
		
		GlobalVariables.messageDoc = new DefaultStyledDocument();
        GlobalVariables.messagePane = new JTextPane(GlobalVariables.messageDoc);
		//messagePane.setPreferredSize(new Dimension(100, 100));
		GlobalVariables.messagePane.setBackground(customGray);
		GlobalVariables.messagePane.setEditable(false);
		
		
		// Add panels to GlobalVariables.frame
	    GlobalVariables.frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
		GlobalVariables.frame.getContentPane().add(textScrollPane, BorderLayout.CENTER);
		//GlobalVariables.frame.getContentPane().add(GlobalVariables.searchScrollPane, BorderLayout.LINE_END); 
		GlobalVariables.frame.getContentPane().add(GlobalVariables.messagePane, BorderLayout.PAGE_END);
		
		
		GlobalVariables.frame.pack();
        GlobalVariables.frame.setVisible(true);
		
		/* Remove searchScrollPane later will keep the space for this pane. 
		 If didn't add in the beginning, in the default layout (not maximum window), set the search panel back,
		 the search panel will take all the window and leave textpanel no space to display.
		*/ 
		// GlobalVariables.frame.getContentPane().remove(GlobalVariables.searchScrollPane); 
		// GlobalVariables.frame.invalidate();
		//GlobalVariables.frame.validate();
		
		
		// Edit/save functional switch
		GlobalVariables.buttonSaveEdit.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) {			  
				ButtonProcess.buttonSaveEditPress(evt);
		  }
		} );
		
		// Enable/disable search result panel, controlled by buttonSearch
		GlobalVariables.buttonSearch.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) {			  
				ButtonProcess.buttonSearchPress(evt);
		  }
		} );
		
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
				//GlobalVariables.frame.setTitle("Test");	
				System.out.println(GlobalVariables.dirName );	
				
			  } 
		} );
		
		// searchKeyWord extract listener (extract the string when press enter key)
		GlobalVariables.searchKeyWord.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) { 
				//GlobalVariables.frame.setTitle("Test");	
				//System.out.println(GlobalVariables.searchKeyWord.getText());
				// File file = new File(GlobalVariables.dirName);
				// SearchProcess.displayDirectoryContents(GlobalVariables.searchKeyWord.getText(), file);				
			} 
		});
		
		// searchKeyWord key listener 
		GlobalVariables.searchKeyWord.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke) { 
				//GlobalVariables.frame.setTitle("Test");	
				//System.out.println(GlobalVariables.searchKeyWord.getText());
				File file = new File(GlobalVariables.dirName);
				SearchProcess.displayDirectoryContents(GlobalVariables.searchKeyWord.getText(), file);				
			} 
		});
		
		// Extract the line string with mouse moving action and display in the message panel
		// Refer to MessageProcess sub module
		GlobalVariables.textPane.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				MessageProcess.textPaneMouseMove(e);
 
			}
		});
		
		
	}
	
		
	public static void main(String[] args) {
        new NoteBook();
	}
	
}

	