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

import javax.swing.KeyStroke;
import javax.swing.undo.*;
import java.awt.Event;
//import javax.swing.undo.CannotUndoException;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.event.*;



  
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
		
		GlobalVariables.buttonHome = new JButton("Home");
		buttonPanel.add(GlobalVariables.buttonHome);
		GlobalVariables.buttonHome.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.buttonUndo = new JButton("Undo");
		buttonPanel.add(GlobalVariables.buttonUndo);
		GlobalVariables.buttonUndo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.buttonRedo = new JButton("Redo");
		buttonPanel.add(GlobalVariables.buttonRedo);
		GlobalVariables.buttonRedo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest = new JButton("Test");
		//buttonPanel.add(buttonTest);
		//buttonTest.setAlignmentX(Component.RIGHT_ALIGNMENT);
		//buttonTest.setForeground(Color.GRAY);
		
		buttonPanel.add(GlobalVariables.searchLabel);
		GlobalVariables.searchLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.searchKeyWord = new JTextField();
		buttonPanel.add(GlobalVariables.searchKeyWord);
		GlobalVariables.searchKeyWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		GlobalVariables.debugDisplay = new JTextField();
		buttonPanel.add(GlobalVariables.debugDisplay);
		GlobalVariables.debugDisplay.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		// Add textpane and searchPane
		GlobalVariables.textDoc = new DefaultStyledDocument();
        GlobalVariables.textPane = new JTextPane(GlobalVariables.textDoc);
		GlobalVariables.textPane.setBackground(GlobalVariables.textDisplayColor);
		GlobalVariables.style = CreateStyles.CreateStyles(GlobalVariables.textDoc);
		GlobalVariables.textPane.setPreferredSize(new Dimension(800, 100));
		GlobalVariables.textScrollPane = new JScrollPane(GlobalVariables.textPane);
        GlobalVariables.textScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GlobalVariables.textScrollPane.setPreferredSize(new Dimension(800, 300));
        GlobalVariables.textScrollPane.setMinimumSize(new Dimension(10, 10));
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
		CreateStyles.CreateSearchStyles(GlobalVariables.searchDoc); // create special style
		
        GlobalVariables.messageField = new JTextField();
		//messageField.setPreferredSize(new Dimension(100, 100));
		GlobalVariables.messageField.setBackground(GlobalVariables.customGray);
		GlobalVariables.messageField.setEditable(false);
		
		
		// Add panels to GlobalVariables.frame
	    GlobalVariables.frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
		GlobalVariables.frame.getContentPane().add(GlobalVariables.textScrollPane, BorderLayout.CENTER);
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
		
		// Purely for undo and redo
		
		 KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, Event.CTRL_MASK);
		 KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, Event.CTRL_MASK);
			
		UndoManager undoManager = new UndoManager();
		
		GlobalVariables.textDoc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});
		
		// Add ActionListeners
		GlobalVariables.buttonUndo.addActionListener((ActionEvent e) -> {
			try {
				undoManager.undo();
			} catch (CannotUndoException cue) {}
		});
		GlobalVariables.buttonRedo.addActionListener((ActionEvent e) -> {
			try {
				undoManager.redo();
			} catch (CannotRedoException cre) {}
		});
		
		// Map undo action
		GlobalVariables.textPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(undoKeyStroke, "undoKeyStroke");
		GlobalVariables.textPane.getActionMap().put("undoKeyStroke", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.undo();
				 } catch (CannotUndoException cue) {}
			}
		});
		// Map redo action
		GlobalVariables.textPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(redoKeyStroke, "redoKeyStroke");
		GlobalVariables.textPane.getActionMap().put("redoKeyStroke", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.redo();
				 } catch (CannotRedoException cre) {}
			}
		});
		
		// End for undo and redo
		
		
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
		
		
		// Go back to home page
		GlobalVariables.buttonHome.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) {			  
			int i =  GlobalVariables.pageList.indexOf("Home");
			GlobalVariables.pageSequences = FileStringProcess.pageSequenceProc(GlobalVariables.pageSequences, "Home");
			GlobalVariables.pageNumber = i;
			String dispContents = GlobalVariables.pageContents.get(i);
			GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
			TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
			GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + "Home");
			TextProcess.textPaneDisplay(dispContents);
			SearchProcess.highlight(GlobalVariables.textPane,GlobalVariables.searchKeyWord.getText());
			//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
			GlobalVariables.textPane.setCaretPosition(1);
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
			// public void keyPressed(KeyEvent ke) { 
			public void keyReleased(KeyEvent ke) { 
				//GlobalVariables.frame.setTitle("Test");
				
				//System.out.println(GlobalVariables.searchKeyWord.getText());
				// File file = new File(GlobalVariables.dirName);
				// SearchProcess.displayDirectoryContents(GlobalVariables.searchKeyWord.getText(), file);				
				SearchProcess.searchResultDisplay(GlobalVariables.searchKeyWord.getText());
				//if(GlobalVariables.searchKeyWord.getText() != null && !GlobalVariables.searchKeyWord.getText().isEmpty()){
					SearchProcess.highlight(GlobalVariables.textPane,GlobalVariables.searchKeyWord.getText());
				//} else {
				//	SearchProcess.removeHighlights(GlobalVariables.textPane);
				//}
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
				SearchProcess.searchPaneMouseMove(e);
 
			}
		});
		
		// Open the linked file
		GlobalVariables.searchPane.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
			if (!GlobalVariables.searchResultFile.equals("")){
				int i =  GlobalVariables.pageList.indexOf(GlobalVariables.searchResultFile);
				if (GlobalVariables.pageNumber != i){ // The designated page is not the opened page
					GlobalVariables.pageNumber = i;
					GlobalVariables.pageSequences = FileStringProcess.pageSequenceProc(GlobalVariables.pageSequences, GlobalVariables.searchResultFile);
					String dispContents = GlobalVariables.pageContents.get(i);
					GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
					GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + GlobalVariables.pageList.get(i));
					TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
					TextProcess.textPaneDisplay(dispContents);
					SearchProcess.highlight(GlobalVariables.textPane,GlobalVariables.searchKeyWord.getText());
					//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
					GlobalVariables.textPane.setCaretPosition(1);
				}	
			}
         }
      });
	  
	  //Previous button process
	  GlobalVariables.buttonPrevious.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
				if(GlobalVariables.pageSeqDepth > 0) {
					String pageName = GlobalVariables.pageSequences.get(GlobalVariables.pageSeqDepth-1);
					int i =  GlobalVariables.pageListLowerCase.indexOf(pageName.toLowerCase());
					GlobalVariables.pageNumber = i;
					String dispContents = GlobalVariables.pageContents.get(i);
					GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + GlobalVariables.pageList.get(i));
					GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
					TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
					TextProcess.textPaneDisplay(dispContents);
					//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
					GlobalVariables.textPane.setCaretPosition(1);
					GlobalVariables.pageSeqDepth = GlobalVariables.pageSeqDepth-1;
				}
				
			  } 
		} );
		
		
		//Next button process
	  GlobalVariables.buttonNext.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
				if(GlobalVariables.pageSeqDepth < (GlobalVariables.pageSequences.size()-1) ) {
					String pageName = GlobalVariables.pageSequences.get(GlobalVariables.pageSeqDepth+1);
					int i =  GlobalVariables.pageListLowerCase.indexOf(pageName.toLowerCase());
					GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + GlobalVariables.pageList.get(i));
					GlobalVariables.pageNumber = i;
					String dispContents = GlobalVariables.pageContents.get(i);
					GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
					TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
					TextProcess.textPaneDisplay(dispContents);
					GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
					GlobalVariables.pageSeqDepth = GlobalVariables.pageSeqDepth+1;
				}
				
			  } 
		} );
	  
	  // This button is only for temporary test, will remove in the official version.
		buttonTest.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
		  
				//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);		// Set scroll position to top
				// System.out.println(GlobalVariables.textScrollPane.getVerticalScrollBar().getValue());
				// GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);		// Set scroll position to top
				// System.out.println(GlobalVariables.textScrollPane.getVerticalScrollBar().getValue());
				
				// System.out.println(GlobalVariables.searchFileResults.size());
				// if (GlobalVariables.searchFileResults.size() > 0) {
					// for (int i = 0; i < GlobalVariables.searchFileResults.size(); i++) {
						// System.out.println(GlobalVariables.searchFileResults.get(i));
					// }
				// }
				//SearchProcess.removeHighlights(GlobalVariables.textPane);
				// GlobalVariables.textEditable = true;
				// GlobalVariables.textPane.setEditable(true);
				// for (int i=0; i<GlobalVariables.pageSequences.size(); i++)
				// {
					// System.out.println(GlobalVariables.pageSequences.get(i));
				// }
				// System.out.println(GlobalVariables.pageSequences.size() + "," + GlobalVariables.pageSeqDepth);
				
			  } 
		} );
		
		// Mouse click process for textPane
		GlobalVariables.textPane.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				//System.out.println(GlobalVariables.linkProcResult.linkName);
				//System.out.println(GlobalVariables.linkNumber);
				if (GlobalVariables.linkProcResult.linkExist) {  // If the link existing, open the page
					if (GlobalVariables.linkNumber == 2) {	//If it is webpage
						URI uri = URI.create(GlobalVariables.linkProcResult.linkName);
						try{
							java.awt.Desktop.getDesktop().browse(uri);
						}catch(IOException f2){
						
						}
					} else if (GlobalVariables.linkNumber == 1) { // If it is existing page link
						int i =  GlobalVariables.pageListLowerCase.indexOf(GlobalVariables.linkProcResult.linkName.toLowerCase());
						GlobalVariables.pageNumber = i;
						GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + GlobalVariables.pageList.get(i));
						GlobalVariables.pageSequences = FileStringProcess.pageSequenceProc(GlobalVariables.pageSequences, GlobalVariables.linkProcResult.linkName);
						String dispContents = GlobalVariables.pageContents.get(i);
						GlobalVariables.pageSymbol = GlobalVariables.pageList.get(i);
						TextProcess.textPaneTitleDisplay(GlobalVariables.pageList.get(i));
						TextProcess.textPaneDisplay(dispContents);
						//GlobalVariables.textScrollPane.getVerticalScrollBar().setValue(1);
						GlobalVariables.textPane.setCaretPosition(1);
					} else if (GlobalVariables.linkNumber == 3) {	// link is file
					    
						File file = new File(GlobalVariables.linkProcResult.linkName);
						File sameFile = new File(GlobalVariables.linkProcResult.linkName);
						GlobalVariables.debugDisplay.setText(GlobalVariables.linkProcResult.linkName);
						boolean exists = file.exists();
						if (exists) {
							try{
								if(file.renameTo(sameFile)){ 
									java.awt.Desktop.getDesktop().open(file);
								}
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
					GlobalVariables.frame.setTitle(GlobalVariables.frameDisplay + GlobalVariables.linkProcResult.linkName);
					GlobalVariables.pageListLowerCase.add(GlobalVariables.linkProcResult.linkName.toLowerCase());
					GlobalVariables.pageContents.add(null);
					GlobalVariables.linkProcResult.linkExist = true; // Set link exis
					GlobalVariables.pageSequences = FileStringProcess.pageSequenceProc(GlobalVariables.pageSequences, GlobalVariables.linkProcResult.linkName);
					GlobalVariables.pageNumber = GlobalVariables.pageList.size()-1;
					GlobalVariables.pageSymbol = GlobalVariables.linkProcResult.linkName;
					FileStringProcess.saveToNoteFile();		
					TextProcess.textPaneEdit(GlobalVariables.pageContents.get(GlobalVariables.pageNumber));					
				} 							
			} 
		});
		
		
	}
	
		
	public static void main(String[] args) {
        new NoteBook();
	}
	
}

	
