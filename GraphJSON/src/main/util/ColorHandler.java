package main.util;

import java.awt.Color;

public class ColorHandler {
	
	public static String colorToRgb(Color color) {
		return String.format("%d, %d, %d",
		        (color.getRed()),
		        (color.getGreen()),
		        (color.getBlue()));
	}
	
	/**
	 * 
	 * @param colorStr e.g. "#FFFFFF"
	 * @return 
	 */
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public static String colorToHex(Color color) {
		return "#"+Integer.toHexString(color.getRGB()).substring(2);
	}
}
