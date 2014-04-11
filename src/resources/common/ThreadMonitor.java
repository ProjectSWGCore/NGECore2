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
package resources.common;

import static java.lang.management.ManagementFactory.getThreadMXBean;

import java.io.IOException;
import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class ThreadMonitor {
	
	  private MBeanServerConnection server;

	  private ThreadMXBean tmbean;

	  private ObjectName objname;

	  // default - JDK 6+ VM
	  private String findDeadlocksMethodName = "findDeadlockedThreads";

	  private boolean canDumpLocks = true;

	  /**
	   * Constructs a ThreadMonitor object to get thread information in the local
	   * JVM.
	   */
	  public ThreadMonitor() {
	    this.tmbean = getThreadMXBean();
	  }

	  /**
	   * Prints the thread dump information to System.out.
	   */
	  public void threadDump() {
	    if (canDumpLocks) {
	      if (tmbean.isObjectMonitorUsageSupported() && tmbean.isSynchronizerUsageSupported()) {
	        dumpThreadInfoWithLocks();
	      }
	    } else {
	      dumpThreadInfo();
	    }
	  }

	  private void dumpThreadInfo() {
	    System.out.println("Full Java thread dump");
	    long[] tids = tmbean.getAllThreadIds();
	    ThreadInfo[] tinfos = tmbean.getThreadInfo(tids, Integer.MAX_VALUE);
	    for (ThreadInfo ti : tinfos) {
	      printThreadInfo(ti);
	    }
	  }

	  /**
	   * Prints the thread dump information with locks info to System.out.
	   */
	  private void dumpThreadInfoWithLocks() {
	    System.out.println("Full Java thread dump with locks info");

	    ThreadInfo[] tinfos = tmbean.dumpAllThreads(true, true);
	    for (ThreadInfo ti : tinfos) {
	      printThreadInfo(ti);
	      LockInfo[] syncs = ti.getLockedSynchronizers();
	      printLockInfo(syncs);
	    }
	    System.out.println();
	  }

	  private static String INDENT = "    ";

	  private void printThreadInfo(ThreadInfo ti) {
	    // print thread information
	    printThread(ti);

	    // print stack trace with locks
	    StackTraceElement[] stacktrace = ti.getStackTrace();
	    MonitorInfo[] monitors = ti.getLockedMonitors();
	    for (int i = 0; i < stacktrace.length; i++) {
	      StackTraceElement ste = stacktrace[i];
	      System.out.println(INDENT + "at " + ste.toString());
	      for (MonitorInfo mi : monitors) {
	        if (mi.getLockedStackDepth() == i) {
	          System.out.println(INDENT + "  - locked " + mi);
	        }
	      }
	    }
	    System.out.println();
	  }

	  private void printThread(ThreadInfo ti) {
	    StringBuilder sb = new StringBuilder("\"" + ti.getThreadName() + "\"" + " Id="
	        + ti.getThreadId() + " in " + ti.getThreadState());
	    if (ti.getLockName() != null) {
	      sb.append(" on lock=" + ti.getLockName());
	    }
	    if (ti.isSuspended()) {
	      sb.append(" (suspended)");
	    }
	    if (ti.isInNative()) {
	      sb.append(" (running in native)");
	    }
	    System.out.println(sb.toString());
	    if (ti.getLockOwnerName() != null) {
	      System.out.println(INDENT + " owned by " + ti.getLockOwnerName() + " Id="
	          + ti.getLockOwnerId());
	    }
	  }

	  @SuppressWarnings("unused")
	private void printMonitorInfo(ThreadInfo ti, MonitorInfo[] monitors) {
	    System.out.println(INDENT + "Locked monitors: count = " + monitors.length);
	    for (MonitorInfo mi : monitors) {
	      System.out.println(INDENT + "  - " + mi + " locked at ");
	      System.out.println(INDENT + "      " + mi.getLockedStackDepth() + " "
	          + mi.getLockedStackFrame());
	    }
	  }

	  private void printLockInfo(LockInfo[] locks) {
	    System.out.println(INDENT + "Locked synchronizers: count = " + locks.length);
	    for (LockInfo li : locks) {
	      System.out.println(INDENT + "  - " + li);
	    }
	    System.out.println();
	  }

	  /**
	   * Checks if any threads are deadlocked. If any, print the thread dump
	   * information.
	   */
	  public boolean findDeadlock() {
	    long[] tids;
	    if (findDeadlocksMethodName.equals("findDeadlockedThreads")
	        && tmbean.isSynchronizerUsageSupported()) {
	      tids = tmbean.findDeadlockedThreads();
	      if (tids == null) {
	        return false;
	      }

	      System.out.println("Deadlock found :-");
	      ThreadInfo[] infos = tmbean.getThreadInfo(tids, true, true);
	      for (ThreadInfo ti : infos) {
	        printThreadInfo(ti);
	        printLockInfo(ti.getLockedSynchronizers());
	        System.out.println();
	      }
	    } else {
	      tids = tmbean.findMonitorDeadlockedThreads();
	      if (tids == null) {
	        return false;
	      }
	      ThreadInfo[] infos = tmbean.getThreadInfo(tids, Integer.MAX_VALUE);
	      for (ThreadInfo ti : infos) {
	        // print thread information
	        printThreadInfo(ti);
	      }
	    }

	    return true;
	  }

	  @SuppressWarnings("unused")
	private void parseMBeanInfo() throws IOException {
	    try {
	      MBeanOperationInfo[] mopis = server.getMBeanInfo(objname).getOperations();

	      // look for findDeadlockedThreads operations;
	      boolean found = false;
	      for (MBeanOperationInfo op : mopis) {
	        if (op.getName().equals(findDeadlocksMethodName)) {
	          found = true;
	          break;
	        }
	      }
	      if (!found) {
	        findDeadlocksMethodName = "findMonitorDeadlockedThreads";
	        canDumpLocks = false;
	      }
	    } catch (IntrospectionException e) {
	      InternalError ie = new InternalError(e.getMessage());
	      ie.initCause(e);
	      throw ie;
	    } catch (InstanceNotFoundException e) {
	      InternalError ie = new InternalError(e.getMessage());
	      ie.initCause(e);
	      throw ie;
	    } catch (ReflectionException e) {
	      InternalError ie = new InternalError(e.getMessage());
	      ie.initCause(e);
	      throw ie;
	    }
	  }
	}