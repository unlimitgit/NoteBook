package com.notebook;

public class EditDisplay
{
	
	public static class ProcResult{
		public int returnCode;
		public String dispStr;    // etc
	}
	
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
}


