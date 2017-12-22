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
import java.awt.Cursor;
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

import java.net.URI;
import java.io.IOException;



  
public class NoteBook {
	
	// Define parameters
	// boolean searchVisible = false;  		// Related to search engine
	// boolean textEditable = true;			// The main panel editable or not
	
	// JFrame frame ;
	// JTextPane textPane, GlobalVariables.messageField;
	// DefaultStyledDocument textDoc, messageDoc;  
	// Style style;
	// JButton buttonSaveEdit, buttonSearch, buttonTest;
	JButton buttonTest;
	
	private NoteBook()  {
			
		// Define its own color
		
		
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
		
		GlobalVariables.buttonPrevious = new JButton("Previous");
		buttonPanel.add(GlobalVariables.buttonPrevious);
		GlobalVariables.buttonPrevious.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.buttonNext = new JButton("Next");
		buttonPanel.add(GlobalVariables.buttonNext);
		GlobalVariables.buttonNext.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest = new JButton("Test");
		buttonPanel.add(buttonTest);
		buttonTest.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		buttonPanel.add(GlobalVariables.searchLabel);
		GlobalVariables.searchLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.searchKeyWord = new JTextField();
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
		GlobalVariables.textPane.setEditable(false);
		
		GlobalVariables.searchDoc = new DefaultStyledDocument();
        GlobalVariables.searchPane = new JTextPane(GlobalVariables.searchDoc);
		GlobalVariables.searchPane.setPreferredSize(new Dimension(800, 100));
		GlobalVariables.searchPane.setBackground(GlobalVariables.customGray);
		GlobalVariables.searchScrollPane = new JScrollPane(GlobalVariables.searchPane);
        GlobalVariables.searchScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GlobalVariables.searchScrollPane.setPreferredSize(new Dimension(250, 155));
        GlobalVariables.searchScrollPane.setMinimumSize(new Dimension(10, 10));
		GlobalVariables.searchPane.setEditable(false);
		
        GlobalVariables.messageField = new JTextField();
		//messageField.setPreferredSize(new Dimension(100, 100));
		GlobalVariables.messageField.setBackground(GlobalVariables.customGray);
		GlobalVariables.messageField.setEditable(false);
		
		
		// Add panels to GlobalVariables.frame
	    GlobalVariables.frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
		GlobalVariables.frame.getContentPane().add(textScrollPane, BorderLayout.CENTER);
		//GlobalVariables.frame.getContentPane().add(GlobalVariables.searchScrollPane, BorderLayout.LINE_END); 
		GlobalVariables.frame.getContentPane().add(GlobalVariables.messageField, BorderLayout.PAGE_END);
		
		
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
		
		// Refer to MessageProcess sub module
		GlobalVariables.searchPane.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				MessageProcess.searchPaneMouseMove(e);
 
			}
		});
		
		// Extract the string contents, and open the website page included by '[' ']'
		GlobalVariables.searchPane.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (e.getButton() != MouseEvent.BUTTON1) {
               return;
            }
            if (e.getClickCount() != 2) {
               return;
            }
			try {
				int caretPos = GlobalVariables.searchPane.getCaretPosition();
				int rowNum = (caretPos == 0) ? 1 : 0;
				for (int offset = caretPos; offset > 0;) {
					offset = Utilities.getRowStart(GlobalVariables.searchPane, offset) - 1;
					rowNum++;
				}
				//System.out.println("Row: " + rowNum); 
				//System.out.println(GlobalVariables.searchFileResults.size());
				if (rowNum <= GlobalVariables.searchFileResults.size()){ 
					//System.out.println(GlobalVariables.searchFileResults.get(rowNum-1));
					GlobalVariables.fileSequences.add(GlobalVariables.searchFileResults.get(rowNum-1));
					GlobalVariables.fileName = GlobalVariables.searchFileResults.get(rowNum-1);
					//EditDisplay.loadFileDisplayProc(GlobalVariables.searchFileResults.get(rowNum-1));
					GlobalVariables.frame.setTitle("Notebook with Java: " +  GlobalVariables.searchFileResults.get(rowNum-1));	
				}					
				
			} catch (BadLocationException e1) {
               e1.printStackTrace();
            }
         }
      });
	  
	  // This button is only for temporary test, will remove in the official version.
		buttonTest.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
		  
				String contents = GlobalVariables.textPane.getText();
				System.out.println(contents);
			  } 
		} );
		
		// Mouse click process for textPane
		GlobalVariables.textPane.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				System.out.println(GlobalVariables.linkProcResult.linkName);
				System.out.println(GlobalVariables.linkNumber);
				if (GlobalVariables.linkProcResult.linkExit) {  // If the link existing, open the page
					if (GlobalVariables.linkNumber == 2) {	//If it is webpage
						URI uri = URI.create(GlobalVariables.linkProcResult.linkName);
						try{
							java.awt.Desktop.getDesktop().browse(uri);
						}catch(IOException f2){
						
						}
					} else if (GlobalVariables.linkNumber == 1) { // If it is existing page link
						int i =  GlobalVariables.pageListLowerCase.indexOf(GlobalVariables.linkProcResult.linkName.toLowerCase());
						GlobalVariables.pageNumber = i;
						String dispContents = GlobalVariables.pageContents.get(i);
						GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
						EditDisplay.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
						EditDisplay.textPaneDisplay(dispContents);
					} else if (GlobalVariables.linkNumber == 3) {	// link is file
					    
						File file = new File(GlobalVariables.linkProcResult.linkName);
						boolean exists = file.exists();
						if (exists) {
							try{
								java.awt.Desktop.getDesktop().open(file);
							}catch(IOException f3){
							
							}
						} else {
							GlobalVariables.messageField.setText("File: " + GlobalVariables.linkProcResult.linkName + " not existing. Please double check.");
							GlobalVariables.messageField.setBackground(Color.yellow);
						}
						
					}
				} else if (GlobalVariables.linkProcResult.linkFind){ // If the link found but not existing
					String dispContent = "Page '" + GlobalVariables.linkProcResult.linkName 
											+ "' doesn't exist. Create it? (y/n):";
					GlobalVariables.messageField.setText(dispContent);
					GlobalVariables.messageField.setBackground(Color.yellow);
					GlobalVariables.messageField.setEditable(true);
					GlobalVariables.messageEditable = true;
					GlobalVariables.messageField.requestFocus();
					
				} 

			 }
		});
		
				
		GlobalVariables.messageField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) { 
				// Extract key input from message
				String content = GlobalVariables.messageField.getText();
				// Clear the contents in the message box
				GlobalVariables.messageField.setEditable(false);
				GlobalVariables.messageEditable = false;
				GlobalVariables.messageField.setText(null);	
				GlobalVariables.messageField.setBackground(GlobalVariables.customGray);	
				// Extract the real key input
				int index = content.indexOf("(y/n):");
				String keyInput = content.substring(index+6);
				if ((keyInput.toLowerCase().equals("yes")) || (keyInput.toLowerCase().equals("y"))) { // If yes
					GlobalVariables.pageList.add(GlobalVariables.linkProcResult.linkName);
					GlobalVariables.pageListLowerCase.add(GlobalVariables.linkProcResult.linkName.toLowerCase());
					GlobalVariables.pageContents.add(null);
					GlobalVariables.pageNumber = GlobalVariables.pageList.size()-1;
					GlobalVariables.pageSymbol = GlobalVariables.linkProcResult.linkName;
					FileStringProcess.saveToNoteFile();		
					EditDisplay.textPaneEdit(GlobalVariables.pageContents.get(GlobalVariables.pageNumber));					
				} 							
			} 
		});
		
		
	}
	
		
	public static void main(String[] args) {
        new NoteBook();
	}
	
}

	
