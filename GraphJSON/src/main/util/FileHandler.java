package main.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
	
	public static String readFile(File file) throws IOException {

	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
	
	public static void saveStringToFile(File file, String data) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(file.getPath());
		out.println(data);
		out.close();
	}
	
	public static String getStringFromClipboard() {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			String result = (String) clipboard.getData(DataFlavor.stringFlavor);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
 	
}
