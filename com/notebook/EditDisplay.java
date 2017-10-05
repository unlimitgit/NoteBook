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
		
		if (content.substring(0,1).compareTo("[") == 0){
			result.returnCode = 2;
			result.dispStr = removeCharAt(result.dispStr,"[");
			result.dispStr = removeCharAt(result.dispStr,"]");
		} else {
			result.returnCode = 1;
			result.dispStr = "Disp";
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
							result = procString(line);
							//System.out.println(result.dispStr + "  " + result.returnCode);
						} else {
							result.dispStr = "";
							result.returnCode = 3;
						}
						
						if ( result.returnCode == 2){
							GlobalVariables.style = GlobalVariables.textDoc.getStyle("bold");
						} else {
							GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
						}
						
						GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);
								
					}   
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


