/*
This is for functions reading from file and processing to string
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

import java.nio.charset.Charset;

public class FileStringProcess{
	
	//Update the contents to the notebook file.
	public static void saveToNoteFile(){
		String fileContents = "";		
		for (int i=0; i<GlobalVariables.pageList.size(); i++ ){
			fileContents = fileContents + GlobalVariables.pageTitle + GlobalVariables.pageList.get(i)
							+ GlobalVariables.newline + GlobalVariables.pageContents.get(i);	
			if (GlobalVariables.pageList.size() > 1) {
				if (i < GlobalVariables.pageList.size()-1) {
					fileContents = fileContents + GlobalVariables.newline;
				}
			}
		}
		//System.out.println(GlobalVariables.pageContents.get(0));
		//System.out.println("file length:" + GlobalVariables.pageContents.get(0).length());
		for (int i = 0; i < GlobalVariables.pageContents.get(0).length(); i++){
			//System.out.println(GlobalVariables.pageContents.get(0).codePointAt(i));
		}
		BufferedWriter bWriter = null;
		try {  
			bWriter = new BufferedWriter(new FileWriter(new File(GlobalVariables.fileName)));  
			bWriter.write(fileContents); 
			bWriter.flush();	
			bWriter.close();  
			//              Thread.sleep(1000);  
		} catch (Exception e) {  
			  
		}  
		
	}
	
	//Extract string lines from the contents of page
	public static ArrayList<String> extractLineStrings(String contents) {
		ArrayList<String> result = new ArrayList<String>();
		String lineSymbol = GlobalVariables.newline;
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
		if (!stringProc.isEmpty()){
			result.add(stringProc);
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
			line = stringProc.substring(0, stringProc.indexOf(GlobalVariables.newline));		//Extract the line with page name
			pageName = line.substring(pageSymbol.length()).trim(); // Extract the page name out
			//pageName = line.substring(pageSymbol.length()).toLowerCase(); // Extract the page name out
			result.add(pageName);
			stringProc = stringProc.substring(stringProc.indexOf(GlobalVariables.newline));	// Substract string for next page processing
			index = stringProc.indexOf(pageSymbol);
		}
		return result;	
	}
	
	// Convert ArrayList String to lower case
	public static ArrayList<String> convertArrayStringLowerCase(ArrayList<String> contents) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i=0; i<contents.size(); i++ ){
			result.add(contents.get(i).toLowerCase());
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
				result = result + line + GlobalVariables.newline;
			}		
		} catch (Exception e) {  

		} 	
		return result;	 
	}
	
	// Separate the contents in the whole file into parts based on page.
	public static ArrayList<String> extractPageContents(String contents) {
		ArrayList<String> result = new ArrayList<String>();
		String pageSymbol = GlobalVariables.pageTitle;
		String stringProc = contents;
		String pageContent;
		int index = stringProc.indexOf(pageSymbol);
		while (index != -1){
			stringProc = stringProc.substring(index+pageSymbol.length());	
			index = stringProc.indexOf(pageSymbol);
			if(index != -1) {   // Not last page
				pageContent = stringProc.substring(stringProc.indexOf(GlobalVariables.newline)+1, index-1);	
			} else { // Last page
				pageContent = stringProc.substring(stringProc.indexOf(GlobalVariables.newline)+1);
			}
			result.add(pageContent);
		}
		return result;	
	}

	
}


