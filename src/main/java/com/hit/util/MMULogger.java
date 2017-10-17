package com.hit.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 
 * @author Aviad and Idan
 * MMULogger write in log file (.txt), this class is singleton
 */
public final class MMULogger {
	private final static String DEFAULT_FILE_NAME = "logs/log.txt";
	private FileHandler handler;
	private static MMULogger instance =null;
	
	private MMULogger() {
		try {
			this.handler = new FileHandler(DEFAULT_FILE_NAME); 
		} catch(IOException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		this.handler.close();
	}
	
	/**
	 * 
	 * @return Instance of the singleton MMULogger
	 */
	public static MMULogger getInstance() {
		if(instance==null){
			instance= new MMULogger();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param command the command to write to log file
	 * @param level use just SERVE/INFO- SEVERE is a message level indicating a serious failure. INFO is a message level for informational messages.
	 */
	public synchronized void write(String command, Level level) {
		LogRecord logRecord = new LogRecord(level, command);
		handler.setFormatter(new OnlyMessageFormatter());
		handler.publish(logRecord);
	}
	
	//inner class
	class OnlyMessageFormatter extends Formatter {

		public OnlyMessageFormatter() {
			super();
		}
		
		@Override
		public String format(LogRecord record) {
			return record.getMessage();
		}
		
	}
	
	/**
	 * override to keep the singleton safe from cloning
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
