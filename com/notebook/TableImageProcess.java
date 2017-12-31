/*
This is for Table Image Process
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.FileStringProcess;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;


public class TableImageProcess
{
	// Create the table contents according to the table contents
	public static void tableCreation(){
		GlobalVariables.tableImageProc.tableStatus = 0;
		if (GlobalVariables.tableResult.row > 0) {
			GlobalVariables.tableImageProc.tableStatus = 1;
			GlobalVariables.tableResult.columnNames = new String[GlobalVariables.tableResult.column];
			GlobalVariables.tableResult.rowData = new Object[1][GlobalVariables.tableResult.column];
			for (int i = 0; i < GlobalVariables.tableResult.column; i++){
				GlobalVariables.tableResult.columnNames[i] = GlobalVariables.tableImageProc.data.get(i);
			}
			
			if (GlobalVariables.tableResult.row > 1) {
				GlobalVariables.tableImageProc.tableStatus = 2;
				GlobalVariables.tableResult.rowData = new Object[GlobalVariables.tableResult.row-1][GlobalVariables.tableResult.column];
				for (int i = 0; i < GlobalVariables.tableResult.row-1; i++){
					for (int j = 0; j < GlobalVariables.tableResult.column; j++){
						GlobalVariables.tableResult.rowData[i][j] = GlobalVariables.tableImageProc.data.get((i+1)*GlobalVariables.tableResult.column + j);
					}
				}
			}
		}
	}

	public static void tableContentProc(String contents){
		ArrayList<String> contentArray = FileStringProcess.extractLineStrings(contents);	
		ArrayList<String> result = new ArrayList<String>();
		GlobalVariables.tableResult.row = contentArray.size();
		GlobalVariables.tableResult.column = 0;
		GlobalVariables.tableImageProc.data = new ArrayList<String>();
		for (int i = 0; i < contentArray.size(); i++){
			result = separateCharater(contentArray.get(i));
			if (GlobalVariables.tableResult.column < result.size()){
				GlobalVariables.tableResult.column =  result.size();
			}
		}
		for (int i = 0; i < contentArray.size(); i++){
			result = separateCharater(contentArray.get(i));
			while (result.size() < GlobalVariables.tableResult.column){
				result.add("");
			}
			for (int j = 0; j < result.size(); j++){
				GlobalVariables.tableImageProc.data.add(result.get(j));
			}
		}
	}
	
	// Separate one line string into several strings based on special symbol
	// Note: if there is one "/" before this special symbol, this one will be ignored. 
	public static ArrayList<String> separateCharater(String contents) {
		ArrayList<String> result = new ArrayList<String>();
		String combineSymbol = GlobalVariables.tableImageProc.tableKeepSymbol 
					+ GlobalVariables.tableImageProc.tableSepSymbol;
		String stringProc = contents.replace(combineSymbol,GlobalVariables.tableImageProc.subSymbol) ;
		int index2 = stringProc.indexOf(GlobalVariables.tableImageProc.tableSepSymbol);		
		while (index2 != -1) {
			if (index2 == 0) {
				result.add("");
			} else {
				result.add(stringProc.substring(0,index2).replace(GlobalVariables.tableImageProc.subSymbol,GlobalVariables.tableImageProc.tableSepSymbol));
			}
			if (index2 == stringProc.length()-1){
				stringProc = "";
			} else {
				stringProc = stringProc.substring(index2+1);
			}
			index2 = stringProc.indexOf(GlobalVariables.tableImageProc.tableSepSymbol);
		}
		result.add(stringProc.replace(GlobalVariables.tableImageProc.subSymbol,GlobalVariables.tableImageProc.tableSepSymbol));				
		
		return result;
		
	}
	
	public static JScrollPane getTableComponent() {
		// JTable table = new JTable(data, columnNames);
		JTable table = new JTable(GlobalVariables.tableResult.rowData, GlobalVariables.tableResult.columnNames);
        Dimension d = table.getPreferredSize();
        d.width = 100;
        table.setPreferredScrollableViewportSize(d);
        return new JScrollPane(table);
    }
	
	public static JScrollPane getContent() {
        return new JScrollPane(GlobalVariables.textPane);
    }
	
}

	


