package com.blastfurnace.otr.util;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blastfurnace.otr.util.reflection.FieldProperties;

public class CSVUtils {

	private static Log log = LogFactory.getLog(CSVUtils.class);
	
	private List<FieldProperties> dataFields;

	public CSVUtils(List<FieldProperties> dataFields) {  
		this.dataFields = dataFields;
	}  // constructor
	
	
	public <T> void writeResults (List<T> objs, String filename, String separator) {
		if (objs.size() < 1) {
			log.info("no objects to write");
			return;
		}
		FileUtils file = new FileUtils(filename);
		file.create();
		writeTitles(file, separator);
		for (T obj : objs) {
			writeData (obj, file, separator);
		}
		file.close();
		log.info("Wrote " + objs.size() + " entries to " + filename);
	}

	private void writeTitles (FileUtils file, String separator) {
		for (FieldProperties fp : dataFields) {
			String title = fp.getName();
			title = StringUtils.getTitleFromHumpBack(title);
			file.write(title + separator);
		}
		file.writeLn("");
	}

	private <T> void writeData (T obj, FileUtils file, String separator) {
		for (FieldProperties fp : dataFields) {
			Field f;
			try {
				f = obj.getClass().getDeclaredField(fp.getName());
				if (f != null) {
					f.setAccessible(true);
					Object value =  f.get(obj);
					file.write(value.toString() + separator);
				}
			} catch (NoSuchFieldException | SecurityException |  IllegalAccessException e) {
				// TODO Auto-generated catch block
				file.write("" + separator);
				e.printStackTrace();
			}
		}
		
		file.writeLn("");
	}

}
