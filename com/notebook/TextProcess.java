/*
This is for TextProcess main functions
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.CreateStyles;
import com.notebook.FileStringProcess;
import com.notebook.SymbolProcess;
import com.notebook.TableImageProcess;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.text.StyleConstants;



public class TextProcess
{
	
	// Set to display mode
	public static void setDisplayMode() {
		GlobalVariables.textEditable = false;
		GlobalVariables.textPane.setEditable(false);
		GlobalVariables.buttonSaveEdit.setText("Edit");
		GlobalVariables.textPane.setBackground(GlobalVariables.textDisplayColor);
	}
	
	// Set to edit mode
	public static void setEditMode() {
		GlobalVariables.textEditable = true;
		GlobalVariables.textPane.setEditable(true);
		GlobalVariables.buttonSaveEdit.setText("Save");
		GlobalVariables.textPane.setBackground(GlobalVariables.textEditColor);
	}
	
	
	// Used to initial the display in textPane
	public static void textPaneTitleDisplay(String contents){
		setDisplayMode();
		GlobalVariables.textPane.setText("");   //Clear all the contents
		try {
			CreateStyles.setStyle(100);
			GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), contents + GlobalVariables.newline, GlobalVariables.style);
		} catch (Exception e) {  
				   
		}
	}
	
	// Used to display the contents in the textPane
	public static void textPaneDisplay(String contents){
		ArrayList<String> stringLine = new ArrayList<String>();
		stringLine =  FileStringProcess.extractLineStrings(contents);		//Separate the whole contents line by line
		// Process contents line by line
		GlobalVariables.InterpDispResult result_1 = new GlobalVariables.InterpDispResult();
		GlobalVariables.InterpDispResult result_2 = new GlobalVariables.InterpDispResult();
		ArrayList<GlobalVariables.InterpDispResult> result_3 = new ArrayList<GlobalVariables.InterpDispResult>();
		ArrayList<GlobalVariables.InterpDispResult> result_4 = new ArrayList<GlobalVariables.InterpDispResult>();
		for (int i=0; i<stringLine.size(); i++ ){
			//Do level 0 processing to locate table or image first
			SymbolProcess.interpSymbolLevel_0(stringLine.get(i));
			if (GlobalVariables.tableImageProc.isTable || GlobalVariables.tableImageProc.isImage) { // Find either table or image
				if (GlobalVariables.tableImageProc.isImage){
					// Display the image
					GlobalVariables.tableImageProc.isImage = false;
					GlobalVariables.style = CreateStyles.CreateStyles(GlobalVariables.textDoc);
					if (GlobalVariables.tableImageProc.imageStatus == 0) {
						//CreateStyles.setStyle(100);
						try {
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "\n",GlobalVariables.textDoc.getStyle("image"));
						} catch (Exception e) {
						}
						
					} else if(GlobalVariables.tableImageProc.imageStatus == 1) {
						CreateStyles.setStyle(0);
						try {
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "This is not image file.\n",GlobalVariables.style);
						} catch (Exception e) {
						}
						
					} else {
						CreateStyles.setStyle(0);
						try {
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "File not exist.\n",GlobalVariables.style);
						} catch (Exception e) {
						}
						
					}
					
				} else {
					if (GlobalVariables.tableImageProc.isTableReady) { // The end of the table
						//tableFinalContents = tableContents.substring(0,tableContents.length()-1);
						GlobalVariables.tableImageProc.tableFinalContents = GlobalVariables.tableImageProc.tableContents;						
						TableImageProcess.tableContentProc(GlobalVariables.tableImageProc.tableFinalContents);	
						TableImageProcess.tableCreation();
						if (GlobalVariables.tableImageProc.tableStatus > 0) {
							GlobalVariables.style = CreateStyles.CreateStyles(GlobalVariables.textDoc); // Refresh the table contents
							try {
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "\n", GlobalVariables.textDoc.getStyle("table"));
							} catch (Exception e) {
							}
						}
						GlobalVariables.tableImageProc.isTable = false;
						GlobalVariables.tableImageProc.isTableReady = false;						
					}
				}
			} else {
				// Do level 1 symbol processing. It has top priority. Also, it will exclude other symbol.
				result_1 =  SymbolProcess.interpSymbolLevel_1(stringLine.get(i));
				// Leve 1 symbol is found.
				if (result_1.symbolFind){
					try {
						CreateStyles.setStyle(result_1.indexStyle);
						GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result_1.dispContent+GlobalVariables.newline, GlobalVariables.style);
					} catch (Exception e) {  
					   
					} 				
				} else {
					// Leve 1 symbol is not found. Will go furhter processing. It will start level 2 processing.
					result_2 = SymbolProcess.interpSymbolLevel_2(stringLine.get(i));
					if (result_2.symbolFind){
						try {
							CreateStyles.setStyle(result_2.indexStyle);
							if (result_2.indexStyle == 20){
								switch (result_2.number){
									case 1:
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "\u2022", GlobalVariables.style);
										break;
									case 2:
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "    \u25b8", GlobalVariables.style);
										break;
									case 3:
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "        \u25aa", GlobalVariables.style);
										break;
									default:	
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "            \u2043", GlobalVariables.style);
										break;
								}	
							} else if (result_2.indexStyle == 21) {
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), Integer.toString(result_2.number) + ", ", GlobalVariables.style); // Number the string
							} else if (result_2.indexStyle == 22) {
								for (int j=0; j<result_2.number; j++){
									GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "\t", GlobalVariables.style); // Tab the string
								}
							}
						} catch (Exception e) {  
						   
						} 
						
					}
					
					// Then level 3 processing
					result_3 = SymbolProcess.interpSymbolLevel_3(result_2.dispContent);
					for (int k=0; k<result_3.size(); k++){
						
						// Level 4 processing.
						
						result_4 = SymbolProcess.interpSymbolLevel_4(result_3.get(k));					
						for (int m=0; m<result_4.size(); m++){
							try {
								if (result_4.get(m).number == 20){
									GlobalVariables.style = GlobalVariables.textDoc.getStyle("hide");
									
								} else if (result_4.get(m).number == 21) {
									CreateStyles.setStyle(result_3.get(k).indexStyle);
									StyleConstants.setForeground(GlobalVariables.style, Color.red);
									
									
								}else if (result_4.get(m).number == 10){
									CreateStyles.setStyle(result_3.get(k).indexStyle);
									StyleConstants.setForeground(GlobalVariables.style, Color.blue);
								} else {
									
									CreateStyles.setStyle(result_3.get(k).indexStyle);
									// if (result_3.get(k).indexStyle != 31) {
										// StyleConstants.setForeground(GlobalVariables.style, Color.black);
									// }
									
									
								}
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result_4.get(m).dispContent, GlobalVariables.style);
								StyleConstants.setForeground(GlobalVariables.style, Color.black); // Go back default color
							} catch (Exception e) {  
							}
								 
						}
						
					}
					
					try {
						GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), GlobalVariables.newline, GlobalVariables.style);		// Add change line 
					} catch (Exception e) {
					}
					
					
				}
			}
			
		}
	}
		
	
	// Used to display the contents in the textPane which is in edit mode
	public static void textPaneEdit(String contents){
		setEditMode();
		GlobalVariables.textPane.setText("");   //Clear all the contents
		try {
			CreateStyles.setStyle(0);
			GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), contents, GlobalVariables.style);
		} catch (Exception e) {  
				   
		}
		//GlobalVariables.textPane.setText(contents);   //Set the new contents for further edit		
	}
	
	
	// Save the contents in the TextProcess Panel into file
	public static void saveTextPaneProc(String fileName, String content) {
		//String content = GlobalVariables.textPane.getDocument().getText(0, GlobalVariables.textPane.getDocument().getLength());
		if (fileName != null) {
			try {
				FileWriter fileWriter =
					new FileWriter(fileName);
					
				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);

				// append a newline character.
				bufferedWriter.write(content);
			   
				// Always close files.
				bufferedWriter.close();
			}
			catch(IOException ex) {
				System.out.println(
					"Error writing to file '"
					+ fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}
		}		
	}
}


