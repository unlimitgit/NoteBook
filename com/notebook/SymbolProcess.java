/*
This is for functions reading from file and processing to string
*/  

package com.notebook;

import com.notebook.GlobalVariables;

import java.util.ArrayList;

public class SymbolProcess{
	
	static int lineNum = 0; // Purely for # symbol. The line with # must be continous. If not, need to be reset.
	
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
		Boolean symbolExist = true;
		Boolean symbolFind;
		while (symbolExist){
			GlobalVariables.InterpDispResult temp1 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp2 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp3 = new GlobalVariables.InterpDispResult();
			GlobalVariables.InterpDispResult temp4 = new GlobalVariables.InterpDispResult();
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
						// Purely for symbol "[", either be hiding (if link existing) or red color (if link not existing)
						temp2.indexStyle = contents.indexStyle;		// Keep the same styple
						temp2.number = 20 ;							// Use this to distinguish
						temp2.symbolFind = true;
						temp2.dispContent = GlobalVariables.symbolArray_4[i][0];							
						result.add(temp2);						
						// For the content between "[" and "]", use *.number to distinguish it
						temp3.indexStyle = contents.indexStyle;
						temp3.number = 10 ;
						temp3.symbolFind = true;
						temp3.dispContent = stringProc.substring(index1 + 1, index2);							
						result.add(temp3);						
						// It is for "]", the same processing as "["
						temp4.indexStyle = contents.indexStyle;
						temp4.number = 20 ;
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


