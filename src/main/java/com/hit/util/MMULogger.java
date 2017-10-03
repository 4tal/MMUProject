package com.hit.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger {
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
	
	public static MMULogger getInstance() {
		if(instance==null){
			instance= new MMULogger();
		}
		return instance;
	}
	
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
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
