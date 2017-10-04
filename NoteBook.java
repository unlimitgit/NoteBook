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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;

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
		String textFrame = "Notebook with Java";
		GlobalVariables.frame = new JFrame(textFrame);
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
        GlobalVariables.buttonSaveEdit = new JButton("Save");
		GlobalVariables.buttonSearch = new JButton("Enable search");
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(GlobalVariables.buttonSaveEdit);
		buttonPanel.add(GlobalVariables.buttonSearch);
		GlobalVariables.buttonSaveEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
		GlobalVariables.buttonSearch.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest = new JButton("Test");
		buttonPanel.add(buttonTest);
		buttonTest.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		// Add textpane and searchPane
		GlobalVariables.textDoc = new DefaultStyledDocument();
        GlobalVariables.textPane = new JTextPane(GlobalVariables.textDoc);
		GlobalVariables.style = CreateStyles.CreateStyles(GlobalVariables.textDoc);
		GlobalVariables.textPane.setPreferredSize(new Dimension(800, 100));
		JScrollPane textScrollPane = new JScrollPane(GlobalVariables.textPane);
        textScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setPreferredSize(new Dimension(250, 155));
        textScrollPane.setMinimumSize(new Dimension(10, 10));
		
		DefaultStyledDocument searchDoc = new DefaultStyledDocument();
        JTextPane searchPane = new JTextPane(searchDoc);
		searchPane.setPreferredSize(new Dimension(800, 100));
		searchPane.setBackground(customGray);
		JScrollPane searchScrollPane = new JScrollPane(searchPane);
        searchScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        searchScrollPane.setPreferredSize(new Dimension(250, 155));
        searchScrollPane.setMinimumSize(new Dimension(10, 10));
		searchPane.setEditable(false);
		
		GlobalVariables.messageDoc = new DefaultStyledDocument();
        GlobalVariables.messagePane = new JTextPane(GlobalVariables.messageDoc);
		//messagePane.setPreferredSize(new Dimension(100, 100));
		GlobalVariables.messagePane.setBackground(customGray);
		GlobalVariables.messagePane.setEditable(false);
		
		
		// Add panels to GlobalVariables.frame
	    GlobalVariables.frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
		GlobalVariables.frame.getContentPane().add(textScrollPane, BorderLayout.CENTER);
		//GlobalVariables.frame.getContentPane().add(searchScrollPane, BorderLayout.LINE_END);
		GlobalVariables.frame.getContentPane().add(GlobalVariables.messagePane, BorderLayout.PAGE_END);
		
		GlobalVariables.frame.pack();
        GlobalVariables.frame.setVisible(true);
		
		// Enable/disable search result panel, controlled by buttonSearch
		GlobalVariables.buttonSearch.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
				if (GlobalVariables.searchVisible){
					GlobalVariables.searchVisible = false;
					GlobalVariables.buttonSearch.setText("Enable search");
					GlobalVariables.frame.getContentPane().remove(searchScrollPane); 
				} else {
					GlobalVariables.searchVisible = true;
					GlobalVariables.buttonSearch.setText("Disable search");
					GlobalVariables.frame.getContentPane().add(searchScrollPane, BorderLayout.LINE_END); 					
				}
				
				GlobalVariables.frame.invalidate();
                GlobalVariables.frame.validate();				
			  } 
		} );
		
		// This button is only for temporary test, will remove in the official version.
		buttonTest.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent evt) { 
				GlobalVariables.frame.setTitle("Test");	
				//System.out.println(GlobalVariables.a );				
			  } 
		} );
		
		// Extract the line string with mouse moving action and display in the message panel
		// Refer to MessageProcess sub module
		GlobalVariables.textPane.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				MessageProcess.textPaneMouseMove(e);
 
			}
		});
		
		
	}
	
	/*
	public JMenuBar createMenuBar() {
		// Create Menu
		JMenuBar menuBar = new JMenuBar();
		
		//Build file menu
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		//New file
        JMenuItem menuItemNew = new JMenuItem("New Notebook");
        menuItemNew.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent evt) {  
                menuItemNewActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemNew);
		
		//Load file
        JMenuItem menuItemLoad = new JMenuItem("Load Notebook");
        menuItemLoad.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent evt) {  
               menuItemLoadActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemLoad);
		
		menuFile.addSeparator();
		
		//Exit system
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent evt) {  
               menuItemExitActionPerformed(evt);  
            }  
        });  
        menuFile.add(menuItemExit);
		
		
		//Build edit menu
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

        		
		return menuBar;
	}
	
	
	private void menuItemNewActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
        
    }  
	
	private void menuItemLoadActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
		
		// Initial parameters
		searchVisible = false; 
		textEditable = false;
		buttonSaveEdit.setText("Edit");
				
		// Open files		
		FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);  
        fd.setVisible(true);  
        String strFile = fd.getDirectory() + fd.getFile(); 		
		String line = null;
		EditDisplay.ProcResult result = new EditDisplay.ProcResult();
        if (strFile != null) {  
            try {  
                textPane.setText("");
				FileReader fileReader = new FileReader(strFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
						// System.out.println(line.length());
						// style = textDoc.getStyle("highlight");
						// textDoc.insertString(textPane.getDocument().getLength(), line+"\n", style);
						if (line.length() > 1){
							result = EditDisplay.procString(line);
							//System.out.println(result.dispStr + "  " + result.returnCode);
						} else {
							result.dispStr = "";
							result.returnCode = 3;
						}
						
						if ( result.returnCode == 2){
							style = textDoc.getStyle("bold");
						} else {
							style = textDoc.getStyle("base");
						}
						
						textDoc.insertString(textPane.getDocument().getLength(), result.dispStr+"\n", style);
								
					}   
				bufferedReader.close();      
            } catch (Exception e) {  
               
            }  
        }  
        
    } 

	private void menuItemExitActionPerformed(ActionEvent evt) {  
        // TODO add your handling code here:  
       System.exit(0);   // Exit the whole system.
    } 
   */	
	
	
	
	public static void main(String[] args) {
        new NoteBook();
	}
	
}

	