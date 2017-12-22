/*
This is for functions reading from file and processing to string
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.util.ArrayList;

public class SymbolProcess{
	
	static int lineNum = 0; // Purely for # symbol. The line with # must be continuous. If not, need to be reset.
	
	// Link string processing, to check whether it includes "|"
	public static ArrayList<String> linkNameDisplayProc(String contents){
		ArrayList<String> result = new ArrayList<String>();
		String strProc = contents;
		String temp1, temp2;
		int index1 = strProc.indexOf("|");
		int index2 = strProc.lastIndexOf("|");
		if (index1 != -1){	// "|" Existing
			result.add(simplifyWhitespace(strProc.substring(0,index1).trim())); 
			result.add(simplifyWhitespace(strProc.substring(index2+1).trim())); 			
		} else {
			result.add(simplifyWhitespace(strProc));
		}
		return result;
	}
	
	
	// Replace multiple continuous whitespace (between different words) with one only
	public static String simplifyWhitespace(String contents){
		String result = contents;
		String stringProc = contents;
		int index = stringProc.indexOf("  ");
		while (index > 0 ){
		  stringProc = stringProc.substring(0,index) + stringProc.substring(index+2);
		  index = stringProc.indexOf("  ");
		}
		result = stringProc;
		return result;
	}
	
	// Used to detect whether the link for the page is existing or not
	// Please note that the link could be webpage (start with http or https), or the link of image (start with image::::).
	// Above these two links, it is always assuming they are existing. The last one is the link of internal page.
	public static Boolean linkExisting(String contents){
		Boolean result = false;
		int k = contents.length();
		if (k > GlobalVariables.fileSymbol.length()) {
			if (contents.substring(0,GlobalVariables.webSymbol.length()).toLowerCase().equals(GlobalVariables.webSymbol.toLowerCase()))  {				
				result = true;
				GlobalVariables.linkNumber = 2;
			} else if (contents.substring(0,GlobalVariables.fileSymbol.length()).toLowerCase().equals(GlobalVariables.fileSymbol.toLowerCase())) {
				result = true;
				GlobalVariables.linkNumber = 3;
			} else {
				result = GlobalVariables.pageListLowerCase.contains(contents.toLowerCase());
				if (result) {
					GlobalVariables.linkNumber = 1;
				} else {
					GlobalVariables.linkNumber = 0;
				}
			}				
		} else if (k > GlobalVariables.webSymbol.length()) {
			if (contents.substring(0,GlobalVariables.webSymbol.length()).toLowerCase().equals(GlobalVariables.webSymbol.toLowerCase())) {
				result = true;
				GlobalVariables.linkNumber = 2;
			} else {
				result = GlobalVariables.pageListLowerCase.contains(contents.toLowerCase());
				if (result) {
					GlobalVariables.linkNumber = 1;
				} else {
					GlobalVariables.linkNumber = 0;
				}
			}
		} else {
			result = GlobalVariables.pageListLowerCase.contains(contents.toLowerCase());
			if (result) {
				GlobalVariables.linkNumber = 1;
			} else {
				GlobalVariables.linkNumber = 0;
			}
		}
		
		return result;
	}
	
	// Used to interpret the level 1 symbol, mainly for "= ", "== ", "=== "
	public static GlobalVariables.InterpDispResult interpSymbolLevel_1(String contents) {
		GlobalVariables.InterpDispResult result = new GlobalVariables.InterpDispResult();
		int index1, index2;
		String stringProc = contents;
		for(int i = 0; i <  GlobalVariables.rows_1 ; i++){
			index1 = stringProc.indexOf(GlobalVariables.symbolArray_1[i][0]);
			if (index1 == 0){				
				index2 = stringProc.indexOf(GlobalVariables.symbolArray_1[i][1]);
				if (index2 > 0){
					result.symbolFind = true;
					result.indexStyle = 10 + i;
					result.dispContent = stringProc.substring(index1+GlobalVariables.symbolArray_1[i][0].length(), index2);
				}
				lineNum = 0;
			}
        }
		return result;
    }
	
	
	
	// Used to interpret the level 2 symbol, mainly for ":", "#", "*"
	public static GlobalVariables.InterpDispResult interpSymbolLevel_2(String contents) {
		GlobalVariables.InterpDispResult result = new GlobalVariables.InterpDispResult();
		int index;
		String stringProc = contents;		
		for(int i = 0; i <  GlobalVariables.rows_2 ; i++){
			index = stringProc.indexOf(GlobalVariables.symbolArray_2[i]);
			if (index == 0){
				result.symbolFind = true;
				result.indexStyle = 20 + i;				
				if (i == 1) {			// for #				
					lineNum = lineNum + 1;
					result.number = lineNum;
					result.dispContent = stringProc.substring(1);					
				} else { // for * and :
					lineNum = 0 ;
					result.number = result.number + 1;
					stringProc = stringProc.substring(1);
					while (stringProc.substring(0,1).equals(GlobalVariables.symbolArray_2[i])){
						stringProc = stringProc.substring(1);
						result.number = result.number + 1;
					}
					result.dispContent = stringProc;	
				}			
			} 
        }		
		if (!result.symbolFind){		// If the line doesn't have these three symbol, reset lineNum
			lineNum = 0 ;
			result.dispContent = stringProc;  // The string will be kept same.
		}
			
		return result;
    }
	
	
	// Used to interpret the level 3 symbol, mainly for "<h>", "<b>", "<i>", "s>", "<x>"
	public static ArrayList<GlobalVariables.InterpDispResult> interpSymbolLevel_3(String contents) {
		ArrayList<GlobalVariables.InterpDispResult> result = new ArrayList<GlobalVariables.InterpDispResult>();
		int index1, index2;
		String stringProc = contents;		
		Boolean symbolExist = true;
		Boolean symbolFind;
		while (symbolExist){
			GlobalVariables.InterpDispResult temp1 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp2 = new GlobalVariables.InterpDispResult();
			symbolFind = false ;
			for(int i = 0; i <  GlobalVariables.rows_3 ; i++){
				if ( !symbolFind){
					index1 = stringProc.indexOf(GlobalVariables.symbolArray_3[i][0]);
					if (index1 != -1){		// Find the first part symbol
						if (index1 != 0){ // something exist before the symbol							
							temp1.dispContent = stringProc.substring(0, index1);
							result.add(temp1);
						}
						index2 = stringProc.indexOf(GlobalVariables.symbolArray_3[i][1]);
						if (index2 != -1) {	// Find the second part symbol
							temp2.indexStyle = 30 + i ;
							temp2.symbolFind = true;
							temp2.dispContent = stringProc.substring(index1 + 3, index2);							
							result.add(temp2);
							stringProc = stringProc.substring(index2 + 4);							
						} else { // Miss the second part symbol
							temp2.indexStyle = 30 + i ;
							temp2.symbolFind = true;
							temp2.dispContent = stringProc.substring(index1 + 3);							
							result.add(temp2);
							symbolExist = false;							
						}
						symbolFind = true ;
					}
				}
			}
			if ( !symbolFind){  // Go through and find no symbol
				temp1.dispContent = stringProc;
				result.add(temp1);
				symbolExist = false;
			}
		}		
		return result;
	}
	
	// Used to interpret the level 4 symbol, mainly for "[" and "]"
	public static ArrayList<GlobalVariables.InterpDispResult> interpSymbolLevel_4(GlobalVariables.InterpDispResult contents) {
		ArrayList<GlobalVariables.InterpDispResult> result = new ArrayList<GlobalVariables.InterpDispResult>();		
		int index1, index2;
		String stringProc = contents.dispContent;	
		String linkString, linkName;
		Boolean symbolExist = true;
		Boolean symbolFind;
		Boolean linkExist = false ;
		ArrayList<String> linkStrs =  new ArrayList<String>();	
		while (symbolExist){
			GlobalVariables.InterpDispResult temp1 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp2 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp3 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp4 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp5 = new GlobalVariables.InterpDispResult();
			symbolFind = false ;
			for(int i = 0; i <  GlobalVariables.rows_4 ; i++){
				if ( !symbolFind){
					index1 = stringProc.indexOf(GlobalVariables.symbolArray_4[i][0]);
					index2 = stringProc.indexOf(GlobalVariables.symbolArray_4[i][1]);
					if ((index1 != -1) && (index2 > index1)){		// Find the symbol pair
						if (index1 != 0){ // something exist before the symbol	
							temp1.indexStyle = contents.indexStyle;
							temp1.dispContent = stringProc.substring(0, index1);
							result.add(temp1);
						}
						
						// For the content between "[" and "]", use *.number to distinguish it
						linkString = stringProc.substring(index1 + 1, index2).trim(); // Get the string between "[" and "]"
						linkStrs = linkNameDisplayProc(linkString);
						
						int k = linkStrs.size();
						
						if (k == 2) {
							temp3.indexStyle = contents.indexStyle;
							temp3.number = 10 ;
							temp3.symbolFind = true;
							temp3.dispContent = linkStrs.get(0);
							linkName =  linkStrs.get(1);
							temp5.indexStyle = contents.indexStyle;
							temp5.number = 20 ;
							temp5.symbolFind = true;
							temp5.dispContent = "|" + linkStrs.get(1);							
						}
						else if (k == 1) {
							temp3.indexStyle = contents.indexStyle;
							temp3.number = 10 ;
							temp3.symbolFind = true;
							temp3.dispContent = linkStrs.get(0);
							linkName =  linkStrs.get(0);							
						} else {
							temp3.indexStyle = contents.indexStyle;
							temp3.number = 10 ;
							temp3.symbolFind = true;
							temp3.dispContent = "";
							linkName =  "";							
						}
						
						linkExist = linkExisting(linkName);
						// Purely for symbol "[", either be hiding (if link existing) or red color (if link not existing)
						temp2.indexStyle = contents.indexStyle;		// Keep the same style
						if (linkExist){
							temp2.number = 20 ;							// Use this to distinguish
						} else {
							temp2.number = 21 ;	
						}
						temp2.symbolFind = true;
						temp2.dispContent = GlobalVariables.symbolArray_4[i][0];							
						result.add(temp2);													
						result.add(temp3);
						if (k == 2) {
							result.add(temp5);
						}
						// It is for "]", the same processing as "["
						temp4.indexStyle = contents.indexStyle;
						temp4.number = temp2.number ;
						temp4.symbolFind = true;
						temp4.dispContent = GlobalVariables.symbolArray_4[i][1];							
						result.add(temp4);						
						stringProc = stringProc.substring(index2 + 1);
						symbolFind = true ;						
					}
				}
			}		
			
			if ( !symbolFind){  // Go through and find no symbol
				temp1.dispContent = stringProc;
				result.add(temp1);
				symbolExist = false;
			}
		}
		
		
		return result;
	}

	
}


