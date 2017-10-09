/*
This is for EditDisplay main functions
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class EditDisplay
{
	
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
	
		
	// Dispaly contents according to format
    public static void loadFileDisplayProc(String fileName) {
		
		GlobalVariables.textEditable = false;
		GlobalVariables.textPane.setEditable(false);
		GlobalVariables.buttonSaveEdit.setText("Edit");
		GlobalVariables.textPane.setBackground(GlobalVariables.textDisplayColor);
		
		String line = null;
		ProcResult result = new ProcResult();
        if (fileName != null) {  
            try {  
                GlobalVariables.textPane.setText("");
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
						// System.out.println(line.length());
						// style = textDoc.getStyle("highlight");
						// textDoc.insertString(textPane.getDocument().getLength(), line+"\n", style);
						if (line.length() > 1){
							if (GlobalVariables.searchVisible) {
								String str1 = line;
								String str2 = GlobalVariables.searchKeyWord.getText();
								int pos = str1.toLowerCase().indexOf(str2.toLowerCase());
								while (pos != -1) {			// Find keyword
									if (pos == 0) {			// Keyword is in the beginning
										// System.out.println(1);
										// System.out.println(str1.substring(0, str2.length()));
										result = procString(str1.substring(0, str2.length()));
										setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str1.substring(str2.length(), str1.length());
										pos = str1.toLowerCase().indexOf(str2.toLowerCase());
									} else if(pos == str1.length()- str2.length()) { // Keyword is int the end
										// System.out.println(2);
										// System.out.println(str1.substring(0, pos));
										result = procString(str1.substring(0, pos));
										setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str2;
										pos = -1;
									} else {  // Keyword is in the middle
										result = procString(str1.substring(0, pos));
										setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										result = procString(str1.substring(pos, pos+str2.length()));
										setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str1.substring(pos+str2.length(), str1.length());
										pos = str1.toLowerCase().indexOf(str2.toLowerCase());
									}
									
								}
								
								result = procString(str1);
								setStyle(result.returnCode);
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
								
								
							} else {
								result = procString(line);
								setStyle(result.returnCode);
								GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
							}
							// result = procString(line);
							//System.out.println(result.dispStr + "  " + result.returnCode);
						} else {
							result.dispStr = "";
							result.returnCode = 1;
							setStyle(result.returnCode);
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
								
								
					
						}
						
						
								
					}   
				bufferedReader.close();      
            } catch (Exception e) {  
               
            }  
        }  
			
	}
	
	private static void setStyle(int styleCode){
		switch (styleCode){
			case 1:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				break;
			case 2:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("bold");
				break;
			case 4:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("highlight");
				break;
			default:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				break;
			
			
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


