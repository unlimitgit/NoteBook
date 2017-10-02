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
 
        style = doc.addStyle("base", baseStyle);
		// StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("bold", baseStyle);
        StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.red);
 
        style = doc.addStyle("italic", baseStyle);
        StyleConstants.setItalic(style, true);
 
        style = doc.addStyle("blue", baseStyle);
        StyleConstants.setForeground(style, Color.blue);
 
        style = doc.addStyle("underline", baseStyle);
        StyleConstants.setUnderline(style, true);
 
        style = doc.addStyle("green", baseStyle);
        StyleConstants.setForeground(style, Color.green.darker());
        StyleConstants.setUnderline(style, true);
 
        style = doc.addStyle("highlight", baseStyle);
        StyleConstants.setForeground(style, Color.yellow);
        StyleConstants.setBackground(style, Color.black);
		
		return style;
		
	}
		
		
}


