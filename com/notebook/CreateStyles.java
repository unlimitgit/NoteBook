/*
This is for creating styles for documents
*/  

package com.notebook;

import com.notebook.GlobalVariables;
import com.notebook.TableImageProcess;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.Image;

import java.awt.Color;


public class CreateStyles
{
	
	// Generate styles for displaying
	public static Style CreateStyles(StyledDocument doc) {
		
		Style style;
		
		Style baseStyle = doc.addStyle("base", null);
        //StyleConstants.setFontFamily(baseStyle, "Lucida Sans Unicode");
		StyleConstants.setFontFamily(baseStyle, "Arial");
        StyleConstants.setFontSize(baseStyle, 14);
        StyleConstants.setFirstLineIndent(baseStyle, 20f);
        StyleConstants.setLeftIndent(baseStyle, 10f);
			
 
        style = doc.addStyle("base", baseStyle);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("table", null);
        StyleConstants.setComponent(style, TableImageProcess.getTableComponent());
		
		style = doc.addStyle("image", baseStyle);
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
		File tmpDir = new File(GlobalVariables.tableImageProc.imageFileName);
		boolean exists = tmpDir.exists();
		if (exists) {
			ImageIcon imageIcon = new ImageIcon(GlobalVariables.tableImageProc.imageFileName);
			if (imageIcon != null) {
				
				int x = imageIcon.getIconWidth();
				int y = imageIcon.getIconHeight();
				
				if ( x > 800) {
					y = (int) (y*800/x + 0.5);
					x = 800; 
					Image image = imageIcon.getImage();// transform it
					Image newImg = image.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
					imageIcon = new ImageIcon(newImg);  // transform it back
				}
								
				GlobalVariables.debugDisplay.setText(Integer.toString(x) + ',' + Integer.toString(y));
				
				StyleConstants.setIcon(style, imageIcon);
				GlobalVariables.tableImageProc.imageStatus = 0;
				
			} else {
				GlobalVariables.tableImageProc.imageStatus = 1;
			}
		} else {
			GlobalVariables.tableImageProc.imageStatus = 2;
		}
		
		
		style = doc.addStyle("header_1", baseStyle);
		StyleConstants.setFontSize(style, 32);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("header_2", baseStyle);
		StyleConstants.setFontSize(style, 26);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("header_3", baseStyle);
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("inlineheader", baseStyle);
		StyleConstants.setFontSize(style, 28);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("bold", baseStyle);
        StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.blue);
		
		style = doc.addStyle("italic", baseStyle);
        StyleConstants.setItalic(style, true);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("smalltext", baseStyle);
        StyleConstants.setFontSize(style, 8);
		StyleConstants.setForeground(style, Color.black);
		
		style = doc.addStyle("strikeout", baseStyle);
        StyleConstants.setStrikeThrough(style, true);
		StyleConstants.setForeground(style, Color.black);
	
		// Used for highlight for searching with keyword
        style = doc.addStyle("highlight", baseStyle);
        StyleConstants.setForeground(style, Color.white);
        StyleConstants.setBackground(style, Color.red);
		
		// Bullet
		style = doc.addStyle("bullet", baseStyle);
		StyleConstants.setFontSize(style, 10);
		StyleConstants.setForeground(style, Color.black);
		
		//Title
		style = doc.addStyle("title", baseStyle);
		StyleConstants.setFontSize(style, 40);
        StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.blue);
		
		//Hiding
		style = doc.addStyle("hide", baseStyle);
		StyleConstants.setFontSize(style, 0);
		
		return style;
		
	}
	
	public static Style CreateSearchStyles(StyledDocument doc) {
		
		Style style;
		
		Style baseStyle = doc.addStyle("base", null);
		
		style = doc.addStyle("bold", null);
		StyleConstants.setFontSize(style, 10);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.blue);
       		
		//Hiding
		style = doc.addStyle("hide", baseStyle);
		StyleConstants.setFontSize(style, 0);
		
		return style;
		
	}
	
	public static void setTextDocStyle(int styleCode){
		switch (styleCode){
			case 100:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("title");
				break;
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
			case 0:
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				break;
			default:	
				GlobalVariables.style = GlobalVariables.textDoc.getStyle("base");
				break;			
		}
	}
	
		
}


