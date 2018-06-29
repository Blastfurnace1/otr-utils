package com.blastfurnace.otr.util;

import java.io.*;

import javax.swing.filechooser.FileSystemView;


// TODO : clean this up
public class FileUtils {
	
	private static final String DEFAULT_DISC_NAME ="UNNAMED_DISC";
	
	/** File to perform operations on. */
  	private File userFile;
  	/** Get the file Name. */
  	public String getFileName () {
		try {
			return userFile.getName();
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}
		return null;
	}
    /** Get the path of the file. */
  	public String getAbsolutePath () {
		try {
			return userFile.getAbsolutePath();
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}
		return null;
	}
    /** Get the extension for the file. */
	public String getFileExtension () {
		String [] split = userFile.getName().split(FILE_EXTENSION);
		return split[split.length - 1];
	}
  	
  	/** File Streams for the file reader/writer. */
  	private FileInputStream inputStream;
  	private FileOutputStream outputStream;
    /** Output stream for the file. */
  	private FileOutputStream getFileOutputStream() {
  		try {
  			return new FileOutputStream(userFile);
  		} catch (IOException ioe) {
  			System.out.println(ioe.getMessage());
  		}
		return null;
  	}
    /** Input stream for the file. */
  	private FileInputStream getFileInputStream() {
  		try {
  			return new FileInputStream(userFile);
  		} catch (IOException ioe) {
		    System.out.println(ioe.getMessage());
    	}
  		return null;
  	}
  	
  	/** The buffered Reader. */
  	private InputStreamReader streamReader;
  	private BufferedReader bufferedReader;
  	
  	/** The output writer. */
  	private OutputStreamWriter streamWriter;
  	
  	/** Static used to parse file extensions. */
  	private final static String FILE_EXTENSION = "\\.";
  	
  	
  	/** Simple constructor.
  	 * @param selectedFile - file to open. */
  	public FileUtils(File selectedFile) { this.userFile = selectedFile;	}
  	/** Simple constructor.
  	 * @param fileName the name of the file. */
  	public FileUtils(String fileName) { this(new File(fileName));	}
  	/** Simple constructor.
  	 * @param path path to the file.
  	 * @param fileName name of the file. */
  	public FileUtils(String path, String fileName) { this(new File(path, fileName)); }

    /** Get the underlying file object. */
 	public File getFile () { return userFile; }
 	
 	  /** Get the underlying file object. */
 	public void setFile (File f) { userFile = f; }
	
 	/** Create the file if it does not exist or delete the file and then create
     * the file. */
 	public void create(){
 		try {
 			if (userFile.exists()) 	userFile.delete();
	  		userFile.createNewFile();
		} catch (IOException ioe) {
		    System.out.println(ioe.getMessage());
    	}
 	}
 	
	/** Close any open file resources. */
  	public void close () {
    	try {
      		if (outputStream != null){
      			outputStream.flush();
	   			outputStream.close();
  			}
      		if (inputStream != null){
      			inputStream.close();
			}
      	} catch(IOException ioe) {
		    System.out.println(ioe.getMessage());
   		}
      	outputStream = null;
      	inputStream = null;
      	userFile = null;
  	}
  	
    /** Read a line from the file. */
   	public String readln () {
    	try {
    		if (streamReader == null) streamReader = new InputStreamReader(getFileInputStream());
    		if (bufferedReader == null) bufferedReader = new BufferedReader(streamReader);
    		return bufferedReader.readLine();
   		} catch (IOException ioe) {
		    System.out.println(ioe.getMessage());
    	}
  		return "";
  	}
   	
    /** Write a line of text to the file.
     * @param str what to write. */
	public void writeLn (String str) {
    	if (streamWriter == null) {
      		streamWriter = new OutputStreamWriter(getFileOutputStream());
		}
		PrintWriter PW = new PrintWriter(streamWriter);
    	PW.print(str);
		PW.println();
    	PW.flush();
  	}
    /** Write to the file. 
     * @param str what to write. */
	public void write (String str) {
   		if (streamWriter == null) {
     		streamWriter = new OutputStreamWriter(getFileOutputStream());
		}
		PrintWriter PW = new PrintWriter(streamWriter);
   		PW.print(str);
   		PW.flush();
  	}
	
	public boolean fileExists() {
		return userFile.exists();
	}
	
	public static boolean fileCopy (File inputFile, File outputFile) {
		FileUtils fileout = new FileUtils(outputFile);
		FileUtils filein = new FileUtils(inputFile);
		
		if (!inputFile.canRead() || !outputFile.canWrite()) { return false;	}
		
		boolean finished = false;
		String entry;
		while (!finished) {
			entry = "";
			try {
				entry = filein.readln();
				if (entry == null) {
					finished = true;
				}
			} catch (Exception e) {
				System.out.println("Could not create backup copy of file " + e.getMessage());
				finished = true;
				filein.close();
				fileout.close();
				filein = fileout = null;
				return false;
			}

			if (!finished) {
				fileout.writeLn(entry);
			}
		}
		
		filein.close();
		fileout.close();
		filein = fileout = null;
		return true;
	}
	
	public static boolean fileBackup(String outputFolder, String fileName) {
		// current File 
		File file = new File(outputFolder, fileName);
		// Backup file
		File file2 = new File(outputFolder, fileName + ".old");
		// Backup the backup file
		if (file2.exists()) {
			File file3 = new File(outputFolder, file2.getName() + "." + System.currentTimeMillis());
			boolean success = file2.renameTo(file3);
			if (!success) {
				System.out.println("Backup file " + file2.getName() + " not renamed to " + file3.getName());
			}
			System.out.println("Backup file " + file2.getName() + " renamed to " + file3.getName());
		}
		
		//  File was not successfully renamed
		if (!file.renameTo(file2)) {
			System.out.println("Could not rename file " + file.getName() + " to " + file2.getName());
			try {
	 			if (file2.exists()) {
	 				System.out.println("Deleted file " + file2.getName());
	 				file2.delete();
	 			}
	 			file2.createNewFile();
			} catch (IOException ioe) {
			    System.out.println("Could not create backup file for copy " + ioe.getMessage());
			    return false;
	    	}
			System.out.println("Copying file " + file.getName() + " to " + file2.getName());
			if (!fileCopy (file, file2)) {
				System.out.println("Could not copy file " + file.getName() + " to " + file2.getName());
				return false;
			}
			System.out.println("Copied file " + file.getName() + " to " + file2.getName());
		}
		return true;
	}
	
	/** Get the volume for a disc. */
	public static String getDiscVolumeLabel (String directory) {
		FileSystemView view = FileSystemView.getFileSystemView();
		File dir = new File(directory);
		String name = view.getSystemDisplayName(dir);
		if (name == null || name.length() < 1) { return DEFAULT_DISC_NAME; }
		//name = name.substring(name.indexOf("DISC"));
		name = name.trim();
		if (name == null || name.length() < 1) {
			return DEFAULT_DISC_NAME;
		}
		int index = name.lastIndexOf(" (");
		if (index > 0) {
			name = name.substring(0, index);
		}
		return name;
	}
}
