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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * @author Charon 
 */

@SuppressWarnings("unused")
public class DevLog {
	
	private static int loggingSessionID=0;	
	private static Map<String,Integer> debugEnable = new HashMap<String,Integer>();	
	static {
		debugEnable.put("Charon",0);
		debugEnable.put("YourNameHere",0);
	}
	
	private static Map<String,Integer> fileEnable = new HashMap<String,Integer>();	
	static {
		fileEnable.put("Charon",0);
		fileEnable.put("YourNameHere",0);
	}
	

	static Path newFile = Paths.get("src", "PSWG_DEVLOG.log");
	//static Path newFile = Paths.get("M:/PSWG/LOGS/PSWG_PacketLog.txt");
	private CharonPacketLogger logger;
	
	public DevLog(){
		
	}
	
	public static void enableMe(){
		String OS_User = System.getProperty("user.name");
		if (debugEnable.keySet().contains(OS_User)){
			debugEnable.put(OS_User,1);
		}
	}
	
	public static void disableMe(){
		String OS_User = System.getProperty("user.name");
		if (debugEnable.keySet().contains(OS_User)){
			debugEnable.put(OS_User,0);
		}
	}
	
	public static void enableFileLogging(){
		String OS_User = System.getProperty("user.name");
		if (fileEnable.keySet().contains(OS_User)){
			fileEnable.put(OS_User,1);
		}
	}
	
	public static void disableFileLogging(){
		String OS_User = System.getProperty("user.name");
		if (fileEnable.keySet().contains(OS_User)){
			fileEnable.put(OS_User,0);
		}
	}
	
	public static boolean isFileLoggingEnabled(String developer){
		if (fileEnable.keySet().contains(developer)){
			if (fileEnable.get(developer)==1){
				return true;
			}
		}
		return false;
	}
	
	public static void debugout(String debugRequester, String testarea, String message){
		String developer = System.getProperty("user.name");
		String logMessage = "";
		if (!debugRequester.equals(developer))
			return;
		if (debugEnable.keySet().contains(developer)){
			
			if (debugEnable.get(developer)==1){
				logMessage = "Devlog->"+developer+"->"+testarea+"-> "+message;
				DevLogQueuer logger = DevLogQueuer.getInstance();
				logger.log(logMessage);
			}
		}
		
		if (isFileLoggingEnabled(debugRequester)){
			if (loggingSessionID>0){
				DevLogQueuer logger = DevLogQueuer.getInstance();   
	        	logger.log(logMessage,newFile);
				
			} else {
				createLog();
				DevLogQueuer logger = DevLogQueuer.getInstance();    
	        	logger.log(logMessage,newFile);
				loggingSessionID++;
			}
		}
		
	}
	
	public static void createLog(){
		
	    try {
	    	Date date = new Date();
	    	Calendar cal1 = Calendar.getInstance();
	    	cal1.setTimeInMillis(System.currentTimeMillis());
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-hh-mm-ss");
	    	String sDate=dateFormat.format(cal1.getTime());

	        String fileName = "PSWG_DEVLOG_"+sDate+".log";
	        newFile = Paths.get("debuglogs", fileName);
	        Files.deleteIfExists(newFile);
	        newFile = Files.createFile(newFile);
	        createHeader(newFile);
	    } catch (IOException ex) {
	    	System.out.println("Error creating file");
	    } 
	}
	
	public static void appendToLog(String content, Path newFile){
	    //Writing to file
	    try(BufferedWriter writer = Files.newBufferedWriter(
	            newFile, Charset.defaultCharset(),new OpenOption[] {StandardOpenOption.APPEND})){
	    	Date date = new Date();
	    	Calendar cal1 = Calendar.getInstance();
	    	cal1.setTimeInMillis(System.currentTimeMillis());
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
	    	String sDate=dateFormat.format(cal1.getTime());
	        writer.append(sDate + "  ");
	        writer.append(content);
	        writer.append("\n");
	        writer.flush();
	    }catch(IOException exception){
	      System.out.println("Error writing to file");
	    }	
	}
	
	public static void createHeader(Path newFile){
	    //Writing to file
	    try(BufferedWriter writer = Files.newBufferedWriter(
	            newFile, Charset.defaultCharset(),new OpenOption[] {StandardOpenOption.APPEND})){
	    	Date date = new Date();
	    	Calendar cal1 = Calendar.getInstance();
	    	cal1.setTimeInMillis(System.currentTimeMillis());
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-hh:mm:ss");
	    	String sDate=dateFormat.format(cal1.getTime());
	    	String developer = System.getProperty("user.name");
	    	writer.append("*********************************************\n");
	        writer.append("**** PROJECTSWG DEBUG LOG " + sDate + " ****\n");	        
	        writer.append("*********************************************\n");
	        writer.append("DEV: "+developer + "\n");
	        writer.append("\n");
	        writer.flush();
	    }catch(IOException exception){
	      System.out.println("Error writing to file");
	    }	
	}

}
