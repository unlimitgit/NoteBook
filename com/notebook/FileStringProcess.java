/*
This is for functions reading from file and processing to string
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class FileStringProcess{
	
	//Extract string lines from the contents of page
	public static ArrayList<String> extractLineStrings(String contents) {
		ArrayList<String> result = new ArrayList<String>();
		String lineSymbol = "\n";
		String stringProc = contents;
		String line;
		int index = stringProc.indexOf(lineSymbol);
		while (index != -1){
			if (index == 0){
				line = "";			// Empty line
			} else {
				line = stringProc.substring(0,index); // Extract the line contents
			}
			result.add(line);
			stringProc = stringProc.substring(index+1);
			index = stringProc.indexOf(lineSymbol);			
		}
		return result;
	}
		
	//Extract the page list (name of root/child) from the contents of the  file
	public static ArrayList<String> extractPageList(String contents) {
		ArrayList<String> result = new ArrayList<String>();
		String pageSymbol = GlobalVariables.pageTitle;
		String stringProc = contents;
		String line, pageName;
		int index = stringProc.indexOf(pageSymbol);
		while (index != -1){
			stringProc = stringProc.substring(index);
			line = stringProc.substring(0, stringProc.indexOf("\n"));		//Extract the line with page name
			//pageName = line.substring(pageSymbol.length(), line.lastIndexOf(".")); // Extract the page name out
			pageName = line.substring(pageSymbol.length()); // Extract the page name out
			result.add(pageName);
			stringProc = stringProc.substring(stringProc.indexOf("\n"));	// Substract string for next page processing
			index = stringProc.indexOf(pageSymbol);
		}
		return result;	
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

	
}


