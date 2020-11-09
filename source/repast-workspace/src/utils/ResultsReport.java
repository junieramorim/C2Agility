package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ResultsReport{
	
	public enum typeFiles {
		RUNNING, FINAL, PARTIAL
	};
	
	private Map<String,String> fileNames;
	
	private final Logger logger = LogManager.getLogger(ResultsReport.class.getName());
	
	private String url = "";
	
	private String data;
	
	private static ResultsReport report;
	
	private ResultsReport() {
		super();
	}
	
	public static ResultsReport getInstance() {
		if (report == null)
			report = new ResultsReport();
		return report;
	}
	
	public void writeFile(String type, boolean append, String line) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append(fileNames.get(type));
		try {
			FileWriter fw = new FileWriter(sb.toString(),append);
		    fw.write(line);
		    fw.close();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
	}
	
	private void writeFile(String url, String name, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append(name);
		try {
			File f = new File(url);
			if(!f.exists())
				f.mkdir();
			FileWriter fw = new FileWriter(sb.toString(),true); //always add a value in the old file
		    fw.write(value);
		   // fw.write(";");
		    fw.close();
		}catch(IOException err) {
			err.printStackTrace();
		}
		
	}
	
	public void deleteFileByType(String type, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append(fileNames.get(type));
		File file = new File(sb.toString());
		file.delete();	
	}
	
	public void deleteFileByName(String name, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append(name);
		File file = new File(sb.toString());
		file.delete();	
	}
	
	public void clearAll() {
		for(String file : fileNames.values()) 
			deleteFileByName(file, this.url);
	}
	
	public String getFileName(String key) {
		return fileNames.get(key);
	}

	public Map<String,String> getFileNames() {
		if(fileNames == null)
			fileNames = new HashMap<String,String>();
		return this.fileNames;
	}
	
	/**
	 * Seeds used: 
	 * 			2500 a 92219 (interval 1831)
	 * 			2500 a 916169 (interval 1831)
	 */
	public void registerMetric(String metric, String url, String value) {
//		if(Parameters.getInstance().repetitions() == 1 || Parameters.getInstance().randomSeed() == 2500) {
//			StringBuffer sb = new StringBuffer();
//			sb.append(url);
//			sb.append(metric);
//			File f = new File(sb.toString());
//			if(f.exists())
//				value = "\n" + value;
//		}
		if(Parameters.getInstance().randomSeed() == 916169) {
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			sb.append(metric);
			File f = new File(sb.toString());
			if(f.exists())
				value = value + "\n";
		}else {
			value = value + ";";
		}
		this.writeFile(url, metric, value);	
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}
	
}
