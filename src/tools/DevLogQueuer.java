/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.nio.file.*;

/** 
 * @author Charon 
 */

public class DevLogQueuer extends Thread {
	  private static DevLogQueuer instance;
	  
	  private BlockingQueue<String> itemsToLog =
			    new ArrayBlockingQueue<String>(100);
	  
	  private static final String SHUTDOWN_REQ = "SHUTDOWN";
	  private volatile boolean shuttingDown, loggerTerminated;
	  
	 
	  
	  public DevLogQueuer() {
		  if (instance!=null)
			  return; // Singleton
		  instance = this;
	      start();
	  }
	  
	  public static DevLogQueuer getInstance() {
			return instance;
	  }
	  
	  public static DevLogQueuer getLogger() {
		    return instance;
	  }
	  
	  public void run() {
		    try {
		      String item;
		      while ((item = itemsToLog.take()) != SHUTDOWN_REQ) {
		        System.err.println(item);
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
		      DevLog.appendToLog(str, path);
		    } catch (Exception iex) {
		      Thread.currentThread().interrupt();
		      throw new RuntimeException("Unexpected interruption");
		    }
	  }
	  
	  public void shutDown() throws InterruptedException {
		  shuttingDown = true;
		  itemsToLog.put(SHUTDOWN_REQ);
	  }
}
