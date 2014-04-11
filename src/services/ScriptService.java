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

import java.util.Iterator;
import java.util.Vector;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import main.NGECore;

@SuppressWarnings("unused")

public class ScriptService {
	
	private NGECore core;
	private PythonInterpreter interpreter;
	
	public ScriptService(NGECore core) {
		this.core = core;
	}
	
	public PyObject callScript(String path, String module, String method) {
		/*PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.cleanup();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(core));*/
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		return python.get(method).__call__();
	}
	
	public PyObject callScript(String path, String module, String method, Object arg1) {
		/*PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.cleanup();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(core), Py.java2py(arg1));*/
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		return python.get(method).__call__(Py.java2py(arg1));
	}
	
	public PyObject callScript(String path, String module, String method, Object arg1, Object arg2) {
		/*PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.cleanup();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2));*/
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		return python.get(method).__call__(Py.java2py(arg1), Py.java2py(arg2));
	}
	
	public PyObject callScript(String path, String module, String method, Object arg1, Object arg2, Object arg3) {
		/*PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.cleanup();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3));*/
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		return python.get(method).__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3));

	}
	
	public PyObject callScript(String path, String module, String method, Object arg1, Object arg2, Object arg3, Object arg4) {
		/*PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.cleanup();
		interpreter.exec("import sys\nsys.path.append('" + path + "')\nfrom " + module + " import " + method);	
		PyObject func = interpreter.get(method);
		func.__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3), Py.java2py(arg4));*/
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		return python.get(method).__call__(Py.java2py(arg1), Py.java2py(arg2), Py.java2py(arg3), Py.java2py(arg4));
	}
	
	public PyObject getMethod(String path, String module, String method) {
		PythonInterpreter python = new PythonInterpreter();
		python.cleanup();
		python.execfile(path + module + ".py");
		PyObject func = python.get(method);
		return func;
	}
	
	public String fetchString(String path, String method) {
		PyObject result = core.scriptService.callScript(path, "", method);
		return ((PyObject)result).asString();		
	}
	
	public int fetchInteger(String path, String method) {
		PyObject result = core.scriptService.callScript(path, "", method);
		return ((PyObject)result).asInt();		
	}
	
	public Vector<String> fetchStringVector(String path, String method) {
		Vector<String> vector = new Vector<String>();
		PyObject result = core.scriptService.callScript(path, "", method);
		Iterable<PyObject> comp = (Iterable<PyObject>)result.asIterable();
		for (Iterator<PyObject> temp = comp.iterator(); temp.hasNext();){
			vector.add(temp.next().asString());
		}
		return vector;
	}
	
	public Vector<Integer> fetchIntegerVector(String path, String method) {
		Vector<Integer> vector = new Vector<Integer>();
		PyObject result = core.scriptService.callScript(path, "", method);
		Iterable<PyObject> comp = (Iterable<PyObject>)result.asIterable();
		for (Iterator<PyObject> temp = comp.iterator(); temp.hasNext();){
			vector.add(temp.next().asInt());
		}
		return vector;
	}
	
}
