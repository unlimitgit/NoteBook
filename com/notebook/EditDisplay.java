/*
This is for EditDisplay main functions
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.CreateStyles;
import com.notebook.FileStringProcess;
import com.notebook.SymbolProcess;

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

public class EditDisplay
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
		
	}
	
	// Create class for distinguish different string format for display
	public static class ProcResult{
		public int returnCode;
		public String dispStr;    
	}
	
	// Process string based on special characteristics
	public static ProcResult procString(String content) {
		ProcResult result = new ProcResult();
		result.dispStr = content;
		
		
		if (GlobalVariables.searchVisible) {
			if (content.toLowerCase().equals(GlobalVariables.searchKeyWord.getText().toLowerCase())) {
				result.returnCode = 4;
			} else {
				result.returnCode = 1;
			}
		} else {
			if (content.substring(0,1).compareTo("[") == 0){
				result.returnCode = 2;
				result.dispStr = removeCharAt(result.dispStr,"[");
				result.dispStr = removeCharAt(result.dispStr,"]");
			} else {
				result.returnCode = 1;
			}
		}
		
		return result;
	
	}
	
	// After locating the special characteritics (used for different )
	private static String removeCharAt(String s, String h) {
		int index = s.indexOf(h);
		String result = s;
		while (index != -1){
			if (index != (result.length()-1)){
				result = result.substring(0,index) + result.substring(index+1);
			} else {
				result = result.substring(0,index);
			}
		index = result.indexOf(h);
		}	
		return result;			
	}
   
    // Load the raw file format for editing purpose
    public static String extractFileProc(String fileName) {
		String line = null;
		String content = "";
		if (fileName != null) {  
			try {  
				GlobalVariables.textPane.setText("");
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
					content = content + line + "\n";							
				}   
				// GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				// GlobalVariables.textDoc.insertString(0, content, GlobalVariables.style);					
				bufferedReader.close();      
			} catch (Exception e) {  
				System.out.println("open fail");  
			}  
		}
		return content;
				
	}
	
	// Used to initial the display in textPane
	public static void textPaneTitleDisplay(String contents){
		setDisplayMode();
		GlobalVariables.textPane.setText("");   //Clear all the contents
		try {
			CreateStyles.setStyle(100);
			GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), contents, GlobalVariables.style);
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
			
			// Do level 1 symbol processing. It has top priority. Also, it will exclude other symbol.
			result_1 =  SymbolProcess.interpSymbolLevel_1(stringLine.get(i));
			// Leve 1 symbol is found.
			if (result_1.symbolFind){
				try {
					CreateStyles.setStyle(result_1.indexStyle);
					GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result_1.dispContent+"\n", GlobalVariables.style);
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
								//GlobalVariables.style = GlobalVariables.textDoc.getStyle("hide");
								CreateStyles.setStyle(result_3.get(k).indexStyle);
								StyleConstants.setForeground(GlobalVariables.style, Color.red);
							} else if (result_4.get(m).number == 10){
								CreateStyles.setStyle(result_3.get(k).indexStyle);
								StyleConstants.setForeground(GlobalVariables.style, Color.blue);
							} else {
								CreateStyles.setStyle(result_3.get(k).indexStyle);
								StyleConstants.setForeground(GlobalVariables.style, Color.black);
								
							}
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result_4.get(m).dispContent, GlobalVariables.style);
							
						} catch (Exception e) {  
						}
							 
					}
					
				}
				
				try {
					GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), "\n", GlobalVariables.style);		// Add change line 
				} catch (Exception e) {
				}
				
				
			}
			
		}
	}
		
	
	
	// Dispaly contents according to format
    public static void loadFileDisplayProc(String fileName) {
		
		// GlobalVariables.textEditable = false;
		// GlobalVariables.textPane.setEditable(false);
		// GlobalVariables.buttonSaveEdit.setText("Edit");
		// GlobalVariables.textPane.setBackground(GlobalVariables.textDisplayColor);
		
		setDisplayMode();
		
		
		String line = null;
		ProcResult result = new ProcResult();
        if (fileName != null) {  
            try {  
                GlobalVariables.textPane.setText("");
				
				// Display file name in the begining
				String fileDisp = fileName.substring(fileName.lastIndexOf('\\')+1, fileName.lastIndexOf('.'));
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("title");
				GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), fileDisp+"\n\n", GlobalVariables.style);
				
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
					if (line.length() > 0){
							if (GlobalVariables.searchVisible) {
								String str1 = line;
								String str2 = GlobalVariables.searchKeyWord.getText();
								int pos = str1.toLowerCase().indexOf(str2.toLowerCase());
								while (pos != -1) {			// Find keyword
									if (pos == 0) {			// Keyword is in the beginning
										result = procString(str1.substring(0, str2.length()));
										CreateStyles.setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str1.substring(str2.length(), str1.length());
										pos = str1.toLowerCase().indexOf(str2.toLowerCase());
									} else if(pos == str1.length()- str2.length()) { // Keyword is int the end
										result = procString(str1.substring(0, pos));
										CreateStyles.setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str2;
										pos = -1;
									} else {  // Keyword is in the middle
										result = procString(str1.substring(0, pos));
										CreateStyles.setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										result = procString(str1.substring(pos, pos+str2.length()));
										CreateStyles.setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str1.substring(pos+str2.length(), str1.length());
										pos = str1.toLowerCase().indexOf(str2.toLowerCase());
									}
									
								}
								
								result = procString(str1);
								
								CreateStyles.setStyle(result.returnCode);
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
								
								
							} else {
								
								result = procString(line);
								CreateStyles.setStyle(result.returnCode);
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
							}
							
						} else {
							result.dispStr = "";
							result.returnCode = 1;
							CreateStyles.setStyle(result.returnCode);
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);

						}
					} 

				GlobalVariables.frame.validate();
				bufferedReader.close();      
            } catch (Exception e) {  
               
            }  
        }  
			
	}

	
	
	// Save the contents in the EditDisplay Panel into file
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


