/*
This is for creating styles for documents
*/  

package com.notebook;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;


public class CreateStyles
{
	
	// Generate styles for displaying
	public static Style CreateStyles(StyledDocument doc) {
		
		Style style;
		
		Style baseStyle = doc.addStyle("base", null);
        StyleConstants.setFontFamily(baseStyle, "Lucida Sans Unicode");
        StyleConstants.setFontSize(baseStyle, 18);
        StyleConstants.setFirstLineIndent(baseStyle, 20f);
        StyleConstants.setLeftIndent(baseStyle, 10f);
		StyleConstants.setForeground(baseStyle, Color.black);
 
        style = doc.addStyle("base", baseStyle);
		
		
		style = doc.addStyle("header_1", baseStyle);
		StyleConstants.setFontSize(style, 42);
		StyleConstants.setBold(style, true);
		
		style = doc.addStyle("header_2", baseStyle);
		StyleConstants.setFontSize(style, 36);
		StyleConstants.setBold(style, true);
		
		style = doc.addStyle("header_3", baseStyle);
		StyleConstants.setFontSize(style, 30);
		StyleConstants.setBold(style, true);
		
		style = doc.addStyle("inlineheader", baseStyle);
		StyleConstants.setFontSize(style, 32);
		StyleConstants.setBold(style, true);
		
		style = doc.addStyle("bold", baseStyle);
        StyleConstants.setBold(style, true);
		
		style = doc.addStyle("italic", baseStyle);
        StyleConstants.setItalic(style, true);
		
		style = doc.addStyle("smalltext", baseStyle);
        StyleConstants.setFontSize(style, 12);
		
		style = doc.addStyle("strikeout", baseStyle);
        StyleConstants.setStrikeThrough(style, true);
	
		// Used for highlight for searching with keyword
        style = doc.addStyle("highlight", baseStyle);
        StyleConstants.setForeground(style, Color.white);
        StyleConstants.setBackground(style, Color.red);
		
		// Bullet
		style = doc.addStyle("bullet", baseStyle);
		StyleConstants.setFontSize(style, 36);
		
		//Title
		style = doc.addStyle("title", baseStyle);
		StyleConstants.setFontSize(style, 36);
        StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.blue);
		
		//Hiding
		style = doc.addStyle("hide", baseStyle);
		StyleConstants.setFontSize(style, 0);
		
		return style;
		
	}
	
	public static void setStyle(int styleCode){
		switch (styleCode){
			case 10:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("header_1");
				break;
			case 11:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("header_2");
				break;
			case 12:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("header_3");
				break;
			case 20:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("bullet");
				break;
			case 30:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("inlineheader");
				break;
			case 31:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("bold");
				break;
			case 32:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("italic");
				break;
			case 33:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("smalltext");
				break;
			case 34:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("strikeout");
				break;
			default:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				break;			
		}
	}
		
		
}


