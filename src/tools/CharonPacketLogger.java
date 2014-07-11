package tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.nio.file.*;

public class CharonPacketLogger extends Thread {
	  private static CharonPacketLogger instance;
	  
	  private BlockingQueue<String> itemsToLog =
			    new ArrayBlockingQueue<String>(100);
	  
	  private static final String SHUTDOWN_REQ = "SHUTDOWN";
	  private volatile boolean shuttingDown, loggerTerminated;
	  
	  public static CharonPacketLogger getLogger() {
	    return instance;
	  }
	  public CharonPacketLogger() {
		  if (instance!=null)
			  return; // Singleton
		  instance = this;
	      start();
	  }
	  
	  public void run() {
		    try {
		      String item;
		      while ((item = itemsToLog.take()) != SHUTDOWN_REQ) {
		        System.out.println(item);
		      }
		    } catch (InterruptedException iex) {
		    } finally {
		      loggerTerminated = true;
		    }
	  }
	  
	  public void log(String str) {
		    if (shuttingDown || loggerTerminated) return;
		    try {
		      itemsToLog.put(str);
		    } catch (InterruptedException iex) {
		      Thread.currentThread().interrupt();
		      throw new RuntimeException("Unexpected interruption");
		    }
	  }
	  
	  public void log(String str,Path path) {
		    if (shuttingDown || loggerTerminated) return;
		    try {
		      itemsToLog.put(str);
		      CharonPacketUtils.appendToLog(str, path);
		    } catch (InterruptedException iex) {
		      Thread.currentThread().interrupt();
		      throw new RuntimeException("Unexpected interruption");
		    }
	  }
	  
	  public void shutDown() throws InterruptedException {
		  shuttingDown = true;
		  itemsToLog.put(SHUTDOWN_REQ);
	  }
}
