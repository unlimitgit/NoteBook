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
	
	// Read out the contents in the file and convert to string
	public static String readFileContents(String fileName) {
		String result = new String();
		String line = null;
		try { 
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 
			while((line = bufferedReader.readLine()) != null) {
				result = result + line + "\n";
			}		
		} catch (Exception e) {  

		} 	
		return result;	 
	}
	
	// Separate the contents in the whole file into three parts based on the symbol for root/child page.
	// Please note the contents will include the page symbol.
	// Part 0: contents (without symbol line) for the designated root/child page.
	// Part 1: contents (with symbol line) before the page symbol. It will be empty if the designated page is root page.
	// Part 2: contents (with symbol line) for the designated root/child page.
	// Part 3: contents (with symbol line) after the designaged page. It will be empty if the designated page is the last one in the file.
	public static String[] separatePageContents(String[] args) {
		String[] result = {"", "","" , ""};
		String fileContents = new String(); 
		String tempResult = new String(); 
		String pageSymbol = new String();
		String commonSymbol = new String();
		fileContents = args[0];
		pageSymbol = args[1];
		commonSymbol = args[2];
		int symbolLen = commonSymbol.length();
		int index = fileContents.indexOf(pageSymbol);
		if (index >= 0){
			if (index > 0) {
				result[1] = fileContents.substring(0, index-1);
			}			
			tempResult = fileContents.substring(index+symbolLen);
			int index1 = tempResult.indexOf(commonSymbol);
			if (index1 != -1){
				result[2] = commonSymbol + tempResult.substring(0, index1-1);
				result[3] = tempResult.substring(index1);
			} else {
				result[2] = commonSymbol + tempResult;
			}
			
			result[0] = result[2].substring(result[2].indexOf("\n")+1);
		}
		return result;	
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
				
				// Display file name in the begining
				String fileDisp = fileName.substring(fileName.lastIndexOf('\\')+1, fileName.lastIndexOf('.'));
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("title");
				GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), fileDisp+"\n\n", GlobalVariables.style);
				
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				while((line = bufferedReader.readLine()) != null) {
					if (line.length() > 1){
							if (GlobalVariables.searchVisible) {
								String str1 = line;
								String str2 = GlobalVariables.searchKeyWord.getText();
								int pos = str1.toLowerCase().indexOf(str2.toLowerCase());
								while (pos != -1) {			// Find keyword
									if (pos == 0) {			// Keyword is in the beginning
										result = procString(str1.substring(0, str2.length()));
										setStyle(result.returnCode);
										GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr, GlobalVariables.style);
										str1 = str1.substring(str2.length(), str1.length());
										pos = str1.toLowerCase().indexOf(str2.toLowerCase());
									} else if(pos == str1.length()- str2.length()) { // Keyword is int the end
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
							
						} else {
							result.dispStr = "";
							result.returnCode = 1;
							setStyle(result.returnCode);
							GlobalVariables.textDoc.insertString(GlobalVariables.textPane.getDocument().getLength(), result.dispStr+"\n", GlobalVariables.style);

						}
					} 

				GlobalVariables.frame.validate();
				bufferedReader.close();      
            } catch (Exception e) {  
               
            }  
        }  
			
	}
	
	private static void contentDisplayProc(String content){
		
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


