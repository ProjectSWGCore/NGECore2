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
package resources.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.python.google.common.collect.ArrayListMultimap;
import org.python.google.common.collect.Multimap;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PersistentProxy;

@Persistent(proxyFor=Multimap.class)
public class MultimapProxy<K, V> implements PersistentProxy<Multimap<K, V>> {
	
	private int size = 0;
	private K[] keys;
	private V[] values;
	
	private MultimapProxy() { }
	
	public void initializeProxy(Multimap<K, V> object) {
		List<K> keyList = new ArrayList<K>();
		List<V> valueList = new ArrayList<V>();
		
		for (Entry<K, V> entry : object.entries()) {
			keyList.add(entry.getKey());
			valueList.add(entry.getValue());
		}
		
		size = object.entries().size();
		
		keys = keyList.toArray(keys);
		values = valueList.toArray(values);
	}
	
	public Multimap<K, V> convertProxy() {
		Multimap<K, V> map = ArrayListMultimap.create();
		
		for (int i = 0; i < size; i++) {
			map.put(keys[i], values[i]);
		}
		
		return map;
	}
	
}
