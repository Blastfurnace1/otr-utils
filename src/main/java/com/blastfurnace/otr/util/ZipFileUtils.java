package com.blastfurnace.otr.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFileUtils {

	private static final Logger log = LoggerFactory.getLogger(ZipFileUtils.class);  

	/** E:/winzip/winzip32 -min -e " + inFileName + " " + destination. */
	private static final String COMMAND = "c:/winzip/winzip32 -min -e -o ";
	
	public ZipFileUtils() {
		// TODO Auto-generated constructor stub
	}

	/** Deletes all subdirectories under dir.
	 Returns true if all deletions were successful.
	 If a deletion fails, the method stops attempting to delete and returns false.*/
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	/** Deletes all files and subdirectories under dir. */
	public void deleteExtractedFiles (String baseDir) {

		File f = new File(baseDir);
		String[] list = f.list();
		for (int i = 0; i < list.length; i++) {
			File dirCheck = new File(baseDir + list[i]);

			if (dirCheck.isDirectory()) {
				deleteDir(dirCheck);
			} else {
				if (dirCheck.isFile()) {
					dirCheck.delete();
				}
			}
		}
	}

	/** unzip the zip file to a specified location. */
	public boolean unzip (String inFileName, String destination)  {
		boolean operationSucceded = true;

		String mydestination = destination.replaceAll(" ", "-");
		try
		{
			String cmd = COMMAND + " \"" + inFileName + "\" \"" + mydestination + "\"";
			log.info(" Unzipping file " + inFileName + " to " + mydestination);
			log.info(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		}
		catch (Exception ioe)
		{
			ioe.printStackTrace();
			log.info("failed on file " + inFileName);
			operationSucceded = false;
		}
		return operationSucceded;
	}

}
