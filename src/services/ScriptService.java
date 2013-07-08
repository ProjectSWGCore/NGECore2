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
package services;

import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import main.NGECore;

public class ScriptService {

	private NGECore core;
	private PythonInterpreter interpreter;

	public ScriptService(NGECore core) {
		this.core = core;
	}
	
	public void callScript(String path, String module, String method) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(core));
		interpreter.cleanup();
	}
	
	public void callScript(String path, String module, String method, Object arg1) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(core), Py.java2py(arg1));
		interpreter.cleanup();
	}
	
	public void callScript(String path, String method, String module, Object arg1, Object arg2) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2));
		interpreter.cleanup();
	}
	
	public void callScript(String path, String method, String module, Object arg1, Object arg2, Object arg3) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3));
		interpreter.cleanup();
	}

	public void callScript(String path, String module, String method, Object arg1, Object arg2, Object arg3, Object arg4) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3), Py.java2py(arg4));
		interpreter.cleanup();
	}
	
	public PyObject getMethod(String path, String module, String method) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		interpreter.cleanup();
		return func;
	}


}
